package main;


import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
  GamePanel gp;

  public AssetSetter(GamePanel gp) {
    this.gp = gp;
  }

  public void setObject(){

    gp.obj[0] = new OBJ_Key(gp);
    gp.obj[0].worldX = gp.tileSize*25;
    gp.obj[0].worldY = gp.tileSize*19;
  }

  public void setNPC(){
    gp.npc[0] = new NPC_OldMan(gp);
    gp.npc[0].worldX = gp.tileSize*21;
    gp.npc[0].worldY = gp.tileSize*21;
  }

  public void setMonster(){
    int i = 0;

    gp.monster[i] = new MON_GreenSlime(gp);
    gp.monster[i].worldX = gp.tileSize * 23;
    gp.monster[i].worldY = gp.tileSize * 36;
    i++;

    gp.monster[i] = new MON_GreenSlime(gp);
    gp.monster[i].worldX = gp.tileSize * 23;
    gp.monster[i].worldY = gp.tileSize * 37;
    i++;

    gp.monster[i] = new MON_GreenSlime(gp);
    gp.monster[i].worldX = gp.tileSize * 11;
    gp.monster[i].worldY = gp.tileSize * 10;
    i++;

    gp.monster[i] = new MON_GreenSlime(gp);
    gp.monster[i].worldX = gp.tileSize * 11;
    gp.monster[i].worldY = gp.tileSize * 11;
    i++;

    gp.monster[i] = new MON_GreenSlime(gp);
    gp.monster[i].worldX = gp.tileSize * 23;
    gp.monster[i].worldY = gp.tileSize * 35;
    i++;
  }
}
