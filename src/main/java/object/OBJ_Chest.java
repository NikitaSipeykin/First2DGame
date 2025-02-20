package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {
  GamePanel gp;


  public OBJ_Chest(GamePanel gp) {
    super(gp);
    this.gp = gp;

    type = type_obstacle;
    name = "Chest";
    image = setup("/objects/chest", gp.tileSize, gp.tileSize);
    image2 = setup("/objects/chest_opened", gp.tileSize, gp.tileSize);
    down1 = image;
    collision = true;

    solidArea.x = 4;
    solidArea.y = 16;
    solidArea.width = 40;
    solidArea.height = 32;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
  }

  @Override
  public void setLoot(Entity loot){
    this.loot = loot;
  }

  @Override
  public void interact() {
    gp.gameState = gp.dialogueState;

    if (!opened){
      gp.playSE(3);

      StringBuilder sb = new StringBuilder();
      sb.append("You open the chest and find a \n" + loot.name + " inside!");

      if(!gp.player.canObtainItem(loot)){
        sb.append("\n...But you can't carry any more!");
      }
      else {
        sb.append("\nYou obtain the " + loot.name + "!");
        down1 = image2;
        opened = true;
      }
      gp.ui.currentDialogue = sb.toString();
    }
    else {
      gp.ui.currentDialogue = "it's empty.";
    }
  }
}
