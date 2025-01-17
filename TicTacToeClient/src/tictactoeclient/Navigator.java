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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
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
    public void luanchInvitation(String targetPage,String pageTitle,String sender){
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(targetPage));
            Parent root = loader.load();
            
            FXMLInvitationAlertController controller = loader.getController();
            controller.setSenderLabel(sender);

            Stage popupStage = new Stage();
            popupStage.setTitle(pageTitle);
            popupStage.setScene(new Scene(root));

            //to prevent the user to interact with the background stage
            popupStage.initModality(Modality.APPLICATION_MODAL);

    //        popupStage.setOnCloseRequest(event->{
    //            System.out.println("Invitation Rejected by X icon");
    //        });

            //showAndWait to prevent the user to interact with the background stage
            popupStage.showAndWait(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void luanchWaiting(String targetPage,String pageTitle,String reciever){
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(targetPage));
            Parent root = loader.load();
            
            FXMLWaitingAlertController controller = loader.getController();
            controller.setRecieverLabel(reciever);

            Stage popupStage = new Stage();
            popupStage.setTitle(pageTitle);
            popupStage.setScene(new Scene(root));

            //to prevent the user to interact with the background stage
            popupStage.initModality(Modality.APPLICATION_MODAL);

            //showAndWait to prevent the user to interact with the background stage
            popupStage.showAndWait(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
