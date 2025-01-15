/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tictactoeclient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author abdullahraed
 */
public class RegisterScreenController implements Initializable {
    
    Navigator navigator;

    private Pane normalPane;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button registerButton;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private ImageView logoImage;
    @FXML
    private Pane innerPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        navigator = new Navigator();
        registerButton.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100%  100%, #82C0CC,#edf6f9);"+" "+"-fx-background-radius : 10;");
    }    

    @FXML
    private void onRegisterClick(ActionEvent event) 
    {
        System.out.println("Register Button Clicked");
        navigator.goToPage(event, "HomeScreen.fxml");
    }
}
