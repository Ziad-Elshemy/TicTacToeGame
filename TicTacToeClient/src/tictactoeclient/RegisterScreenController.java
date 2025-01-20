/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import onlineplaying.PlayerDto;
import utilities.Codes;
import onlineplaying.NetworkAccessLayer;

/**
 * FXML Controller class
 *
 * @author abdullahraed
 */

public class RegisterScreenController implements Initializable,Listener {
    ActionEvent myEvent;
    Navigator navigator;
    Gson gsonFile;
    PlayerDto player;
    Alert myAlert;
    String username;
    String name;
    String password;
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
    private Button backButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        NetworkAccessLayer.setRef(this);
        myAlert=new Alert(Alert.AlertType.INFORMATION);
        gsonFile = new Gson();
        navigator = new Navigator();
        registerButton.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100%  100%, #82C0CC,#edf6f9);"+" "+"-fx-background-radius : 10;");
    }    

    @FXML
    private void onRegisterClick(ActionEvent event) 
    {
        myEvent=event;
        System.out.println("Register Button Clicked");
        if( usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty() || nameTextField.getText().isEmpty() )
        {
            myAlert.setContentText("Please fill all the given Fields to commplete your Registeration");
            myAlert.showAndWait();
        }
        else
        {
            username=usernameTextField.getText();
            name=nameTextField.getText();
            password=passwordTextField.getText();
            player = new PlayerDto(username, name, password, false, false, 0);
            // create an array to save the code of registeration and the player object then convert the array to a json string
            ArrayList requestArr = new ArrayList();
            requestArr.add(Codes.REGESTER_CODE);
            //convert the player object to be string to add it to the array
            requestArr.add(gsonFile.toJson(player));
            //now we have array of strings contain code and object, 
            //so we need to convert all the array to string to send it to the server
            String jsonRegisterationRequest = gsonFile.toJson(requestArr);
            // now we need to send this string to the server using the sendRequest function
            NetworkAccessLayer.sendRequest(jsonRegisterationRequest);
        }
    }
 
    @FXML
    private void onBackButton(ActionEvent event) 
    {
        System.out.println("back button clicked");
        Platform.runLater(()->{
              navigator.goToPage(event, "LoginScreen.fxml");
        });
    }
    
    @Override
    public void onServerResponse(boolean success, ArrayList responseData)
    {
        if(success)
         {

           Platform.runLater(()->{
                try {
                    Stage newStage = new Stage();
                    newStage.initModality(Modality.APPLICATION_MODAL);
                    Parent root = FXMLLoader.load(getClass().getResource("CompleteRegisterationScreen.fxml"));
                    Scene scene = new Scene(root);
                    newStage.setScene(scene);
                    newStage.show();
                    
            }
            catch (IOException ex){
                Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
           });
            

             System.out.println("Test the array data" + responseData.toString());

             Platform.runLater(()->{
                 navigator.goToPage(myEvent, "LoginScreen.fxml");
                });
         }
         else
         {
              Platform.runLater(()->{
                 myAlert = new Alert(Alert.AlertType.INFORMATION);
                 myAlert.setContentText("Dublicate Username please choose another one");
                 myAlert.showAndWait();
              });
         }
    }
}
