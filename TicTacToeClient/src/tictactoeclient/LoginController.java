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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Ziad-Elshemy
 */
public class LoginController implements Initializable {
    
    Navigator navigator;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private VBox mainVBox;
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
    private ImageView logoImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        navigator = new Navigator();
    }    

    @FXML
    private void gologin(ActionEvent event) {
    }

    @FXML
    private void goregister(ActionEvent event) {
        navigator.goToPage(event, "RegisterScreen.fxml");
    }

    @FXML
    private void localGameBtnAction(ActionEvent event) {
        navigator.goToPage(event, "FXMLGameScreen.fxml");
    }
    
}
