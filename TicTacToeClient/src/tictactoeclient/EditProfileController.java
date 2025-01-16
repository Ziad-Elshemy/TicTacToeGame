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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author HANY
 */
public class EditProfileController implements Initializable {

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
    
    Navigator navigator; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        navigator=new Navigator();
    }    

    @FXML
    private void onSubmitUpdateClicked(ActionEvent event) {
    }

    @FXML
    private void onSeeYourRecordsButtonClicked(ActionEvent event) {
    }

    @FXML
    private void onCancelButtonClicked(ActionEvent event) {
    }
    
    @FXML
    void onBackIconClicked(ActionEvent event) {
        
        navigator.goToPage(event, "HomeScreen.fxml");

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


    
}
