/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import com.google.gson.Gson;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    
    @FXML
    private Label recieverLabel;

    Timeline timeline;
    private Media media;
    static MediaPlayer alarmMediaPlayer;
    Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       navigator = new Navigator();

        Platform.runLater(() -> {
            stage = (Stage) recieverLabel.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                System.out.println("Invitation Rejected by X icon");
                timeline.stop();
                alarmMediaPlayer.pause();
                if (!TicTacToeClient.isMuted) {
                    TicTacToeClient.mediaPlayer.play();
                }
            });
            startProgressIndicator(stage);
        });
    } 
    
        private void startProgressIndicator(Stage stage) {

        TicTacToeClient.mediaPlayer.pause();

        progressIndicator.setProgress(0);

        timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            double progress = progressIndicator.getProgress() + 0.0066;
            progressIndicator.setProgress(progress);
        }));

        timeline.setCycleCount(150);
        timeline.setOnFinished(event -> {

            System.out.println("Time expired, closing stage...");
            alarmMediaPlayer.pause();
            if (!TicTacToeClient.isMuted) {
                TicTacToeClient.mediaPlayer.play();
            }
            stage.close();
        });

        timeline.play();
    }
    
}
