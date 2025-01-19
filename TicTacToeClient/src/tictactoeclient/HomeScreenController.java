/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import com.google.gson.Gson;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import onlineplaying.NetworkAccessLayer;
import onlineplaying.PlayerDto;
import utilities.Codes;

/**
 * FXML Controller class
 *
 * @author esraa.m.mosaad
 */
public class HomeScreenController implements Initializable ,Listener {
    
    Gson gson;
    PlayerDto player;
    Navigator navigator;
    
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
    private Text username2;

    @FXML
    private ImageView userImage1;

    @FXML
    private Circle avatar11;

    @FXML
    private Text username1;

    @FXML
    private Text score1;


    @FXML
    private Circle avatar111;

    @FXML
    private Text username11;

    @FXML
    private Text score11;
    @FXML
    private Button editProfileButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button singlePlayerButton;

    @FXML
    private Button localTwoPlayersButton;
    @FXML
    private Circle avatarOne;
    
    @FXML
    private AnchorPane userCard;
    @FXML
    private AnchorPane userCard1;
    @FXML
    private Circle avatarOne1;
    @FXML
    private ImageView userImage11;
    @FXML
    private AnchorPane userCard11;
    @FXML
    private Circle avatarOne11;
    @FXML
    private ImageView userImage111;
    @FXML
    private Circle avatar1111;
    @FXML
    private Text username111;
    @FXML
    private Text score111;
    @FXML
    private AnchorPane userCard111;
    @FXML
    private Circle avatarOne111;
    @FXML
    private ImageView userImage1111;
    @FXML
    private Circle avatar11111;
    @FXML
    private Text username1111;
    @FXML
    private Text score1111;
    @FXML
    private Button muteBtn;
    @FXML
    private ImageView muteImg;
    @FXML
    private Button inviteBtn;
    @FXML
    private Button inviteBtn2;
     ActionEvent eventforEdit;

    @FXML
    
    
    void onEditProfileButtonClicked(ActionEvent event) {
        
        eventforEdit = event;
        ArrayList requestList = new ArrayList();
        requestList.add(Codes.SELECT_DATA_FOR_EDIT_PROFILE_CODE);
        requestList.add("hanySamy");
        String jsonSelectForEdit =gson.toJson(requestList);
        NetworkAccessLayer.sendRequest(jsonSelectForEdit, this);
        
      
    }

     @FXML
    void onInviteButtonClicked(ActionEvent event) {
         
        navigator.goToPage(event,"FXMLGameScreen.fxml");
        
        
    }
    @FXML
    void onLocalTwoPlayersButtonClicked(ActionEvent event) {
        
         navigator.goToPage(event,"FXMLGameScreen.fxml");


    }

    @FXML
    void onLogoutButtonClicked(ActionEvent event) {
        navigator.goToPage(event,"LoginScreen.fxml");
    }

    @FXML
    void onSinglePlayerButtonClicked(ActionEvent event) {
        
          navigator.goToPage(event,"FXMLGameScreen.fxml");


    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        navigator=new Navigator();
        gson = new Gson();
        player = new PlayerDto();
        
        if( !TicTacToeClient.isMuted){
           
         muteImg.setImage(new Image("file:src/Images/volume.png")); 
        
        }
        
       else {
           
         muteImg.setImage(new Image("file:src/Images/mute.png"));
       
       }
        
        
        
        

    }    

     
    @FXML
    void onMuteBtnClicked(ActionEvent event){
        
        
       if( TicTacToeClient.isMuted){
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
    
    @FXML
    void onBackIconClicked(ActionEvent event) {
        
        navigator.goToPage(event, "LoginScreen.fxml");

    }

    @FXML
    private void onInviteButtonClicked2(ActionEvent event) {
        navigator.addAlert("FXMLInvitationAlert.fxml","Invitaion Request");
    }

    @Override
    public void onServerResponse(boolean success, ArrayList data) {
        player = gson.fromJson(data.get(1).toString(), PlayerDto.class);
        System.out.println("Home player: "+player.getUserName());
        Platform.runLater(()->{
         navigator.goToPage(eventforEdit, "FXMLEditProfile.fxml",player);
        });
    }
    
}
