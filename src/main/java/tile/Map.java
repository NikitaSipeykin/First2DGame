package tile;

import main.GamePanel;
import tile_interactive.IT_Water;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager{
  GamePanel gp;
  BufferedImage[] worldMap;
  public boolean miniMapOn = false;

  public Map(GamePanel gp) {
    super(gp);
    this.gp = gp;
    createWorldMap();
  }

  public void createWorldMap(){
    worldMap = new BufferedImage[gp.maxMap];
    int worldMapWith = gp.tileSize * gp.maxWorldCol;
    int worldMapHeight = gp.tileSize * gp.maxWorldRow;

    for (int i = 0; i < gp.maxMap; i++) {
      int waterTileCounter = 0;
      worldMap[i] = new BufferedImage(worldMapWith, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = (Graphics2D) worldMap[i].createGraphics();

      int col = 0;
      int row = 0;
      while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
        int tileNum = mapTileNum[i][col][row];
        int x = gp.tileSize * col;
        int y = gp.tileSize * row;
        g2.drawImage(tile[tileNum].image, x, y, null);

        if (tileNum > 0 && tileNum <= 15
            || tileNum > 20 && tileNum <= 28
            || tileNum > 29 && tileNum <= 35
            || tileNum > 40 && tileNum <= 55
            || tileNum > 60 && tileNum <= 64
            || tileNum > 71 && tileNum <= 73
            || tileNum > 79 && tileNum <= 82
            || tileNum == 68){
          gp.iWater[i][waterTileCounter] = new IT_Water(gp, col, row, tileNum);
          waterTileCounter++;
        }

        col++;
        if (col == gp.maxWorldCol){
          col = 0;
          row++;
        }
      }
      g2.dispose();
    }
  }

  public void drawFullMapScreen(Graphics2D g2){
    //background color
    g2.setColor(Color.black);
    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

    //draw map
    int width = 500;
    int height = 500;
    int x = gp.screenWidth/2 - width/2;
    int y = gp.screenHeight/2 - height/2;
    g2.drawImage(worldMap[gp.currentMap], x, y, width, height,null);

    //draw player
    double scale = (double) (gp.tileSize * gp.maxWorldCol)/width;
    int playerX = (int) (x + gp.player.worldX/scale);
    int playerY = (int)(y + gp.player.worldY/scale);
    int playerSize = (int)(gp.tileSize/scale);
    g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);

    //hint
    g2.setFont(gp.ui.maruMonica.deriveFont(32f));
    g2.setColor(Color.white);
    g2.drawString("Press M to close", 750, 550);
  }

  public void drawMiniMap(Graphics2D g2){
    if (miniMapOn){
      //draw map
      int width = 200;
      int height = 200;
      int x = gp.screenWidth - width - 50;
      int y = 50;
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
      g2.drawImage(worldMap[gp.currentMap], x, y, width, height,null);

      //draw player
      double scale = (double) (gp.tileSize * gp.maxWorldCol)/width;
      int playerX = (int) (x + gp.player.worldX/scale);
      int playerY = (int)(y + gp.player.worldY/scale);
      int playerSize = (int)(gp.tileSize/scale);
      g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
  }
}
