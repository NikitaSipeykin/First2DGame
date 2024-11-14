package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

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
  public final int worldWidth = tileSize * maxWorldCol;
  public final int worldHeight = tileSize * maxWorldRow;

  //FPS
  int FPS = 60;

  TileManager tileManager = new TileManager(this);
  KeyHandler keyH = new KeyHandler();
  Thread gameThread;
  public CollisionChecker cChecker = new CollisionChecker(this);
  public AssetSetter aSetter = new AssetSetter(this);
  public Player player = new Player(this, keyH);
  public SuperObject obj[] = new SuperObject[10];

  public GamePanel(){
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyH);
    this.setFocusable(true);
  }

  public void setupGame(){
    aSetter.setObject();
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
    player.update();
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D)g;
    tileManager.draw(g2);
    for (int i = 0; i < obj.length; i++) {
      if (obj[i] != null){
        obj[i].draw(g2, this);
      }
    }
    player.draw(g2);
    g2.dispose();
  }
}
