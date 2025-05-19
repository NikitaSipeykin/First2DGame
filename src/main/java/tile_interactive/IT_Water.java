package tile_interactive;

import main.GamePanel;

import java.awt.image.BufferedImage;

public class IT_Water extends InteractiveTile{
  GamePanel gp;

  //water template
//  public BufferedImage upLeftCorner_1 = setup("/tiles/my version/water/4", gp.tileSize, gp.tileSize);
//  public BufferedImage upLeftCorner_2 = setup("/tiles/my version/water/4", gp.tileSize, gp.tileSize);
//  public BufferedImage upCenter_1 = setup("/tiles/my version/water/4", gp.tileSize, gp.tileSize);
//  public BufferedImage upCenter2 = setup("/tiles/my version/water/4", gp.tileSize, gp.tileSize);
//  public BufferedImage upRightCorner_1 = setup("/tiles/my version/water/4", gp.tileSize, gp.tileSize);
//  public BufferedImage upRightCorner_2 = setup("/tiles/my version/water/4", gp.tileSize, gp.tileSize);
//  public BufferedImage leftCenter_1 = setup("/tiles/my version/water/4", gp.tileSize, gp.tileSize);
//  public BufferedImage leftCenter_2 = setup("/tiles/my version/water/4", gp.tileSize, gp.tileSize);
//  public BufferedImage center = setup("/tiles/my version/water/4", gp.tileSize, gp.tileSize);
//  public BufferedImage rightCenter_1 = setup("/tiles/my version/water/4", gp.tileSize, gp.tileSize);
//  public BufferedImage rightCenter_2 = setup("/tiles/my version/water/4", gp.tileSize, gp.tileSize);
//  public BufferedImage downLeftCorner = setup("/tiles/my version/water/4", gp.tileSize, gp.tileSize);
//  public BufferedImage downCenter = setup("/tiles/my version/water/4", gp.tileSize, gp.tileSize);
//  public BufferedImage downRightCorner = setup("/tiles/my version/water/4", gp.tileSize, gp.tileSize);



