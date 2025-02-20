package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
  //player stats
  int level;
  int maxLife;
  int life;
  int maxMana;
  int mana;
  int strength;
  int dexterity;
  int exp;
  int nextLevelExp;
  int coin;

  //player inventory
  ArrayList<String> itemNames = new ArrayList<>();
  ArrayList<Integer> itemAmount = new ArrayList<>();
  int currentWeaponSlot;
  int currentShieldSlot;

  //object on map
  String[][] mapObjectNames;
  int[][] mapObjectWorldX;
  int[][] mapObjectWorldY;
  String[][] mapObjectLootNames;
  boolean[][] mapObjectOpened;
}
