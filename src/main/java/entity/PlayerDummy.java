package entity;

import main.GamePanel;

public class PlayerDummy extends Entity{
  public static final String npcName = "Dummy";
  public PlayerDummy(GamePanel gp) {
    super(gp);

    name = npcName;
    getImage();
  }

  public void getImage(){
    up1 = setup("/player/boyUp1", gp.tileSize, gp.tileSize);
    up2 = setup("/player/boyUp2", gp.tileSize, gp.tileSize);
    down1 = setup("/player/boyDown1", gp.tileSize, gp.tileSize);
    down2 = setup("/player/boyDown2", gp.tileSize, gp.tileSize);
    left1 = setup("/player/boyLeft1", gp.tileSize, gp.tileSize);
    left2 = setup("/player/boyLeft2", gp.tileSize, gp.tileSize);
    right1 = setup("/player/boyRight1", gp.tileSize, gp.tileSize);
    right2 = setup("/player/boyRight2", gp.tileSize, gp.tileSize);
  }
}
