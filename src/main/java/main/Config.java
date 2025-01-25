package main;

import java.io.*;

public class Config {
  GamePanel gp;

  public Config(GamePanel gp) {
    this.gp = gp;
  }

  public void saveConfig(){
     try {
       BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

       //full screen
       if (gp.fullScreenOn){
         bw.write("on");
       }
       if (!gp.fullScreenOn){
         bw.write("off");
       }
       bw.newLine();

       //music volume
       bw.write(String.valueOf(gp.music.volumeScale));
       bw.newLine();

       //SE volume
       bw.write(String.valueOf(gp.sound.volumeScale));
       bw.newLine();

       bw.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void loadConfig(){
    try {
      BufferedReader br = new BufferedReader(new FileReader("config.txt"));

      String s = br.readLine();

      //full screen
      if (s.equals("on")){
        gp.fullScreenOn = true;
      }
      if (s.equals("off")){
        gp.fullScreenOn = false;
      }

      //music volume
      s = br.readLine();
      gp.music.volumeScale = Integer.parseInt(s);

      //SE volume
      s = br.readLine();
      gp.sound.volumeScale = Integer.parseInt(s);

      br.close();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
