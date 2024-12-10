package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
  GamePanel gp;
  KeyHandler keyH;

  public final int screenX;
  public final int screenY;
  public int hasKey = 0;
  int standCounter = 0;
  boolean moving = false;
  int pixelCounter = 0;

  public Player(GamePanel gp, KeyHandler keyH) {
    this.gp = gp;
    this.keyH = keyH;

    screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
    screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

    solidArea = new Rectangle();
    solidArea.x = 1;
    solidArea.y = 1;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.width = 46;
    solidArea.height = 46;

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
    up1 = setup("boyUp1");
    up2 = setup("boyUp2");
    down1 = setup("boyDown1");
    down2 = setup("boyDown2");
    left1 = setup("boyLeft1");
    left2 = setup("boyLeft2");
    right1 = setup("boyRight1");
    right2 = setup("boyRight2");
  }

  public BufferedImage setup(String imageName){
    UtilityTool uTool = new UtilityTool();
    BufferedImage image = null;

    try{
      image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
      image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
    }catch (IOException e){
      e.printStackTrace();
    }
    return image;
  }

  public void update(){

    if (!moving){
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

        moving = true;

        //check tile collision
        collisionON = false;
        gp.cChecker.checkTile(this);

        //check object collision
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);


      }else {
        standCounter++;
        if (standCounter == 20){
          spriteNumber = 1;
          standCounter = 0;
        }
      }
    }

      if (moving){
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
        pixelCounter += speed;
        if(pixelCounter == 48){
          moving = false;
          pixelCounter = 0;
        }
      }

  }

  public void pickUpObject(int i){
    if (i != 999){
       String objectName = gp.obj[i].name;
      switch (objectName){
        case "Key":
          gp.playSE(1);
          hasKey++;
          gp.obj[i] = null;
          gp.ui.showMessage("You got a key!");
          break;
        case"Door":
          if (hasKey > 0){
            gp.playSE(3);
            gp.obj[i] = null;
            hasKey--;
            gp.ui.showMessage("You opened the door!");
          }else {
            gp.ui.showMessage("You need a key!");
          }
          break;
        case "Boots":
          gp.playSE(2);
          speed += 2;
          gp.obj[i] = null;
          gp.ui.showMessage("Speed up!");
          break;
        case "Chest":
          gp.ui.gameFinished = true;
          gp.stopMusic();
          gp.playSE(4);
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
    g2.drawImage(image, screenX, screenY,null);
  }
}
