/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.registerscene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author abdullahraed
 */
public class RegisterSceneController implements Initializable {

    @FXML
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
        normalPane.setBackground(new Background(new BackgroundFill(Color.rgb(22, 105, 122),null,null)));
        registerButton.setBackground(new Background(new BackgroundFill(Color.rgb(130,192, 204),null,null)));
        registerButton.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100%  100%, #82C0CC,#edf6f9);"+" "+"-fx-background-radius : 10;");
        innerPane.setStyle("-fx-border-radius: 15;"+" "+"-fx-background-radius: 15;"+" "+"-fx-padding: 10; "+" "+"-fx-background-color: white;");
    }    

    @FXML
    private void onRegisterClick(ActionEvent event) 
    {
        sceneSwitch(event);
        System.out.println("Register Button Clicked");
    }
    
     private void sceneSwitch(ActionEvent e)
    {
        try {
            Parent sceneParent = FXMLLoader.load(getClass().getResource("TestScene.fxml"));
            Scene tempScene = new Scene(sceneParent);
            Stage window = (Stage)(((Node)e.getSource()).getScene().getWindow());
            window.setScene(tempScene);
            window.show();
        } 
        catch (IOException ex) {
            System.out.println("cannot Find the desired fxml file to load on the Stage");
        }
    }
}
