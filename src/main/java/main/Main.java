package main;

import javax.swing.*;

public class Main {
  public static JFrame window;

  public static void main(String[] args) {
    window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);
    window.setTitle("Blue Boy Adventure");
    new Main().setIcon();

    GamePanel gamePanel = new GamePanel();
    window.add(gamePanel);

    gamePanel.config.loadConfig();
    if (gamePanel.fullScreenOn){
      window.setUndecorated(true);
    }
    window.pack();

    window.setLocationRelativeTo(null);
    window.setVisible(true);

    gamePanel.setupGame();
    gamePanel.setGameThread();
  }

  public void setIcon(){
    ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("player/boyDown1.png"));
    window.setIconImage(imageIcon.getImage());
  }
}
