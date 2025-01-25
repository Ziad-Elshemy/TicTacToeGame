/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import onlineplaying.NetworkAccessLayer;
import onlineplaying.PlayerDto;
import utilities.Codes;

/**
 * FXML Controller class
 *
 * @author HANY
 */
public class EditProfileController implements Initializable ,Listener{
    Navigator navigator; 
    Gson gson;
    PlayerDto player;
    Alert alert;
    ActionEvent eventForEdit;

    @FXML
    private Circle avatar;
    @FXML
    private ImageView userImage;
    @FXML
    private Circle avatar1;
    @FXML
    private Text username;
    @FXML
    private Text score;
    @FXML
    private TextField userNameField;
    @FXML
    private Button submitUpdateButton;
    @FXML
    private Button SeeYourRecordsButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField passWordField;
    @FXML
    private ImageView muteImg;
    @FXML
    private Button muteBtn;
    @FXML
    private Button deletAccountButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        userImage.setImage(NetworkAccessLayer.playerData.getGender().equals("Male")?new Image("file:src/Images/boy.png"):new Image("file:src/Images/girl.png"));
        NetworkAccessLayer.setRef(this);
        navigator=new Navigator();
        gson = new Gson();
        player = new PlayerDto();
        
        String score1 = String.valueOf(NetworkAccessLayer.playerData.getScore()); // primitive int
        username.setText(NetworkAccessLayer.playerData.getName());
        score.setText(score1);
        userNameField.setText(NetworkAccessLayer.playerData.getUserName());
    }    

    @FXML
    private void onSubmitUpdateClicked(ActionEvent event) {
        NetworkAccessLayer.setRef(this);
        player.setUserName(userNameField.getText());
        player.setPassword(passWordField.getText());        
        ArrayList requestArrayList = new ArrayList();
        requestArrayList.add(Codes.CHANGE_PASSWORD_CODE);
        requestArrayList.add(gson.toJson(player));
        String jsonEditProfileRequest = gson.toJson(requestArrayList);
        NetworkAccessLayer.sendRequest(jsonEditProfileRequest);
        //System.out.println("Json Sent From EditProfile"+jsonEditProfileRequest);
    }

    @FXML
    private void onSeeYourRecordsButtonClicked(ActionEvent event) {
        navigator.goToPage(event, "OnlineGamesRecords.fxml");
    }

    @FXML
    private void onCancelButtonClicked(ActionEvent event) {
        navigator.goToPage(event, "HomeScreen.fxml");
    }
    
    @FXML
    void onBackIconClicked(ActionEvent event) {
        
        navigator.goToPage(event, "HomeScreen.fxml");

    }
    
         
    @FXML
    void onMuteBtnClicked(ActionEvent event){
        
        
       if(TicTacToeClient.isMuted){
        TicTacToeClient.mediaPlayer.play();
        muteImg.setImage(new Image("file:src/Images/volume.png")); 
        TicTacToeClient.isMuted=false;
        
        }
       else {
        TicTacToeClient.mediaPlayer.pause();
        muteImg.setImage(new Image("file:src/Images/mute.png"));
        TicTacToeClient.isMuted=true;
       
       }
        

    }

    @Override
    public void onServerResponse(boolean success, ArrayList responseData) {
        System.out.println("Responce on Edit Page"+responseData);
        if ((double)responseData.get(0) ==Codes.CHANGE_PASSWORD_CODE && success)
        {
            
            System.out.println("Updated");
            Platform.runLater(()->{
             alert = new Alert(Alert.AlertType.INFORMATION, "Password Updated");
            alert.showAndWait();
            });
           
        }
        else if ((double)responseData.get(0)==Codes.CHANGE_PASSWORD_CODE && !success)
        {
            System.err.println("NotUPdataed");
            Platform.runLater(()->{
            alert = new Alert(Alert.AlertType.ERROR, "UserName not Found");
            alert.showAndWait();
            });
        }
        else if((double)responseData.get(0)==(double)Codes.DELETE_ACCOUNT_CODE && success)
        {  System.out.println("NAvigate To Login");
            Platform.runLater(()->{
            navigator.goToPage(eventForEdit, "LoginScreen.fxml");
              
           });
            
        }
        
        else if((double)responseData.get(0)==Codes.DELETE_ACCOUNT_CODE && !success)
        {
            
            Platform.runLater(()->{
                 Alert myAlert = new Alert(Alert.AlertType.INFORMATION);
                 myAlert.setContentText("Can't Delete Your Account");
                 myAlert.showAndWait();
              });
        }
        
        
    }

    @FXML
    private void onDeletAccountButtonClicked(ActionEvent event) {
        
       // NetworkAccessLayer.setRef(this);
        eventForEdit = event;
        player.setUserName(userNameField.getText());
        ArrayList requestArrayList = new ArrayList();
        requestArrayList.add(Codes.DELETE_ACCOUNT_CODE);
        requestArrayList.add(gson.toJson(player));
        String jsonDeleteAccountRequest = gson.toJson(requestArrayList);
        NetworkAccessLayer.sendRequest(jsonDeleteAccountRequest);
        
       // navigator.goToPage(eventForEdit, "LoginScreen.fxml");
        
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
                   Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
               }
           });
       }
    }
    
}
