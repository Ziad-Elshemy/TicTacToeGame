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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    private Text passwordLable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        navigator=new Navigator();
        gson = new Gson();
        player = new PlayerDto();
    }    

    @FXML
    private void onSubmitUpdateClicked(ActionEvent event) {
        //System.out.println("Submit Update Button Clicked");
        player.setUserName(userNameField.getText());
        player.setPassword(passWordField.getText());        
        ArrayList requestArrayList = new ArrayList();
        requestArrayList.add(Codes.CHANGE_PASSWORD_CODE);
        requestArrayList.add(gson.toJson(player));
        String jsonEditProfileRequest = gson.toJson(requestArrayList);
        NetworkAccessLayer.sendRequest(jsonEditProfileRequest, this);
        System.out.println("Json Sent From EditProfile"+jsonEditProfileRequest);
    }

    @FXML
    private void onSeeYourRecordsButtonClicked(ActionEvent event) {
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

    @Override
    public void onServerResponse(boolean success,ArrayList raquestData) {
        System.err.println("EtitProfile ServerResponse :"+raquestData);
        if (success)
        {
            //player = gson.fromJson(raquestData.get(1).toString(), PlayerDto.class);
            //System.out.println("ON EditPage : "+player.getName());
            System.out.println("Updated");
            Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Password Updated");
            alert.showAndWait();
            });
           
        }
        else
        {
            System.err.println("NotUPdataed");
            Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.ERROR, "UserName not Found");
            alert.showAndWait();
            });
        }
    }
    public void setData(PlayerDto player)
    {
        
        String score1 = String.valueOf(player.getScore()); // primitive int
        username.setText(player.getName());
        score.setText(score1);
        userNameField.setText(player.getUserName());
       System.out.println("Fun to Set Name :"+player.getScore());
    }

    
    
}
