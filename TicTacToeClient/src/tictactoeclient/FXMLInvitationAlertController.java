/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import com.google.gson.Gson;
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
import onlineplaying.NetworkAccessLayer;
import onlineplaying.PlayerDto;
import utilities.Codes;
import utilities.Strings;

/**
 * FXML Controller class
 *
 * @author Ziad-Elshemy
 */
public class FXMLInvitationAlertController implements Initializable, Listener {

    Gson gson;
    PlayerDto senderPlayer;
    Navigator navigator;

    @FXML
    private Button acceptBtn;
    @FXML
    private Button rejectBtn;
    @FXML
    private Label senderLabel;
    @FXML
    private ProgressIndicator progressIndicator;

    Timeline timeline;
    private Media media;
    static MediaPlayer alarmMediaPlayer;
    Stage stage;

    //Stage stage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        NetworkAccessLayer.setRef(this);
        gson = new Gson();
        senderPlayer = new PlayerDto();
        navigator = new Navigator();

        Platform.runLater(() -> {
            stage = (Stage) acceptBtn.getScene().getWindow();
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

    public void setSenderLabel(PlayerDto sender) {
        senderPlayer = sender;
        senderLabel.setText(senderPlayer.getUserName());
    }

    @FXML
    private void acceptBtnAction(ActionEvent event) {

        ArrayList requestArr = new ArrayList();
        requestArr.add(Codes.INVITATION_REPLY_CODE);
        requestArr.add(1); // for accept
        requestArr.add(gson.toJson(senderPlayer));
        String jsonRegisterationRequest = gson.toJson(requestArr);
        NetworkAccessLayer.sendRequest(jsonRegisterationRequest);

        System.out.println("you have Accepted Invitation from " + senderPlayer.getUserName());
        Stage stage = (Stage) acceptBtn.getScene().getWindow();
        timeline.stop();
        alarmMediaPlayer.pause();
        if (!TicTacToeClient.isMuted) {
            TicTacToeClient.mediaPlayer.play();
        }
        stage.close();
        Platform.runLater(() -> {
            System.out.println("naviigate the enemy: " + senderPlayer.getUserName());
            navigator.luanchOnlineGame(TicTacToeClient.mainStage, "OnlineGameScreen.fxml", senderPlayer, "O");
        });

        System.out.println("can't reach here?");
    }

    @FXML
    private void rejectBtnAction(ActionEvent event) {

        ArrayList requestArr = new ArrayList();
        requestArr.add(Codes.INVITATION_REPLY_CODE);
        requestArr.add(0); // for reject
        requestArr.add(gson.toJson(senderPlayer));
        String jsonRegisterationRequest = gson.toJson(requestArr);
        NetworkAccessLayer.sendRequest(jsonRegisterationRequest);

        //System.out.println("Invitation from "+senderPlayer.getUserName()+ " has been Rejected");
        System.out.println("you have Rejected Invitation from " + senderPlayer.getUserName());
        Stage stage = (Stage) acceptBtn.getScene().getWindow();
        closeAlert();
        navigator.goToPage(TicTacToeClient.mainStage, "HomeScreen.fxml");
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
        //alarmMediaPlayer.volumeProperty().set(0.25);
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

    // i need to test here
    @Override
    public void onServerResponse(boolean success, ArrayList responseData) {
        if (success) {
            System.out.println("Accepted");
            System.out.println("hi from invitation alarm data: " + responseData.toString());
            Platform.runLater(() -> {
                closeAlert();
                navigator.luanchOnlineGame(TicTacToeClient.mainStage, "OnlineGameScreen.fxml", senderPlayer, "O");

            });
        } else {
            System.out.println("Rejeted");
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
                   Logger.getLogger(FXMLInvitationAlertController.class.getName()).log(Level.SEVERE, null, ex);
               }
              
           });
       }
    }

}
