/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import onlineplaying.PlayerDto;
import static tictactoeclient.FXMLWaitingAlertController.alarmMediaPlayer;

/**
 * FXML Controller class
 *
 * @author esraa.m.mosaad
 */
public class OnLoginCompleteController implements Initializable {
    
    Navigator navigator;

    @FXML
    private ProgressIndicator progressIndicator;
    


    Timeline timeline;
    private Media media;
    static MediaPlayer alarmMediaPlayer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       navigator = new Navigator();

        Platform.runLater(() -> {
            startProgressIndicator();
        });
    } 
 
    
        private void startProgressIndicator() {


        progressIndicator.setProgress(0);

        timeline = new Timeline(new KeyFrame(Duration.millis(45), e -> {
            double progress = progressIndicator.getProgress() + 0.05;
            progressIndicator.setProgress(progress);
        }));

        timeline.setCycleCount(50);
        timeline.setOnFinished(event -> {
                 LoginScreenController.stage.close();
        });

        timeline.play();
    }
    
}
