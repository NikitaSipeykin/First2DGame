package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
  KeyHandler keyH;
  public final int screenX;
  public final int screenY;
  int standCounter = 0;
  boolean moving = false;
  int pixelCounter = 0;

  public Player(GamePanel gp, KeyHandler keyH) {
    super(gp);
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

    //player status
    maxLife = 6;
    life = maxLife;
  }

  public void getPlayerImage(){
    up1 = setup("/player/boyUp1");
    up2 = setup("/player/boyUp2");
    down1 = setup("/player/boyDown1");
    down2 = setup("/player/boyDown2");
    left1 = setup("/player/boyLeft1");
    left2 = setup("/player/boyLeft2");
    right1 = setup("/player/boyRight1");
    right2 = setup("/player/boyRight2");
  }

  public void update(){

    if (!moving){
      if(keyH.rightPressed || keyH.upPressed || keyH.downPressed || keyH.leftPressed){
        if(keyH.upPressed) {
          direction = "up";
        } else if (keyH.downPressed) {
          direction = "down";
        } else if (keyH.leftPressed) {
          direction = "left";
        } else if (keyH.rightPressed) {
          direction = "right";
        }

        moving = true;

        //check tile collision
        collisionON = false;
        gp.cChecker.checkTile(this);

        //check npc collision
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

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
    }
  }

  public void interactNPC(int i){
    if (i != 999){
      if (gp.keyH.enterPressed){
        gp.gameState = gp.dialogueState;
        gp.npc[i].speak();
      }
    }
    gp.keyH.enterPressed = false;
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
