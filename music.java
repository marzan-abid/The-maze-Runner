
import java.io.File;
import java.io.IOException;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dell
 */
public class music {
    
    int count=0;
    Clip clip;
    MediaPlayer mediaPlayer;
    
    music() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        this.count = count;
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\Dell\\Desktop\\java_project20ap\\fourMazeProject\\src\\diamond.wav").getAbsoluteFile());
//        

        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
       clip.start();

    //String bip = "C:\\Users\\Dell\\Desktop\\java_project20ap\\fourMazeProject\\src\\162471__kastenfrosch__sound5";
    //Media hit = new Media(new File(bip).toURI().toString());
    //mediaPlayer = new MediaPlayer(hit);
    //mediaPlayer.play();
    }
//    public void run(){
//        if(count==1){
//            
//            //mediaPlayer.play();
//            clip.start();
//        }
//    } 
}