  public IT_Water(GamePanel gp, int col, int row, int tileNumber) {
    super(gp, col, row);
    this.gp = gp;

    this.worldX = gp.tileSize * col;
    this.worldY = gp.tileSize * row;
    //up left corner
    if (tileNumber == 1 || tileNumber == 5){
      down1 = setup("/tiles/my version/water/4", gp.tileSize, gp.tileSize);
      down2 = setup("/tiles/my version/water/5", gp.tileSize, gp.tileSize);
      animated = true;
    }
    //up right corner
    if (tileNumber == 4 || tileNumber == 7){
      down1 = setup("/tiles/my version/water/10", gp.tileSize, gp.tileSize);
      down2 = setup("/tiles/my version/water/11", gp.tileSize, gp.tileSize);
      animated = true;
    }
    //up center
    if (tileNumber == 3 || tileNumber == 6){
      down1 = setup("/tiles/my version/water/8", gp.tileSize, gp.tileSize);
      down2 = setup("/tiles/my version/water/9", gp.tileSize, gp.tileSize);
      animated = true;
    }
    //left center
    if (tileNumber == 25 || tileNumber == 41){
      down1 = setup("/tiles/my version/water/32", gp.tileSize, gp.tileSize);
    }
    //right center
    if (tileNumber == 27 || tileNumber == 44){
      down1 = setup("/tiles/my version/water/31", gp.tileSize, gp.tileSize);
    }
    //down left corner
    if (tileNumber == 45 || tileNumber == 61){
      down1 = setup("/tiles/my version/water/28", gp.tileSize, gp.tileSize);
    }
    //down center
    if (tileNumber == 46 || tileNumber == 63){
      down1 = setup("/tiles/my version/water/29", gp.tileSize, gp.tileSize);
    }
    //down right corner
    if (tileNumber == 47 || tileNumber == 64){
      down1 = setup("/tiles/my version/water/30", gp.tileSize, gp.tileSize);
    }

    //left slope
    if (tileNumber == 21){
      down1 = setup("/tiles/my version/water/12", gp.tileSize, gp.tileSize);
      down2 = setup("/tiles/my version/water/13", gp.tileSize, gp.tileSize);
      animated = true;
    }
    //right slope
    if (tileNumber == 24){
      down1 = setup("/tiles/my version/water/14", gp.tileSize, gp.tileSize);
      down2 = setup("/tiles/my version/water/15", gp.tileSize, gp.tileSize);
      animated = true;
    }
    //up slope
    if (tileNumber == 2){
      down1 = setup("/tiles/my version/water/6", gp.tileSize, gp.tileSize);
      down2 = setup("/tiles/my version/water/7", gp.tileSize, gp.tileSize);
      animated = true;
    }
    //down slope
    if (tileNumber == 62){
      down1 = setup("/tiles/my version/water/20", gp.tileSize, gp.tileSize);
      down2 = setup("/tiles/my version/water/21", gp.tileSize, gp.tileSize);
      animated = true;
    }

    //left long slope (top)
    if (tileNumber == 11){
      down1 = setup("/tiles/my version/water/22", gp.tileSize, gp.tileSize);
      down2 = setup("/tiles/my version/water/23", gp.tileSize, gp.tileSize);
      animated = true;
    }
    //left long slope (center)
    if (tileNumber == 31){
      down1 = setup("/tiles/my version/water/26", gp.tileSize, gp.tileSize);
      down2 = setup("/tiles/my version/water/27", gp.tileSize, gp.tileSize);
      animated = true;
    }
    //left long slope (bottom)
    if (tileNumber == 51){
      down1 = setup("/tiles/my version/water/24", gp.tileSize, gp.tileSize);
      down2 = setup("/tiles/my version/water/25", gp.tileSize, gp.tileSize);
      animated = true;
    }

    //right long slope (top)
    if (tileNumber == 82){
      down1 = setup("/tiles/my version/water/33", gp.tileSize, gp.tileSize);
      down2 = setup("/tiles/my version/water/34", gp.tileSize, gp.tileSize);
      animated = true;
    }
    //right long slope (center)
    if (tileNumber == 81){
      down1 = setup("/tiles/my version/water/35", gp.tileSize, gp.tileSize);
      down2 = setup("/tiles/my version/water/36", gp.tileSize, gp.tileSize);
      animated = true;
    }
    //right long slope (bottom)
    if (tileNumber == 80){
      down1 = setup("/tiles/my version/water/37", gp.tileSize, gp.tileSize);
      down2 = setup("/tiles/my version/water/38", gp.tileSize, gp.tileSize);
      animated = true;
    }


    //up left corner (earth)
    if(tileNumber == 12 || tileNumber == 22){
      down1 = setup("/tiles/my version/water/3", gp.tileSize, gp.tileSize);
    }
    //up right corner (earth)
    if(tileNumber == 13 || tileNumber == 23){
      down1 = setup("/tiles/my version/water/2", gp.tileSize, gp.tileSize);
    }
    //down left corner (earth)
    if (tileNumber == 42 || tileNumber == 32 || tileNumber == 72){
      down1 = setup("/tiles/my version/water/16", gp.tileSize, gp.tileSize);
      down2 = setup("/tiles/my version/water/17", gp.tileSize, gp.tileSize);
      animated = true;
    }
    //down right corner (earth)
    if (tileNumber == 43 || tileNumber == 33 || tileNumber == 73){
      down1 = setup("/tiles/my version/water/18", gp.tileSize, gp.tileSize);
      down2 = setup("/tiles/my version/water/19", gp.tileSize, gp.tileSize);
      animated = true;
    }

    //center (without borders)
    if (tileNumber == 26 || tileNumber == 8 || tileNumber == 9 || tileNumber == 10 || tileNumber == 14 ||
        tileNumber == 15 || tileNumber == 28 || tileNumber == 30 || tileNumber == 34 || tileNumber == 35 ||
        tileNumber == 48 || tileNumber == 49 || tileNumber == 50 || tileNumber == 52 || tileNumber == 53 ||
        tileNumber == 54 || tileNumber == 55 || tileNumber == 68){
      down1 = setup("/tiles/my version/water/1", gp.tileSize, gp.tileSize);
    }
  }

  @Override
  public void update() {
    spriteCounter++;
    if (spriteCounter > 48){
      if (spriteNumber == 1){
        spriteNumber = 2;
      } else if (spriteNumber == 2) {
        spriteNumber = 1;
      }
      spriteCounter = 0;
    }
  }
}
