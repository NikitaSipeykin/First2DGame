package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
  GamePanel gp;
  public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, spacePressed;
  //debug
  public boolean showDebugText = false;
  public boolean godModeOn = false;

  public KeyHandler(GamePanel gp){
    this.gp = gp;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //we don't need this method
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();

    //title state
    if (gp.gameState == gp.titleState){
      titleState(code);
    }
    //play state
    else if (gp.gameState == gp.playState){
      playState(code);
    }

    //pause state
    else if (gp.gameState == gp.pauseState){
     pauseState(code);
    }

    //dialogue state
    else if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState){
      dialogueState(code);
    }

    //character state
    else if (gp.gameState == gp.characterState) {
      characterState(code);
    }

    //options state
    else if (gp.gameState == gp.optionsState) {
      optionsState(code);
    }

    //game over state
    else if (gp.gameState == gp.gameOverState) {
      gameOverState(code);
    }

    //trade state
    else if (gp.gameState == gp.tradeState) {
      tradeState(code);
    }

    //map state
    else if (gp.gameState == gp.mapState) {
      mapState(code);
    }
  }

  public void titleState(int code){
    if(code == KeyEvent.VK_W){
      gp.ui.commandNum--;
      if (gp.ui.commandNum < 0){
        gp.ui.commandNum = 2;
      }
    }
    if(code == KeyEvent.VK_S){
      gp.ui.commandNum++;
      if (gp.ui.commandNum > 2){
        gp.ui.commandNum = 0;
      }
    }
    if (code == KeyEvent.VK_ENTER){
      if (gp.ui.commandNum == 0){
        gp.gameState = gp.playState;
        gp.playMusic(0);
      }
      if (gp.ui.commandNum == 1){
        gp.saveLoad.load();
        gp.gameState = gp.playState;
        gp.playMusic(0);
      }
      if (gp.ui.commandNum == 2){
        System.exit(0);
      }
    }
  }

  public void playState(int code){
    if(code == KeyEvent.VK_W){
      upPressed = true;
    }
    if(code == KeyEvent.VK_S){
      downPressed = true;
    }
    if(code == KeyEvent.VK_A){
      leftPressed = true;
    }
    if(code == KeyEvent.VK_D){
      rightPressed = true;
    }
    if(code == KeyEvent.VK_P){
      gp.gameState = gp.pauseState;
    }
    if(code == KeyEvent.VK_C){
      gp.gameState = gp.characterState;
    }
    if(code == KeyEvent.VK_ENTER){
      enterPressed = true;
    }
    if(code == KeyEvent.VK_F){
      shotKeyPressed = true;
    }
    if(code == KeyEvent.VK_ESCAPE){
      gp.gameState = gp.optionsState;
    }
    if(code == KeyEvent.VK_M){
      gp.gameState = gp.mapState;
    }
    if(code == KeyEvent.VK_X){
      if (!gp.map.miniMapOn){
        gp.map.miniMapOn = true;
      }else {
        gp.map.miniMapOn = false;
      }
    }
    if(code == KeyEvent.VK_SPACE){
      spacePressed = true;
    }

    //debug
    if(code == KeyEvent.VK_T){
      if (!showDebugText){
        showDebugText = true;
      } else if (showDebugText) {
        showDebugText = false;
      }
    }
    if(code == KeyEvent.VK_R){
      switch (gp.currentMap){
        case 0: gp.tileManager.loadMap("/Map (Tile editor version)/worldmap.txt",0); break;
        case 1: gp.tileManager.loadMap("/Map (Tile editor version)/indoor01.txt",1); break;
      }
    }
    if(code == KeyEvent.VK_G){
      if (!godModeOn){
        godModeOn = true;
      }
      else {
        godModeOn = false;
      }
    }
  }

  public void pauseState(int code){
    if(code == KeyEvent.VK_P){
      gp.gameState = gp.playState;
    }


  }

  public void dialogueState(int code){
    if (code == KeyEvent.VK_ENTER){
      enterPressed = true;
    }
  }

  public void characterState(int code){
    if (code == KeyEvent.VK_C){
      gp.gameState = gp.playState;
    }

    if (code == KeyEvent.VK_ENTER){
      gp.player.selectItem();
    }
    playerInventory(code);
  }

  public void optionsState(int code){
    if (code == KeyEvent.VK_ESCAPE){
      gp.gameState = gp.playState;
    }
    if (code == KeyEvent.VK_ENTER){
      enterPressed = true;
    }

    int maxCommandNum = 0;
    switch (gp.ui.subState){
      case 0: maxCommandNum = 5; break;
      case 3: maxCommandNum = 1; break;
    }
    if (code == KeyEvent.VK_W){
      gp.ui.commandNum--;
      gp.playSE(9);
      if (gp.ui.commandNum < 0){
        gp.ui.commandNum = maxCommandNum;
      }
    }
    if (code == KeyEvent.VK_S){
      gp.ui.commandNum++;
      gp.playSE(9);
      if (gp.ui.commandNum > maxCommandNum){
        gp.ui.commandNum = 0;
      }
    }
    if (code == KeyEvent.VK_A){
      if (gp.ui.subState == 0){
        if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0){
          gp.music.volumeScale--;
          gp.music.checkVolume();
          gp.playSE(9);
        }
        if (gp.ui.commandNum == 2 && gp.sound.volumeScale > 0){
          gp.sound.volumeScale--;
          gp.playSE(9);
        }
      }
    }
    if (code == KeyEvent.VK_D){
      if (gp.ui.subState == 0){
        if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5){
          gp.music.volumeScale++;
          gp.music.checkVolume();
          gp.playSE(9);
        }
        if (gp.ui.commandNum == 2 && gp.sound.volumeScale < 5){
          gp.sound.volumeScale++;
          gp.playSE(9);
        }
      }
    }
  }

  public void gameOverState(int code){
    if (code == KeyEvent.VK_W){
      gp.ui.commandNum--;
      if (gp.ui.commandNum < 0){
        gp.ui.commandNum = 1;
      }
      gp.playSE(9);
    }
    if (code == KeyEvent.VK_S){
      gp.ui.commandNum++;
      if (gp.ui.commandNum > 1){
        gp.ui.commandNum = 0;
      }
      gp.playSE(9);
    }
    if (code == KeyEvent.VK_ENTER){
      if (gp.ui.commandNum == 0){
        gp.gameState = gp.playState;
        gp.resetGame(false);
        gp.playMusic(0);
      }
      else if (gp.ui.commandNum == 1){
        gp.gameState = gp.titleState;
        gp.resetGame(true);
      }
    }
  }

  public void tradeState(int code){
    if (code == KeyEvent.VK_ENTER){
      enterPressed = true;
    }

    if (gp.ui.subState == 0){
      if (code == KeyEvent.VK_W){
        gp.ui.commandNum--;
        if (gp.ui.commandNum < 0){
          gp.ui.commandNum = 2;
        }
        gp.playSE(9);
      }
      if (code == KeyEvent.VK_S){
        gp.ui.commandNum++;
        if (gp.ui.commandNum > 2){
          gp.ui.commandNum = 0;
        }
        gp.playSE(9);
      }
    }
    if (gp.ui.subState == 1){
      npcInventory(code);
      if (code == KeyEvent.VK_ESCAPE){
        gp.ui.subState = 0;
      }
    }
    if (gp.ui.subState == 2){
      playerInventory(code);
      if (code == KeyEvent.VK_ESCAPE){
        gp.ui.subState = 0;
      }
    }
  }

  public void mapState(int code){
    if (code == KeyEvent.VK_M){
      gp.gameState = gp.playState;
    }
  }

  public void playerInventory(int code){
    if (code == KeyEvent.VK_W){
      if (gp.ui.playerSlotRow != 0){
        gp.ui.playerSlotRow--;
        gp.playSE(9);
      }
    }
    if (code == KeyEvent.VK_A){
      if (gp.ui.playerSlotCol != 0){
        gp.ui.playerSlotCol--;
        gp.playSE(9);
      }
    }
    if (code == KeyEvent.VK_S){
      if (gp.ui.playerSlotRow != 3){
        gp.ui.playerSlotRow++;
        gp.playSE(9);
      }
    }
    if (code == KeyEvent.VK_D){
      if (gp.ui.playerSlotCol != 4){
        gp.ui.playerSlotCol++;
        gp.playSE(9);
      }
    }
  }

  public void npcInventory(int code){
    if (code == KeyEvent.VK_W){
      if (gp.ui.npcSlotRow != 0){
        gp.ui.npcSlotRow--;
        gp.playSE(9);
      }
    }
    if (code == KeyEvent.VK_A){
      if (gp.ui.npcSlotCol != 0){
        gp.ui.npcSlotCol--;
        gp.playSE(9);
      }
    }
    if (code == KeyEvent.VK_S){
      if (gp.ui.npcSlotRow != 3){
        gp.ui.npcSlotRow++;
        gp.playSE(9);
      }
    }
    if (code == KeyEvent.VK_D){
      if (gp.ui.npcSlotCol != 4){
        gp.ui.npcSlotCol++;
        gp.playSE(9);
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();

    if(code == KeyEvent.VK_W){
      upPressed = false;
    }
    if(code == KeyEvent.VK_S){
      downPressed = false;
    }
    if(code == KeyEvent.VK_A){
      leftPressed = false;
    }
    if(code == KeyEvent.VK_D){
      rightPressed = false;
    }
    if(code == KeyEvent.VK_F){
      shotKeyPressed = false;
    }
    if(code == KeyEvent.VK_ENTER){
      enterPressed = false;
    }
    if(code == KeyEvent.VK_SPACE){
      spacePressed = false;
    }
  }
}
