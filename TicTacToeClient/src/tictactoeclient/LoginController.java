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
 * @author youse
 */
public class LoginController implements Initializable {

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
    private AnchorPane rootPane;
    @FXML
    private VBox mainVBox;
    @FXML
    private ImageView logoImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         // Center the VBox when the window is first loaded
        centerVBox();

        // Add listeners to handle resizing dynamically
        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> centerVBox());
        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> centerVBox());
    }

    private void centerVBox() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();
        double vboxWidth = mainVBox.getWidth();
        double vboxHeight = mainVBox.getHeight();

        // Center the VBox dynamically
        AnchorPane.setTopAnchor(mainVBox, (height - vboxHeight) / 2);
        AnchorPane.setLeftAnchor(mainVBox, (width - vboxWidth) / 2);
    
    }    

    @FXML
    private void gologin(ActionEvent event) {
    }

    @FXML
    private void goregister(ActionEvent event) {
    }
    
}