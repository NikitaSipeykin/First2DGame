package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Entity {
  GamePanel gp;

  public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
  public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
  public BufferedImage image, image2, image3;
  public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
  public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
  public int solidAreaDefaultX, solidAreaDefaultY;
  public boolean collision = false;
  String[] dialogues = new String[20];

  //state
  public int worldX, worldY;
  public String direction = "down";
  public int spriteNumber = 1;
  int dialogueIndex = 0;
  public boolean collisionON = false;
  public boolean invincible = false;
  boolean attacking = false;
  public boolean alive = true;
  public boolean dying = false;
  boolean hpBarOn = false;
  public boolean onPath = false;
  public boolean knockBack = false;

  //counter
  public int spriteCounter = 0;
  public int actionLockCounter = 0;
  public int invincibleCounter = 0;
  public int shotAvailableCounter = 0;
  int dyingCounter = 0;
  int hpBarCounter = 0;
  int knockBackCounter = 0;

  //character attributes
  public int speed;
  public int defaultSpeed;
  public String name;
  public int maxLife;
  public int life;
  public int maxMana;
  public int mana;
  public int ammo;
  public int level;
  public int strength;
  public int dexterity;
  public int attack;
  public int defense;
  public int exp;
  public int nextLevelExp;
  public int coin;
  public Entity currentWeapon;
  public Entity currentShield;
  public Entity currentLight;
  public Projectile projectile;

  //item attributes
  public ArrayList<Entity> inventory = new ArrayList<>();
  public final int maxInventorySize = 20;
  public int value;
  public int attackValue;
  public int defenseValue;
  public String description = "";
  public int useCost;
  public int price;
  public int knockBackPower = 0;
  public boolean stackable  = false;
  public int amount = 1;
  public int lightRadius;

  //type
  public int type; // 0 = player; 1 = npc; 2 = monster
  public final int type_player = 0;
  public final int type_npc = 1;
  public final int type_monster = 2;
  public final int type_sword = 3;
  public final int type_axe = 4;
  public final int type_shield = 5;
  public final int type_consumable = 6;
  public final int type_pickupOnly = 7;
  public final int type_obstacle = 8;
  public final int type_light = 9;

  public Entity(GamePanel gp) {
    this.gp = gp;
  }

  public int getLeftX(){
    return worldX + solidArea.x;
  }

  public int getRightX(){
    return worldX + solidArea.x + solidArea.width;
  }

  public int getTopY(){
    return worldY + solidArea.y;
  }

  public int getBottomY(){
    return worldY + solidArea.y + solidArea.height;
  }

  public int getCol(){
    return (worldX + solidArea.x) / gp.tileSize;
  }

  public int getRow(){
    return (worldY + solidArea.y) / gp.tileSize;
  }

  public void setAction(){}

  public void damageReaction(){}

  public void speak(){
    if (dialogues[dialogueIndex] == null){
      dialogueIndex = 0;
    }
    gp.ui.currentDialogue = dialogues[dialogueIndex];
    dialogueIndex++;

    switch (gp.player.direction){
      case "up": direction = "down"; break;
      case "down": direction = "up"; break;
      case "left": direction = "right"; break;
      case "right": direction = "left"; break;
    }
  }

  public void interact(){

  }

  public boolean use(Entity entity){
    return false;
  }

  public void checkDrop(){}

  public void dropItem(Entity droppedItem){
    for (int i = 0; i < gp.obj[1].length; i++) {
      if (gp.obj[gp.currentMap][i] == null){
        gp.obj[gp.currentMap][i] = droppedItem;
        gp.obj[gp.currentMap][i].worldX = worldX; //dead monster's worldX
        gp.obj[gp.currentMap][i].worldY = worldY; //dead monster's worldY
        break;
      }
    }
  }

  public Color getParticleColor(){
    Color color = null;
    return color;
  }

  public int getParticleSize(){
    int size = 0; //6 pixels
    return size;
  }

  public int getParticleSpeed(){
    int speed = 0;
    return speed;
  }

  public int getParticleMaxLife(){
    int maxLife = 0;
    return maxLife;
  }

  public void generateParticle(Entity generator, Entity target){
    Color color = generator.getParticleColor();
    int size = generator.getParticleSize();
    int speed = generator.getParticleSpeed();
    int maxLife = generator.getParticleMaxLife();

    Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
    Particle p2 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
    Particle p3 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
    Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);

    gp.particleList.add(p1);
    gp.particleList.add(p2);
    gp.particleList.add(p3);
    gp.particleList.add(p4);
  }

  public void checkCollision(){

    collisionON = false;
    gp.cChecker.checkTile(this);
    gp.cChecker.checkObject(this, false);
    gp.cChecker.checkEntity(this, gp.npc);
    gp.cChecker.checkEntity(this, gp.monster);
    gp.cChecker.checkEntity(this, gp.iTile);
    boolean contactPlayer = gp.cChecker.checkPlayer(this);

    if (this.type == type_monster && contactPlayer){
      damagePlayer(attack);
    }
  }

  public void update(){
    if (knockBack){
      checkCollision();

      if (collisionON){
        knockBackCounter = 0;
        knockBack = false;
        speed = defaultSpeed;
      } else if (!collisionON) {
        switch (gp.player.direction){
          case "up": worldY -= speed; break;
          case "down": worldY += speed; break;
          case "left": worldX -= speed; break;
          case "right": worldX += speed; break;
        }
      }
      knockBackCounter++;
      if (knockBackCounter == 10){
        knockBackCounter = 0;
        knockBack = false;
        speed = defaultSpeed;
      }
    }
    else {
      setAction();
      checkCollision();

      if (!collisionON){
        switch (direction){
          case "up": worldY -= speed; break;
          case "down": worldY += speed; break;
          case "left": worldX -= speed; break;
          case "right": worldX += speed; break;
        }
      }
    }

    spriteCounter++;
    if(spriteCounter > 24){
      if (spriteNumber == 1) {
        spriteNumber = 2;
      } else if (spriteNumber == 2) {
        spriteNumber = 1;
      }
      spriteCounter = 0;
    }

    if(invincible){
      invincibleCounter++;
      if (invincibleCounter > 40){
        invincible = false;
        invincibleCounter = 0;
      }
    }
    if (shotAvailableCounter < 30){
      shotAvailableCounter++;
    }
  }

  public void damagePlayer(int attack){
    if (!gp.player.invincible){
      //we can give damage
      gp.playSE(6);


      int damage = attack - gp.player.defense;
      if (damage < 0){
        damage = 0;
      }

      gp.player.life -= damage;
      gp.player.invincible = true;
    }
  }

  public void draw(Graphics2D g2){
    BufferedImage image = null;

    int screenX = worldX - gp.player.worldX + gp.player.screenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
        worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
        worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
        worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

      switch (direction){
        case "up":
          if(spriteNumber == 1){image = up1;}
          if (spriteNumber == 2) {image = up2;}
          break;
        case "down":
          if(spriteNumber == 1){image = down1;}
          if (spriteNumber == 2) {image = down2;}
          break;
        case "left":
          if(spriteNumber == 1){image = left1;}
          if (spriteNumber == 2) {image = left2;}
          break;
        case "right":
          if(spriteNumber == 1){image = right1;}
          if (spriteNumber == 2) {image = right2;}
          break;
      }

      //monster HP bar
      if (type == type_monster && hpBarOn){
        double oneScale = (double)gp.tileSize/maxLife;
        double hpBarValue = oneScale * life;

        g2.setColor(new Color(35, 35, 35));
        g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);

        g2.setColor(new Color(255, 0, 30));
        g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

        hpBarCounter++;

        if (hpBarCounter > 600){
          hpBarCounter = 0;
          hpBarOn = false;
        }
      }


      if (invincible){
        hpBarOn = true;
        hpBarCounter = 0;
        changeAlpha(g2, 0.4f);
      }
      if (dying){
        dyingAnimation(g2);
      }

      g2.drawImage(image, screenX, screenY,null);

      //reset alpha
      changeAlpha(g2, 1f);
    }
  }

  public void dyingAnimation(Graphics2D g2){
    dyingCounter++;
    int i = 5;

    if (dyingCounter <= i){changeAlpha(g2, 0f);}
    if (dyingCounter > i && dyingCounter <= i*2){changeAlpha(g2, 1f);}
    if (dyingCounter > i*2 && dyingCounter <= i*3){changeAlpha(g2, 0f);}
    if (dyingCounter > i*3 && dyingCounter <= i*4){changeAlpha(g2, 1f);}
    if (dyingCounter > i*4 && dyingCounter <= i*5){changeAlpha(g2, 0f);}
    if (dyingCounter > i*5 && dyingCounter <= i*6){changeAlpha(g2, 1f);}
    if (dyingCounter > i*6 && dyingCounter <= i*7){changeAlpha(g2, 0f);}
    if (dyingCounter > i*7 && dyingCounter <= i*8){changeAlpha(g2, 1f);}
    if (dyingCounter > i*8){
      alive = false;
    }
  }

  public void changeAlpha(Graphics2D g2, float alphaValue){
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
  }

  public BufferedImage setup(String imagePath, int width, int height){
    UtilityTool uTool = new UtilityTool();
    BufferedImage image = null;

    try{
      image = ImageIO.read(getClass().getResourceAsStream( imagePath + ".png"));
      image = uTool.scaleImage(image, width, height);
    }catch (IOException e){
      e.printStackTrace();
    }
    return image;
  }

  public void searchPath(int goalCol, int goalRow){
    int startCol = (worldX + solidArea.x)/gp.tileSize;
    int startRow = (worldY + solidArea.y)/gp.tileSize;

    gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow, this);

    if(gp.pFinder.search()){
       //next worldX & worldY
      int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
      int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

      //Entity's solidArea position
      int enLeftX = getLeftX();
      int enRightX = getRightX();
      int enTopY = getTopY();
      int enBottomY = getBottomY();

      if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
        direction = "up";
      }
      else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
        direction = "down";
      }
      else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize){
        //left or right
        if (enLeftX > nextX){
          direction = "left";
        }
        if (enLeftX < nextX){
          direction = "right";
        }
      }
      else if (enTopY > nextY && enLeftX > nextX){
        //up or left
        direction = "up";
        checkCollision();

        if (collisionON){
          direction = "left";
        }
      }
      else if (enTopY > nextY && enLeftX < nextX){
        //up or right
        direction = "up";
        checkCollision();

        if (collisionON){
          direction = "right";
        }
      }
      else if (enTopY < nextY && enLeftX > nextX){
        //down or left
        direction = "down";
        checkCollision();

        if (collisionON){
          direction = "left";
        }
      }
      else if (enTopY < nextY && enLeftX < nextX){
        //down or right
        direction = "down";
        checkCollision();

        if (collisionON){
          direction = "right";
        }
      }

//      //if reaches the goal, stop the search
//      int nextCol = gp.pFinder.pathList.get(0).col;
//      int nextRow = gp.pFinder.pathList.get(0).row;
//
//      if (nextCol == goalCol && nextRow == goalRow){
//        onPath = false;
//      }
    }
  }

  public int getDetected(Entity user, Entity[][] target, String targetName) {
    int index = 999;

    //check the surrounding object
    int nextWorldX = user.getLeftX();
    int nextWorldY = user.getTopY();

    switch (user.direction){
      case "up": nextWorldY = user.getTopY() - 1; break;
      case "down": nextWorldY = user.getBottomY() + 1; break;
      case "left": nextWorldX = user.getLeftX() - 1; break;
      case "right": nextWorldX = user.getRightX() + 1; break;
    }
    int col = nextWorldX / gp.tileSize;
    int row = nextWorldY / gp.tileSize;

    for (int i = 0; i < target[1].length; i++) {
      if (target[gp.currentMap][i] != null){
        if (target[gp.currentMap][i].getCol() == col && target[gp.currentMap][i].getRow() == row &&
            target[gp.currentMap][i].name.equals(targetName)){
          index = i;
          break;
        }
      }
    }
    return index;
  }
}
