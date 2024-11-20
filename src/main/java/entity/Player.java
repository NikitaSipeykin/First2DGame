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
  int hasKey = 0;

  public Player(GamePanel gp, KeyHandler keyH) {
    this.gp = gp;
    this.keyH = keyH;

    screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
    screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

    solidArea = new Rectangle();
    solidArea.x = 8;
    solidArea.y = 16;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.width = 32;
    solidArea.height = 32;

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
      } else if (keyH.downPresed) {
        direction = "down";
      } else if (keyH.leftPresed) {
        direction = "left";
      } else if (keyH.rightPresed) {
        direction = "right";
      }

      //check tile collision
      collisionON = false;
      gp.cChecker.checkTile(this);

      //check object collision
      int objIndex = gp.cChecker.checkObject(this, true);
      pickUpObject(objIndex);

      //if collision is false player can't move
      if (!collisionON){
        switch (direction){
          case "up":
            worldY -= speed;
            break;
          case "down":
            worldY += speed;
            break;
          case "left":
            worldX -= speed;
            break;
          case "right":
            worldX += speed;
            break;
        }
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

  public void pickUpObject(int i){
    if (i != 999){
       String objectName = gp.obj[i].name;
      switch (objectName){
        case "Key":
          hasKey++;
          gp.obj[i] = null;
          break;
        case"Door":
          if (hasKey > 0){
            gp.obj[i] = null;
            hasKey--;
          }
          break;
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
