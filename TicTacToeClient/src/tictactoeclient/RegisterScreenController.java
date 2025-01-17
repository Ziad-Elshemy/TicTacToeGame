/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tictactoeclient;

import com.google.gson.Gson;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import onlineplaying.ConnectionsHandler;
import onlineplaying.PlayerDto;
import utilities.Codes;

/**
 * FXML Controller class
 *
 * @author abdullahraed
 */
public class RegisterScreenController implements Initializable {
    
    Navigator navigator;
    Gson gson;
    PlayerDto player;

    private Pane normalPane;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button registerButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private ImageView logoImage;
    @FXML
    private Pane innerPane;
    @FXML
    private TextField nameTextField;
    @FXML
    private Label nameLabel;
    
    @FXML
    private Label usernameError;

    @FXML
    private Label nameError;

    @FXML
    private Label passwordError;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gson = new Gson();
        player = new PlayerDto();
        navigator = new Navigator();
        registerButton.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100%  100%, #82C0CC,#edf6f9);"+" "+"-fx-background-radius : 10;");
        usernameError.setText("");
        nameError.setText("");
        passwordError.setText(""); 
    }    

    @FXML
    private void onRegisterClick(ActionEvent event) {
        
        if(usernameTextField.getText().isEmpty()){
            usernameError.setText("Please Enter Your Username");
            usernameTextField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15;");
        }
        
        if(nameTextField.getText().isEmpty()){
            nameError.setText("Please Enter Your Name"); 
            nameTextField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15;");
        }
         
        if(passwordTextField.getText().isEmpty()){
            passwordError.setText("Please Enter Your Password");
            passwordTextField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15;");
        }
        
      if(!usernameTextField.getText().isEmpty()&&!nameTextField.getText().isEmpty()&&!passwordTextField.getText().isEmpty()){
        
                   
        
//        //test to send data 
//        Stage stage = (Stage)registerButton.getScene().getWindow();
//        new ConnectionsHandler(stage);
        
        player.setUserName(usernameTextField.getText());
        player.setName(nameTextField.getText());
        player.setPassword(passwordTextField.getText());
        
        // create an array to save the code of registeration and the player object then convert the array to a json string
        ArrayList requestArr = new ArrayList();
        requestArr.add(Codes.REGESTER_CODE);
        //convert the player object to be string to add it to the array
        requestArr.add(gson.toJson(player));
        
        System.out.println(requestArr.get(0).getClass().getName());  
        //now we have array of strings contain code and object, 

        //so we need to convert all the array to string to send it to the server
        String jsonRegisterationRequest = gson.toJson(requestArr);
        
        // now we need to send this string to the server using the sendRequest function
        TicTacToeClient.connectionHandler.sendRequest(jsonRegisterationRequest);
        System.out.println("the sendRequest data is: "+jsonRegisterationRequest);
        
        
        
         }   
        
    }
    
    @FXML
    void onLoginBtnClicked(ActionEvent event) {
        
        navigator.goToPage(event, "LoginScreen.fxml");

    }
}
