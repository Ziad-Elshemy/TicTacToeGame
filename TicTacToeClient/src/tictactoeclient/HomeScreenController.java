/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import com.google.gson.Gson;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
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
    private AnchorPane anchorPane;
    
    @FXML
    private Button singlePlayerButton;

    @FXML
    private Button localTwoPlayersButton;

    @FXML
    private Circle avatar;

    @FXML
    private ImageView userImage;

    @FXML
    private Circle onlineCircle;

    @FXML
    private Text username;

    @FXML
    private Text score;

    @FXML
    private Button editProfileButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button muteBtn;

    @FXML
    private ImageView muteImg;
    
    ActionEvent eventforEdit;


    @FXML
    
    
    void onEditProfileButtonClicked(ActionEvent event) {
        NetworkAccessLayer.setRef(this);
        eventforEdit = event;
        ArrayList requestList = new ArrayList();
        requestList.add(Codes.SELECT_DATA_FOR_EDIT_PROFILE_CODE);
        requestList.add(NetworkAccessLayer.playerData.getUserName());
        String jsonSelectForEdit =gson.toJson(requestList);
        NetworkAccessLayer.sendRequest(jsonSelectForEdit);
        
      
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

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gson = new Gson();
        player = new PlayerDto();
        navigator=new Navigator();
        NetworkAccessLayer.setRef(this);    
        NetworkAccessLayer.ref=this;

        username.setText(NetworkAccessLayer.playerData.getUserName()); 
        score.setText("Score : "+NetworkAccessLayer.playerData.getScore()); 
        addUserCard(NetworkAccessLayer.onlinePlayers);
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
    
    

//    @FXML
//    private void onInviteButtonClicked2(ActionEvent event) {
//        navigator.luanchInvitation("FXMLInvitationAlert.fxml","Invitaion Request","Ghazal Elshemy");
//    }
//
//    @FXML
//    private void onInviteButtonClicked3(ActionEvent event) {
//        player.setUserName("ziad2");
//        ArrayList requestArr = new ArrayList();
//        requestArr.add(Codes.SEND_INVITATION_CODE);
//        System.out.println("hi "+ player.getUserName());
//        requestArr.add(gsonFile.toJson(player));
//        System.out.println("hi ya"+ player.getUserName());
//        String jsonRegisterationRequest = gsonFile.toJson(requestArr);
//        NetworkAccessLayer.sendRequest(jsonRegisterationRequest);
//        Platform.runLater(()->{
//            navigator.luanchWaiting("FXMLWaitingAlert.fxml", "Invitaion Requestttt", player.getUserName());
//        });
//        
//        
//    }

 public void addUserCard(ArrayList<PlayerDto> onlinePlayers){
        
        
    anchorPane.getChildren().clear();

        
        
     int i=0;
        
       for(PlayerDto player : onlinePlayers){
           
           
           if(player.getUserName().equals(NetworkAccessLayer.playerData.getUserName())){
           
             continue;
           }
           
           
        Circle avatarOne=new Circle();
        ImageView userImageOne=new ImageView();
        Circle onlineCircleOne=new Circle();
        Text usernameOne=new Text();
        Text scoreOne=new Text();
        Button inviteBtn=new Button();
        AnchorPane userCard=new AnchorPane();
        
        
        avatarOne.setFill(javafx.scene.paint.Color.DODGERBLUE);
        avatarOne.setLayoutX(33.0);
        avatarOne.setLayoutY(20.0);
        avatarOne.setRadius(33.0);
        avatarOne.setStroke(javafx.scene.paint.Color.TRANSPARENT);
        avatarOne.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        avatarOne.setStyle("-fx-fill: linear-gradient(#82C0CC, white);");

        userImageOne.setFitHeight(41.0);
        userImageOne.setFitWidth(44.0);
        userImageOne.setLayoutX(14.0);
        userImageOne.setLayoutY(0.0);
        userImageOne.setPickOnBounds(true);
        userImageOne.setPreserveRatio(true);
        userImageOne.setImage(new Image(getClass().getResource("/Images/boy.png").toExternalForm()));

        onlineCircleOne.setFill(javafx.scene.paint.Color.DODGERBLUE);
        onlineCircleOne.setLayoutX(52.0);
        onlineCircleOne.setLayoutY(40.0);
        onlineCircleOne.setRadius(5.0);
        onlineCircleOne.setStroke(javafx.scene.paint.Color.TRANSPARENT);
        onlineCircleOne.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        onlineCircleOne.setStyle(!player.getIsPlaying()?"-fx-fill: #008000;":"-fx-fill: #FFA62B;");

        usernameOne.setLayoutX(74.0);
        usernameOne.setLayoutY(18.0);
        usernameOne.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        usernameOne.setStrokeWidth(0.0);
        usernameOne.setText(player.getUserName()); 
        usernameOne.setWrappingWidth(116.0595703125);
        usernameOne.setFont(new Font("Arial", 18.0));

        scoreOne.setFill(javafx.scene.paint.Color.valueOf("#0000009f"));
        scoreOne.setLayoutX(73.0);
        scoreOne.setLayoutY(40.0);
        scoreOne.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        scoreOne.setStrokeWidth(0.0);
        scoreOne.setText("Score : "+player.getScore());
        scoreOne.setFont(new Font(18.0));

        inviteBtn.setLayoutX(190.0);
        inviteBtn.setLayoutY(0.0);
        inviteBtn.setMnemonicParsing(false);
        inviteBtn.setPrefHeight(35.0);
        inviteBtn.setPrefWidth(83.0);
        inviteBtn.setStyle("-fx-background-color: linear-gradient(to right,#82C0CC, white); -fx-background-radius: 12;");
        inviteBtn.setText("Invite");
        inviteBtn.setTextFill(javafx.scene.paint.Color.valueOf("#fffafa"));
        inviteBtn.setFont(new Font("Arial Bold", 15.0));
        
        inviteBtn.setOnAction((event)->{
        
        
        

        });
        
        userCard.getChildren().add(avatarOne);
        userCard.getChildren().add(userImageOne);
        userCard.getChildren().add(onlineCircleOne);
        userCard.getChildren().add(usernameOne);
        userCard.getChildren().add(scoreOne);
        userCard.getChildren().add(inviteBtn);
        
        
        userCard.setLayoutX(44.0);
        userCard.setLayoutY(0.0+i);
        userCard.setPrefHeight(86.0);
        userCard.setPrefWidth(297.0);
        
        
        
        anchorPane.getChildren().add(userCard);

       
        
        i+=75;
       
       
       
       
       
       
       }
    
    
    
    
    
    } 
 
         
    @FXML
    private void onInviteButtonClicked3(ActionEvent event) {
        player.setUserName("ziad2");
        ArrayList requestArr = new ArrayList();
        requestArr.add(Codes.SEND_INVITATION_CODE);
        System.out.println("hi "+ player.getUserName());
        requestArr.add(gson.toJson(player));
        System.out.println("hi ya"+ player.getUserName());
        String jsonRegisterationRequest = gson.toJson(requestArr);
        NetworkAccessLayer.sendRequest(jsonRegisterationRequest);
        Platform.runLater(()->{
            navigator.luanchWaiting("FXMLWaitingAlert.fxml", "Invitaion Requestttt", player.getUserName());
                
                
        
                
                });
                
                
  }

 
    @Override
    public void onServerResponse(boolean success, ArrayList responseData) {
        
        if((double)responseData.get(0)==(Codes.SEND_INVITATION_CODE)&&success){
        System.out.println("testttt "+ responseData.toString());
        System.out.println("show the invitation");
        Platform.runLater(()->{
            navigator.luanchInvitation("FXMLInvitationAlert.fxml","Invitaion Request",responseData.get(1).toString());
        });
        }
        else if((double)responseData.get(0)==(Codes.SELECT_DATA_FOR_EDIT_PROFILE_CODE)&&success)
        {
        player = gson.fromJson(responseData.get(1).toString(), PlayerDto.class);
        System.out.println("Home player: "+player.getUserName());
        Platform.runLater(()->{
         navigator.goToPage(eventforEdit, "FXMLEditProfile.fxml",player);
        });
        }
        else {
            System.err.println("Wrong In Home Screen" +responseData.get(0)+" "+Codes.SELECT_DATA_FOR_EDIT_PROFILE_CODE);
        }
        
    }

  /*  @Override
    public void onServerResponse(boolean success, ArrayList data) {
        player = gson.fromJson(data.get(1).toString(), PlayerDto.class);
        System.out.println("Home player: "+player.getUserName());
        Platform.runLater(()->{
         navigator.goToPage(eventforEdit, "FXMLEditProfile.fxml",player);
        });
    }*/
    
    @Override
    public void onOnlinePlayersUpdate(ArrayList<PlayerDto> onlinePlayers) {
           Platform.runLater(()->{
                if(onlinePlayers!=null){
                 addUserCard(onlinePlayers);
                 System.out.println(onlinePlayers+"========================================================================");             
              }
           
           });   
        
    }
    
}
