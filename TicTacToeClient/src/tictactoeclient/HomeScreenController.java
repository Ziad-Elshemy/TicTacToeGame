/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author esraa.m.mosaad
 */
public class HomeScreenController implements Initializable {
    
    
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
    Navigator navigator;
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
    void onEditProfileButtonClicked(ActionEvent event) {

    }

    void onInviteButtonClicked(ActionEvent event) {
         
        navigator.goToPage(event,"GameScreen.fxml");


    }

    @FXML
    void onLocalTwoPlayersButtonClicked(ActionEvent event) {
        
         navigator.goToPage(event,"GameScreen.fxml");


    }

    @FXML
    void onLogoutButtonClicked(ActionEvent event) {

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
    }    

    @FXML
    private void onMuteBtnClicked(ActionEvent event) {
        
        
    }
    
}
