package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{
  //screen settings
  final int originalTileSize = 16; //16x16 tiles
  final int scale = 3;
  public final int tileSize = originalTileSize * scale; //48x48 tiles
  public final int maxScreenCol = 16;
  public final int maxScreenRow = 12;
  public final int screenWidth = tileSize * maxScreenCol; //768 pixels
  public final int screenHeight = tileSize * maxScreenRow; //576 pixels
  //world settings
  public final int maxWorldCol = 50;
  public final int maxWorldRow = 50;
  //FPS
  int FPS = 60;
  //SYSTEM
  TileManager tileManager = new TileManager(this);
  public KeyHandler keyH = new KeyHandler(this);
  Sound music = new Sound();
  Sound sound = new Sound();
  public CollisionChecker cChecker = new CollisionChecker(this);
  public AssetSetter aSetter = new AssetSetter(this);
  public UI ui = new UI(this);
  public EventHandler eHandler = new EventHandler(this);
  Thread gameThread;
  //ENTITY AND OBJECTS
  public Player player = new Player(this, keyH);
  public Entity[] obj = new Entity[10];
  public Entity[] npc = new Entity[10];
  public Entity[] monster = new Entity[20];
  ArrayList<Entity> entityList = new ArrayList<>();
  //game state
  public int gameState;
  public final int titleState = 0;
  public final int playState = 1;
  public final int pauseState = 2;
  public final int dialogueState = 3;

  public GamePanel(){
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyH);
    this.setFocusable(true);
  }

  public void setupGame(){
    aSetter.setObject();
    aSetter.setNPC();
    aSetter.setMonster();
//    playMusic(0);
    gameState = titleState;
  }

  public void setGameThread(){
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    double drawInterval = 1000000000d/FPS;
    double nextDrawTime = System.nanoTime() + drawInterval;
    while (gameThread != null){
      //
      //1 UPDATE: update information such as character positions
      update();
      //2 DRAW: draw the screen with updated information
      repaint();

      try {
        double remainingTime = nextDrawTime - System.nanoTime();
        remainingTime = remainingTime/1000000d;

        if (remainingTime < 0){
          remainingTime = 0d;
        }

        Thread.sleep((long) remainingTime);
        nextDrawTime += drawInterval;
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

//  //alternative method
//  @Override
//  public void run() {
//    double drawInterval = 1000000000d/FPS;
//    double delta = 0;
//    long lastTime = System.nanoTime();
//    long currentTime;
//    long timer = 0;
//    int drawCount = 0;
//
//    while (gameThread != null){
//      currentTime = System.nanoTime();
//      delta += (currentTime - lastTime) / drawInterval;
//      timer += (currentTime - lastTime);
//      lastTime = currentTime;
//      if(delta >= 1){
//        update();
//        repaint();
//        delta--;
//        drawCount++;
//      }
//      if(timer >= 1000000000){
//        System.out.println("FPS: " + drawCount);
//        drawCount = 0;
//        timer = 0;
//      }
//    }
//  }

  public void update(){
    if (gameState == playState){
      player.update();
      for (int i = 0; i < npc.length; i++) {
        if (npc[i] != null){
          npc[i].update();
        }
      }
      for (int i = 0; i < monster.length; i++) {
        if (monster[i] != null){
          monster[i].update();
        }
      }
    }
    if (gameState == pauseState){

    }
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;

    //debug
    long drawStart = 0;
    if (keyH.checkDrawTime){
      drawStart = System.nanoTime();
    }

    //title screen
    if (gameState == titleState){
      ui.draw(g2);
    }
    //others
    else {
      //tile
      tileManager.draw(g2);

      //add entities to the list
      entityList.add(player);

      for (int i = 0; i < npc.length; i++) {
        if (npc[i] != null){
          entityList.add(npc[i]);
        }
      }

      for (int i = 0; i < obj.length; i++) {
        if (obj[i] != null){
          entityList.add(obj[i]);
        }
      }

      for (int i = 0; i < monster.length; i++) {
        if (monster[i] != null){
          entityList.add(monster[i]);
        }
      }

      //sort
      Collections.sort(entityList, new Comparator<Entity>() {
        @Override
        public int compare(Entity e1, Entity e2) {
          int result = Integer.compare(e1.worldY, e2.worldY);
          return result;
        }
      });

      //draw entities
      for (int i = 0; i < entityList.size(); i++) {
        entityList.get(i).draw(g2);
      }

      //empty entity list
      entityList.clear();
//      player.draw(g2);
      ui.draw(g2);
    }




    //debug
    if (keyH.checkDrawTime){
      long drawEnd = System.nanoTime();
      long passed = drawEnd - drawStart;
      g2.setColor(Color.white);
      g2.drawString("Draw Time: " + passed, 10, 400);
    }

    g2.dispose();
  }

  public void playMusic(int i){
     music.setFile(i);
     music.play();
     music.loop();
  }

  public void stopMusic(){
     music.stop();
  }

  public void playSE(int i){
    sound.setFile(i);
    sound.play();
  }
}
