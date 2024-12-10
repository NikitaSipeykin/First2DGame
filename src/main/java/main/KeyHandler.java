package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
  public boolean upPresed, downPresed, leftPresed, rightPresed;
  //debug
  boolean checkDrawTime = false;

  @Override
  public void keyTyped(KeyEvent e) {
    //we don't need this method
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();

    if(code == KeyEvent.VK_W){
      upPresed = true;
    }
    if(code == KeyEvent.VK_S){
      downPresed = true;
    }
    if(code == KeyEvent.VK_A){
      leftPresed = true;
    }
    if(code == KeyEvent.VK_D){
      rightPresed = true;
    }

    //debug
    if(code == KeyEvent.VK_T){
      if (!checkDrawTime){
        checkDrawTime = true;
      } else if (checkDrawTime) {
        checkDrawTime = false;
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();


    if(code == KeyEvent.VK_W){
      upPresed = false;
    }
    if(code == KeyEvent.VK_S){
      downPresed = false;
    }
    if(code == KeyEvent.VK_A){
      leftPresed = false;
    }
    if(code == KeyEvent.VK_D){
      rightPresed = false;
    }
  }
}
