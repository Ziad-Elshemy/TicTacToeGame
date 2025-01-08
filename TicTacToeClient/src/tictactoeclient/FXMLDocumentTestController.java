/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Ziad-Elshemy
 */
public class FXMLDocumentTestController implements Initializable {
    
    Navigator navigator;
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
         navigator.goToPage(event,"FXMLGameScreen.fxml");

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        navigator = new Navigator();
    }
    
}
