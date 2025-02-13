package tictactoeclient;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import onlineplaying.NetworkAccessLayer;
import onlineplaying.PlayerDto;
import utilities.Codes;

public class LoginScreenController implements Initializable, Listener {

    Navigator navigator;
    Gson gson;
    PlayerDto player;
    ActionEvent myEvent;
    static Stage stageOfNames;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Button localGameButton;
    @FXML
    private Button playPCButton;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private VBox mainVBox;
    @FXML
    private ImageView logoImage;

    @FXML
    private Button muteBtn;

    @FXML
    private ImageView muteImg;

    @FXML
    private Label passwordError;

    @FXML
    private Label usernameError;

    @FXML
    private Label serverOfflineText;

    @FXML
    private Button reconnectButton;
    
    static Stage stage;

    @FXML
    void onMuteBtnClicked(ActionEvent event) {

        if (TicTacToeClient.isMuted) {

            TicTacToeClient.mediaPlayer.play();
            muteImg.setImage(new Image(getClass().getResource("/Images/volume.png").toString()));
            TicTacToeClient.isMuted = false;

        } else {

            TicTacToeClient.mediaPlayer.pause();
            muteImg.setImage(new Image(getClass().getResource("/Images/mute.png").toString()));
            TicTacToeClient.isMuted = true;

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        

        gson = new Gson();
        player = new PlayerDto();
        navigator = new Navigator();
        centerVBox();
        usernameError.setText("");
        passwordError.setText("");
        NetworkAccessLayer.setRef(this);

        if (!TicTacToeClient.isMuted) {

            muteImg.setImage(new Image(getClass().getResource("/Images/volume.png").toString()));

        } else {

            muteImg.setImage(new Image(getClass().getResource("/Images/mute.png").toString()));

        }

        if (NetworkAccessLayer.isServerOffline) {

            usernameField.setDisable(true);
            passwordField.setDisable(true);
            loginButton.setDisable(true);
            registerButton.setDisable(true);
            serverOfflineText.setText("  Server Is Offline Now Try Again Later Or You Can Play Offline");

        } else {
            reconnectButton.setDisable(true);
        }
        if (NetworkAccessLayer.serverIP == null && NetworkAccessLayer.local == false) {
            navigator.popUpStage("ConnectToServerScreen.fxml");
        }
    }

    private void centerVBox() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();
        double vboxWidth = mainVBox.getWidth();
        double vboxHeight = mainVBox.getHeight();

    }

    @FXML
    private void gologin(ActionEvent event) {

        myEvent = event;

        if (usernameField.getText().isEmpty()) {
            usernameError.setText("Please Enter Your Username");
            usernameField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15;");
        }

        if (passwordField.getText().isEmpty()) {
            passwordError.setText("Please Enter Your Password");
            passwordField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15;");
        }

        if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {

            player.setUserName(usernameField.getText());
            player.setPassword(passwordField.getText());
            player.setIsOnline(true);
            ArrayList requestArr = new ArrayList();
            requestArr.add(Codes.LOGIN_CODE);
            requestArr.add(gson.toJson(player));
            String jsonLoginRequest = gson.toJson(requestArr);
            NetworkAccessLayer.sendRequest(jsonLoginRequest);
            System.out.println("the sendRequest data is: " + jsonLoginRequest);

        }

    }

    @FXML
    private void goregister(ActionEvent event) {

        navigator.goToPage(event, "RegisterScreen.fxml");

    }

    @FXML
    private void localGameBtnAction(ActionEvent event) {

        navigator.goToPage(event, "FXMLGameScreen.fxml");
    }

    @FXML
    private void onPcButton(ActionEvent event) {

        navigator.goToPage(event, "VsComputerScene.fxml");

    }

    @Override
    public void onServerResponse(boolean success, ArrayList responseData) {

        if (success) {
            Platform.runLater(() -> {
//                new Alert(Alert.AlertType.CONFIRMATION, "You Successfully Login ;)", ButtonType.OK).showAndWait();
//                navigator.goToPage(myEvent, "HomeScreen.fxml");
       Platform.runLater(() -> {

            try {

                Parent root = FXMLLoader.load(getClass().getResource("OnLoginComplete.fxml"));
                stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                
                navigator.goToPage(myEvent, "HomeScreen.fxml");
           

            } catch (IOException ex) {
                Logger.getLogger(VsComputerSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

            });
        } else {
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.ERROR, " You Already Logged in Or Player Not Found Please Register", ButtonType.OK).showAndWait();
            });
        }

    }

    @FXML
    private void onReconnectButon(ActionEvent event) {
        navigator.popUpStage("ConnectToServerScreen.fxml");
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
                   Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
               }
              
           });
       }
    }
    
}



