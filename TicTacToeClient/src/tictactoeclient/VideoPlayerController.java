/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author esraa.m.mosaad
 */
public class VideoPlayerController implements Initializable {
    
    
    private Media media;
    static MediaPlayer mediaPlayer;
    static String videoUrl=null;

    @FXML
    private MediaView video;
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        media = new Media(new File(videoUrl).toURI().toString());
        mediaPlayer =new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        video.setFitHeight(400);
        video.setFitWidth(700);
        video.setMediaPlayer(mediaPlayer); 
        mediaPlayer.volumeProperty().set(0.2);
        mediaPlayer.play();
        TicTacToeClient.mediaPlayer.pause();
        
        
        
        
    }    
    
}
