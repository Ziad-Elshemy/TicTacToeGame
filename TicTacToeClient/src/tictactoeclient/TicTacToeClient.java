/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import static tictactoeclient.VideoPlayerController.videoUrl;

/**
 *
 * @author Ziad-Elshemy
 */
public class TicTacToeClient extends Application {
    
    private  Media media;
    static  MediaPlayer mediaPlayer;
    private  String videoUrl;
    private  MediaView music;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        
        videoUrl="file:/C:/Users/esraa/OneDrive/Documents/NetBeansProjects/TicTacToeGame/TicTacToeClient/src/videos/m.mp3";
        media = new Media(videoUrl);
        mediaPlayer =new MediaPlayer(media);
        music=new MediaView(mediaPlayer);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        

        Parent root = FXMLLoader.load(getClass().getResource("Splash.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        
      
     
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
      
    }
    
}
