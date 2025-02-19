package data;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.io.*;

public class SaveLoad {
  GamePanel gp;

  public SaveLoad(GamePanel gp) {
    this.gp = gp;
  }

  public Entity getObject(String itemName){
    Entity obj = null;
    switch (itemName){
      //todo: when you kill enemy and they drop loot like mana cristal ext. you get NPException (Fix it!!!)
      case "Woodcutter's Axe" : obj = new OBJ_Axe(gp); break;
      case "Boots" : obj = new OBJ_Boots(gp); break;
      case "Key" : obj = new OBJ_Key(gp); break;
      case "Lantern" : obj = new OBJ_Lantern(gp); break;
      case "Red Potion" : obj = new OBJ_Potion_Red(gp); break;
      case "Blue Shield" : obj = new OBJ_Shield_Blue(gp); break;
      case "Wood Shield" : obj = new OBJ_Shield_Wood(gp); break;
      case "Normal Sword" : obj = new OBJ_Sword_Normal(gp); break;
      case "Tent" : obj = new OBJ_Tent(gp); break;
      case "Door" : obj = new OBJ_Door(gp); break;
      case "Chest" : obj = new OBJ_Chest(gp); break;
      case "Bronze Coin": obj = new OBJ_Coin_Bronze(gp); break;
    }
    return obj;
  }

  public void save(){
    try {
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
      DataStorage ds = new DataStorage();

      //player stats
      ds.level = gp.player.level;
      ds.maxLife = gp.player.maxLife;
      ds.life = gp.player.life;
      ds.maxMana = gp.player.maxMana;
      ds.mana = gp.player.mana;
      ds.strength = gp.player.strength;
      ds.dexterity = gp.player.dexterity;
      ds.exp = gp.player.exp;
      ds.nextLevelExp = gp.player.nextLevelExp;
      ds.coin = gp.player.coin;

      //player inventory
      for (int i = 0; i < gp.player.inventory.size(); i++) {
        ds.itemNames.add(gp.player.inventory.get(i).name);
        ds.itemAmount.add(gp.player.inventory.get(i).amount);
      }

      //player equipment
      ds.currentWeaponSlot = gp.player.getCurrentWeaponSLot();
      ds.currentShieldSlot = gp.player.getCurrentShieldSLot();

      //objects on map
      ds.mapObjectNames = new String[gp.maxMap][gp.obj[1].length];
      ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
      ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
      ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[1].length];
      ds.mapObjectOpened = new  boolean[gp.maxMap][gp.obj[1].length];

      for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
        for (int i = 0; i < gp.obj[1].length; i++) {
          if (gp.obj[mapNum][i] == null){
            ds.mapObjectNames[mapNum][i] = "NA";
          }
          else {
            ds.mapObjectNames[mapNum][i] = gp.obj[mapNum][i].name;
            ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
            ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
            if (gp.obj[mapNum][i].loot != null){
              ds.mapObjectLootNames[mapNum][i] = gp.obj[mapNum][i].loot.name;
            }
            ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
          }
        }
      }

      //wright the dataStorage object
      oos.writeObject(ds);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void load(){
    try {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));

      //read the DataStorage object
      DataStorage ds = (DataStorage) ois.readObject();

      //player state
      gp.player.level = ds.level;
      gp.player.maxLife = ds.maxLife;
      gp.player.life = ds.life;
      gp.player.maxMana = ds.maxMana;
      gp.player.mana = ds.mana;
      gp.player.strength = ds.strength;
      gp.player.dexterity = ds.dexterity;
      gp.player.exp = ds.exp;
      gp.player.nextLevelExp = ds.nextLevelExp;
      gp.player.coin = ds.coin;

      //player inventory
      gp.player.inventory.clear();
      for (int i = 0; i < ds.itemNames.size(); i++) {
        gp.player.inventory.add(getObject(ds.itemNames.get(i)));
        gp.player.inventory.get(i).amount = ds.itemAmount.get(i);
      }

      //player equipment
      gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
      gp.player.currentShield =gp.player.inventory.get(ds.currentShieldSlot);
      gp.player.getAttack();
      gp.player.getDefense();
      gp.player.getAttackImage();

      //objects on map
      for (int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
        for (int i = 0; i < gp.obj[1].length; i++) {
          if (ds.mapObjectNames[mapNum][i].equals("NA")){
            gp.obj[mapNum][i] = null;
          }
          else {
            gp.obj[mapNum][i] = getObject(ds.mapObjectNames[mapNum][i]);
            gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
            gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
            if (ds.mapObjectLootNames[mapNum][i] != null){
              gp.obj[mapNum][i].loot = getObject(ds.mapObjectLootNames[mapNum][i]);
            }
            gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
            if (gp.obj[mapNum][i].opened){
              gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
            }
          }
        }
      }

    }catch (Exception e){
      e.printStackTrace();
    }
  }
}
