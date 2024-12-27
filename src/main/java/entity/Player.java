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

  public Player(GamePanel gp, KeyHandler keyH) {
    super(gp);
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

    attackArea.width = 36;
    attackArea.height = 36;

    setDefaultValues();
    getPlayerImage();
    getPlayerAttackImage();
  }

  public void setDefaultValues(){
    worldX = gp.tileSize * 23;
    worldY = gp.tileSize * 21;
//    worldX = gp.tileSize * 10;
//    worldY = gp.tileSize * 13;
    speed = 4;
    direction = "down";

    //player status
    maxLife = 6;
    life = maxLife;
  }

  public void getPlayerImage(){
    up1 = setup("/player/boyUp1", gp.tileSize, gp.tileSize);
    up2 = setup("/player/boyUp2", gp.tileSize, gp.tileSize);
    down1 = setup("/player/boyDown1", gp.tileSize, gp.tileSize);
    down2 = setup("/player/boyDown2", gp.tileSize, gp.tileSize);
    left1 = setup("/player/boyLeft1", gp.tileSize, gp.tileSize);
    left2 = setup("/player/boyLeft2", gp.tileSize, gp.tileSize);
    right1 = setup("/player/boyRight1", gp.tileSize, gp.tileSize);
    right2 = setup("/player/boyRight2", gp.tileSize, gp.tileSize);
  }

  public void getPlayerAttackImage(){
    attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
    attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
    attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
    attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
    attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
    attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
    attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
    attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
  }

  public void update(){
    if (attacking){
      attacking();
    }else if(keyH.rightPressed || keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.enterPressed){
        if(keyH.upPressed) {
          direction = "up";
        } else if (keyH.downPressed) {
          direction = "down";
        } else if (keyH.leftPressed) {
          direction = "left";
        } else if (keyH.rightPressed) {
          direction = "right";
        }

        //check tile collision
        collisionON = false;
        gp.cChecker.checkTile(this);

        //check object collision
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        //check npc collision
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

        //check monster collision
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        contactMonster(monsterIndex);

        //check event
        gp.eHandler.checkEvent();


        //if collision is false player can't move
        if (!collisionON && !keyH.enterPressed){
          switch (direction){
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
          }
        }

        gp.keyH.enterPressed = false;

        spriteCounter++;
        if(spriteCounter > 10){
          if (spriteNumber == 1) {
            spriteNumber = 2;
          } else if (spriteNumber == 2) {
            spriteNumber = 1;
          }
          spriteCounter = 0;
        }else {
          standCounter++;
          if (standCounter == 20){
            spriteNumber = 1;
            standCounter = 0;
          }
        }
      }


      //this needs to be outside of key if statement!
    if(invincible){
      invincibleCounter++;
      if (invincibleCounter > 60){
        invincible = false;
        invincibleCounter = 0;
      }
    }
  }

  public void attacking(){
    spriteCounter++;
    if (spriteCounter <= 5){
      spriteNumber = 1;
    }
    if (spriteCounter > 5 && spriteCounter <= 25){
      spriteNumber = 2;

      //sve the current worldX, worldY, solidArea
      int currentWorldX = worldX;
      int currentWorldY = worldY;
      int solidAreaWidth = solidArea.width;
      int solidAreaHeight = solidArea.height;

      //adjust player's worldX/Y for the attackArea
      switch (direction){
        case "up": worldY -= attackArea.height; break;
        case "down": worldY += attackArea.height; break;
        case "left": worldX -= attackArea.width; break;
        case "right": worldX += attackArea.width; break;
      }

      //attackArea becomes solidArea
      solidArea.width = attackArea.width;
      solidArea.height = attackArea.height;

      //check monster collision with the updated worldX/Y and solidArea
      int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
      damageMonster(monsterIndex);

      //after checking the collision restore the original data
      worldX = currentWorldX;
      worldY = currentWorldY;
      solidArea.width = solidAreaWidth;
      solidArea.height = solidAreaHeight;
    }
    if (spriteCounter > 25){
      spriteNumber = 1;
      spriteCounter = 0;
      attacking = false;
    }
  }

  public void pickUpObject(int i){
    if (i != 999){
    }
  }

  public void interactNPC(int i){
    if (gp.keyH.enterPressed){
      if (i != 999){
          gp.gameState = gp.dialogueState;
          gp.npc[i].speak();
      }else {
        gp.playSE(7);
          attacking = true;
      }
    }

  }

  public void contactMonster(int i){
    if (i != 999){
      if (!invincible){
        gp.playSE(6);
        life -= 1;
        invincible = true;
      }
    }

  }

  public void damageMonster(int i){
    if (i != 999){
      if (!gp.monster[i].invincible){
        gp.playSE(5);
        gp.monster[i].life -= 1;
        gp.monster[i].invincible = true;
        gp.monster[i].damageReaction();

        if (gp.monster[i].life <= 0){
          gp.monster[i].dying = true;
        }
      }
    }
  }

  public void draw(Graphics2D g2){
//    g2.setColor(Color.white);
//    g2.fillRect(x, y, gp.tileSize, gp.tileSize);

    BufferedImage image = null;
    int tempScreenX = screenX;
    int tempScreenY = screenY;

    switch (direction){
      case "up":
        if (!attacking){
          if(spriteNumber == 1){image = up1;}
          if (spriteNumber == 2) {image = up2;}
        }
        if (attacking){
          tempScreenY = screenY - gp.tileSize;
          if(spriteNumber == 1){image = attackUp1;}
          if (spriteNumber == 2) {image = attackUp2;}
          }
        break;
      case "down":
        if (!attacking){
          if(spriteNumber == 1){image = down1;}
          if (spriteNumber == 2) {image = down2;}
        }
        if (attacking){
          if(spriteNumber == 1){image = attackDown1;}
          if (spriteNumber == 2) {image = attackDown2;}
        }
        break;
      case "left":
        if (!attacking){
          if(spriteNumber == 1){image = left1;}
          if (spriteNumber == 2) {image = left2;}
        }
        if (attacking){
          tempScreenX = screenX - gp.tileSize;
          if(spriteNumber == 1){image = attackLeft1;}
          if (spriteNumber == 2) {image = attackLeft2;}
        }
        break;
      case "right":
        if (!attacking){
          if(spriteNumber == 1){image = right1;}
          if (spriteNumber == 2) {image = right2;}
        }
        if (attacking){
          if(spriteNumber == 1){image = attackRight1;}
          if (spriteNumber == 2) {image = attackRight2;}
        }
        break;
    }

    if (invincible){
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
    }
    g2.drawImage(image, tempScreenX, tempScreenY,null);

    //reset alpha
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    //debug
//    g2.setFont(new Font("Arial", Font.PLAIN, 26));
//    g2.setColor(Color.white);
//    g2.drawString("Invincible:" + invincibleCounter, 10, 400);
  }
}
