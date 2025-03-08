package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_RedSlime extends Entity {
  GamePanel gp;

  public MON_RedSlime(GamePanel gp) {
    super(gp);
    this.gp = gp;

    type = type_monster;
    name = "Red Slime";
    defaultSpeed = 2;
    speed = defaultSpeed;
    maxLife = 8;
    life = maxLife;
    attack = 7;
    defense = 0;
    exp = 5;
    projectile = new OBJ_Rock(gp);

    solidArea.x = 3;
    solidArea.y = 18;
    solidArea.width = 42;
    solidArea.height = 30;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;

    getImage();
  }

  public void getImage(){
    up1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
    up2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);
    down1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
    down2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);
    left1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
    left2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);
    right1 = setup("/monster/redslime_down_1", gp.tileSize, gp.tileSize);
    right2 = setup("/monster/redslime_down_2", gp.tileSize, gp.tileSize);
  }

  public void setAction(){
    if (onPath){
      //check if it stops chasing
      checkStopChasingOrNot(gp.player, 15, 100);

      //search the direction to go
      searchPath(getGoalCol(gp.player), getGoalRow(gp.player));

      //check if it shoots a projectile
      checkShootOrNot(200, 30);

    }else {
      //check if it starts chasing
      checkStartChasingOrNot(gp.player, 5, 100);

      //get a random direction
      getRandomDirection(120);
    }
  }

  @Override
  public void damageReaction() {
    actionLockCounter = 0;
    //direction = gp.player.direction;
    onPath = true;
  }

  @Override
  public void checkDrop() {
    //cast a die
    int i = new Random().nextInt(100)+1;

    //set the monster drop
    if(i < 50){
      dropItem(new OBJ_Coin_Bronze(gp, 13));
    }
    if (i >= 50 && i < 75){
      dropItem(new OBJ_Heart(gp));
      dropItem(new OBJ_Coin_Bronze(gp, 15));
    }
    if (i >= 75 ){
      dropItem(new OBJ_ManaCrystal(gp));
      dropItem(new OBJ_Coin_Bronze(gp, 16));
    }
  }
}
