/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Ziad-Elshemy
 */
public class Navigator {
    public void goToPage(ActionEvent event,String targetPage){
        
        try {
            System.out.println("You clicked me!");
            //label.setText("Hello World!");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(targetPage));
            Scene page1Scene = new Scene(loader.load());
            // Get current stage and set new scene (Page 1) 
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(page1Scene);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void goToPage(Stage stage, String targetPage){
        
        try {
            System.out.println("You clicked me!");
            //label.setText("Hello World!");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(targetPage));
            Scene page1Scene = new Scene(loader.load());
            // Get current stage and set new scene (Page 1) 
            stage.setScene(page1Scene);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
