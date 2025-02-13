/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import onlineplaying.NetworkAccessLayer;
import onlineplaying.PlayerDto;
import utilities.Strings;

/**
 * FXML Controller class
 *
 * @author Ziad-Elshemy
 */
public class FXMLWaitingAlertController implements Initializable, Listener {

    Gson gson;
    PlayerDto recieverPlayer;
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
        // TODO
        NetworkAccessLayer.setRef(this);
        gson = new Gson();
        recieverPlayer = new PlayerDto();
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
            initMedia();
        });
    }

    public void setRecieverLabel(String recieverName) {
        recieverLabel.setText(recieverName);
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

    private void initMedia() {
        media = new Media(getClass().getResource(Strings.alarmMusic).toString());
        alarmMediaPlayer = new MediaPlayer(media);
        alarmMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        alarmMediaPlayer.volumeProperty().set(0.25);
        alarmMediaPlayer.play();
    }

    public void closeAlert() {
        timeline.stop();
        alarmMediaPlayer.pause();
        if (!TicTacToeClient.isMuted) {
            TicTacToeClient.mediaPlayer.play();
        }
        stage.close();
    }

    @Override
    public void onServerResponse(boolean success, ArrayList responseData) {
        System.out.println("hi from waiting alarm data: " + responseData.toString());
        if (success) {
            Object result = responseData.get(2);

            if (result instanceof LinkedTreeMap) {
                String jsonResult = gson.toJson(result);
                Platform.runLater(() -> {
                    navigator.luanchOnlineGame(TicTacToeClient.mainStage, "OnlineGameScreen.fxml", gson.fromJson(jsonResult, PlayerDto.class), "X");
                    closeAlert();
                });
            }

        } else {
            Platform.runLater(() -> {
                navigator.goToPage(TicTacToeClient.mainStage, "HomeScreen.fxml");
                closeAlert();
            });
        }
    }

     @Override
    public void onServerCloseResponse(boolean serverClosed) {
       if(serverClosed)
       {
           Platform.runLater(()->{
               navigator.popUpStage("ServerDisconnect.fxml");
             
               try {
                   NetworkAccessLayer.mySocket.close();
               } catch (IOException ex) {
                   Logger.getLogger(FXMLWaitingAlertController.class.getName()).log(Level.SEVERE, null, ex);
               }
               
           });
       }
    }

}
