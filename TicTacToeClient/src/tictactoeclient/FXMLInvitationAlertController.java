/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import static tictactoeclient.TicTacToeClient.mediaPlayer;
import utilities.Strings;

/**
 * FXML Controller class
 *
 * @author Ziad-Elshemy
 */
public class FXMLInvitationAlertController implements Initializable {

    @FXML
    private Button acceptBtn;
    @FXML
    private Button rejectBtn;
    @FXML
    private Label senderLabel;
    @FXML
    private ProgressIndicator progressIndicator;
    
    Timeline timeline;
    private  Media media;
    static  MediaPlayer alarmMediaPlayer;
    
    //Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(()->{
            Stage stage = (Stage)acceptBtn.getScene().getWindow();
            stage.setOnCloseRequest(event->{
                System.out.println("Invitation Rejected by X icon");
                timeline.stop();
                alarmMediaPlayer.pause();
                TicTacToeClient.mediaPlayer.play();
            });
            startProgressIndicator(stage);
            initMedia();
        });
        
        
        
    }

    @FXML
    private void acceptBtnAction(ActionEvent event) {
        System.out.println("Invitation Accepted");
        Stage stage = (Stage)acceptBtn.getScene().getWindow();
        timeline.stop();
        alarmMediaPlayer.pause();
        TicTacToeClient.mediaPlayer.play();
        stage.close();
    }

    @FXML
    private void rejectBtnAction(ActionEvent event) {
        System.out.println("Invitation Rejected");
        Stage stage = (Stage)acceptBtn.getScene().getWindow();
        timeline.stop();
        alarmMediaPlayer.pause();
        TicTacToeClient.mediaPlayer.play();
        stage.close();
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
            TicTacToeClient.mediaPlayer.play();
            stage.close();
        });

        timeline.play();
    }

    private void initMedia() {
        media = new Media(new File(Strings.alarmMusic).toURI().toString());
        alarmMediaPlayer =new MediaPlayer(media);
        alarmMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        alarmMediaPlayer.volumeProperty().set(0.25);
        alarmMediaPlayer.play();
    }
    
}
