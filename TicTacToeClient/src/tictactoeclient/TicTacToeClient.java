/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import utilities.Strings;


/**
 *
 * @author Ziad-Elshemy
 */
public class TicTacToeClient extends Application {
    
    private  Media media;
    static  MediaPlayer mediaPlayer;
    private  MediaView music;
     static boolean isMuted;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        
        media = new Media(new File(Strings.music).toURI().toString());
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
