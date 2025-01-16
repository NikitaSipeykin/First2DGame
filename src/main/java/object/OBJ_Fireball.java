package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile {
  GamePanel gp;
  public OBJ_Fireball(GamePanel gp) {
    super(gp);
    this.gp = gp;

    name = "Fireball";
    speed = 5;
    maxLife = 80;
    life = maxLife;
    attack = 2;
    useCost = 1;
    alive = false;
    getImage();
  }

  private void getImage() {
    up1 = setup("/projectile/fireball_up_1", gp.tileSize, gp.tileSize*2);
    up2 = setup("/projectile/fireball_up_2", gp.tileSize, gp.tileSize*2);
    down1 = setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize*2);
    down2 = setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize*2);
    left1 = setup("/projectile/fireball_left_1", gp.tileSize*2, gp.tileSize);
    left2 = setup("/projectile/fireball_left_2", gp.tileSize*2, gp.tileSize);
    right1 = setup("/projectile/fireball_right_1", gp.tileSize*2, gp.tileSize);
    right2 = setup("/projectile/fireball_right_2", gp.tileSize*2, gp.tileSize);
  }
}
