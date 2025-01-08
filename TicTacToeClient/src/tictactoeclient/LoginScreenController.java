/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author youse
 */
public class LoginScreenController implements Initializable {
    
    Navigator navigator;

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
        navigator = new Navigator();
        centerVBox();

        // Add listeners to handle resizing dynamically
//        rootPane.heightProperty().addListener((obs, oldVal, newVal) -> centerVBox());
//        rootPane.widthProperty().addListener((obs, oldVal, newVal) -> centerVBox());
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
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
            Scene page1Scene = new Scene(loader.load());
            
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(page1Scene);
            stage.show();
        } catch (IOException ex) {
            
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void goregister(ActionEvent event) {
        
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterScreen.fxml"));
            Scene page1Scene = new Scene(loader.load());
            
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(page1Scene);
            stage.show();
        } catch (IOException ex) {
            
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void localGameBtnAction(ActionEvent event) {
        navigator.goToPage(event, "FXMLGameScreen.fxml");
    }
    
}
