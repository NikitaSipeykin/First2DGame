package tile_interactive;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.awt.*;
import java.util.Random;

public class IT_DestructibleWall extends InteractiveTile{
  GamePanel gp;

  public IT_DestructibleWall(GamePanel gp, int col, int row) {
    super(gp, col, row);
    this.gp = gp;

    this.worldX = gp.tileSize * col;
    this.worldY = gp.tileSize * row;

    down1 = setup("/tiles_interactive/destructiblewall", gp.tileSize, gp.tileSize);
    destructible = true;
    life = 4;
  }

  @Override
  public boolean isCorrectItem(Entity entity) {
    boolean isCorrectItem = false;

    if (entity.currentWeapon.type == type_pickaxe){
      isCorrectItem = true;
    }

    return isCorrectItem;
  }

  @Override
  public void playSE() {
    gp.playSE(20);
  }

  @Override
  public InteractiveTile getDestroyedForm() {
    InteractiveTile tile = null;

    return tile;
  }

  @Override
  public Color getParticleColor(){
    Color color = new Color(65, 65, 65);
    return color;
  }

  @Override
  public int getParticleSize(){
    int size = 6; //6 pixels
    return size;
  }

  @Override
  public int getParticleSpeed(){
    int speed = 1;
    return speed;
  }

  @Override
  public int getParticleMaxLife(){
    int maxLife = 20;
    return maxLife;
  }

//  public void checkDrop() {
//    //cast a die
//    int i = new Random().nextInt(100)+1;
//
//    //set the wall drop
//    if(i < 80){
//      dropItem(new OBJ_Coin_Bronze(gp));
//    }
//  }
}