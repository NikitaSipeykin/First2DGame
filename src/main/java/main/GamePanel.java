package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
  //screen settings
  final int originalTileSize = 16; //16x16 tiles
  final int scale = 3;

  final int tileSize = originalTileSize * scale; //48x48 tiles
  final int maxScreenCol = 16;
  final int maxScreenRow = 12;
  final int screenWidth = tileSize * maxScreenCol; //768 pixels
  final int screenHeight = tileSize * maxScreenRow; //576 pixels

  //FPS
  int FPS = 60;

  KeyHandler keyH = new KeyHandler();
  Thread gameThread;

  //set players default position
  int playerX = 100;
  int playerY = 100;
  int playerSpeed = 4;

  public GamePanel(){
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyH);
    this.setFocusable(true);
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
    if(keyH.upPresed) {
      playerY -= playerSpeed;
    } else if (keyH.downPresed) {
      playerY += playerSpeed;
    } else if (keyH.leftPresed) {
      playerX -= playerSpeed;
    } else if (keyH.rightPresed) {
      playerX += playerSpeed;
    }
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D)g;

    g2.setColor(Color.white);
    g2.fillRect(playerX, playerY, tileSize, tileSize);
    g2.dispose();
  }
}
