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

  private void setDialogue() {
    dialogues[0][0] = "You open the chest and find a \n" + loot.name + " inside!";
    dialogues[0][1] = "...But you can't carry any more!";
    dialogues[1][0] = "You open the chest and find a \n" + loot.name + " inside!";
    dialogues[1][1] = "You obtain the " + loot.name + "!";
    dialogues[2][0] = "it's empty.";
  }

  @Override
  public void setLoot(Entity loot){
    this.loot = loot;
    setDialogue();
  }

  @Override
  public void interact() {
    if (!opened){
      gp.playSE(3);

      if(!gp.player.canObtainItem(loot)){
        startDialogue(this, 0);
      }
      else {
        startDialogue(this, 1);
        down1 = image2;
        opened = true;
      }
    }
    else {
      startDialogue(this, 2);
    }
  }
}
