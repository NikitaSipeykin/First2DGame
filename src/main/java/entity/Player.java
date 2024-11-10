package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
  GamePanel gp;
  KeyHandler keyH;

  public final int screenX;
  public final int screenY;

  public Player(GamePanel gp, KeyHandler keyH) {
    this.gp = gp;
    this.keyH = keyH;

    screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
    screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

    setDefaultValues();
    getPlayerImage();
  }

  public void setDefaultValues(){
    worldX = gp.tileSize * 23;
    worldY = gp.tileSize * 21;
    speed = 4;
    direction = "down";
  }

  public void getPlayerImage(){
    try {
      up1 = ImageIO.read(getClass().getResourceAsStream("/player/boyUp1.png"));
      up2 = ImageIO.read(getClass().getResourceAsStream("/player/boyUp2.png"));
      down1 = ImageIO.read(getClass().getResourceAsStream("/player/boyDown1.png"));
      down2 = ImageIO.read(getClass().getResourceAsStream("/player/boyDown2.png"));
      left1 = ImageIO.read(getClass().getResourceAsStream("/player/boyLeft1.png"));
      left2 = ImageIO.read(getClass().getResourceAsStream("/player/boyLeft2.png"));
      right1 = ImageIO.read(getClass().getResourceAsStream("/player/boyRight1.png"));
      right2 = ImageIO.read(getClass().getResourceAsStream("/player/boyRight2.png"));
    }catch (IOException e){
      e.printStackTrace();
    }
  }

  public void update(){
    if(keyH.rightPresed || keyH.upPresed || keyH.downPresed || keyH.leftPresed){
      if(keyH.upPresed) {
        direction = "up";
        worldY -= speed;
      } else if (keyH.downPresed) {
        direction = "down";
        worldY += speed;
      } else if (keyH.leftPresed) {
        direction = "left";
        worldX -= speed;
      } else if (keyH.rightPresed) {
        direction = "right";
        worldX += speed;
      }

      spriteCounter++;
      if(spriteCounter > 10){
        if (spriteNumber == 1) {
          spriteNumber = 2;
        } else if (spriteNumber == 2) {
          spriteNumber = 1;
        }
        spriteCounter = 0;
      }
    }

  }

  public void draw(Graphics2D g2){
//    g2.setColor(Color.white);
//    g2.fillRect(x, y, gp.tileSize, gp.tileSize);

    BufferedImage image = null;

    switch (direction){
      case "up":
        if(spriteNumber == 1){
          image = up1;
        }
        if (spriteNumber == 2) {
          image = up2;
        }
        break;
      case "down":
        if(spriteNumber == 1){
          image = down1;
        }
        if (spriteNumber == 2) {
          image = down2;
        }
        break;
      case "left":
        if(spriteNumber == 1){
          image = left1;
        }
        if (spriteNumber == 2) {
          image = left2;
        }
        break;
      case "right":
        if(spriteNumber == 1){
          image = right1;
        }
        if (spriteNumber == 2) {
          image = right2;
        }
        break;
    }
    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
  }
}
