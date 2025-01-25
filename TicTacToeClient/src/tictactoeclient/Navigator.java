/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import onlineplaying.NetworkAccessLayer;
import onlineplaying.PlayerDto;
import utilities.Codes;

/**
 *
 * @author Ziad-Elshemy
 */
public class Navigator {
    Gson gsonFile;

    public Navigator() {
        
       gsonFile=new Gson();
    }
    
    
    
    
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
            stage.setOnCloseRequest((e)->{
            
             
                 
                if(NetworkAccessLayer.mySocket!=null){
                    
                   ArrayList arr=new ArrayList();
                   arr.add(Codes.LOGOUT_CODE);

                   System.out.println(arr);

                   NetworkAccessLayer.toServer.println(gsonFile.toJson(arr)); 
                   
                   
                   Platform.runLater(()->{
                       
                       try {
                           NetworkAccessLayer.thread.stop();
                           
                           NetworkAccessLayer.mySocket.close();
                       } catch (IOException ex) {
                           Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   
                   
                   
                   }); 
                    
                    
           
                   
                 
                 }
                 Platform.exit();
             
         });
            

            
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
            
            
            stage.setOnCloseRequest((e)->{
                
                
                
                if(NetworkAccessLayer.mySocket!=null){
                    
                    ArrayList arr=new ArrayList();
                    arr.add(Codes.LOGOUT_CODE); 
                    
                    System.out.println(arr);
                    
                    NetworkAccessLayer.toServer.println(gsonFile.toJson(arr));
                    
                    
                    Platform.runLater(()->{
                        
                        try {
                            NetworkAccessLayer.thread.stop();
                            
                            NetworkAccessLayer.mySocket.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                        
                    });
                    
                    
                    
                    
                    
                }
                Platform.exit();
                
            });
        } catch (IOException ex) {
            Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
        }
            
      
    }
    
    public void luanchOnlineGame(Stage stage, String targetPage, PlayerDto enemy, String Sympol){
        
        try {
            System.out.println("You clicked me!");
            //label.setText("Hello World!");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(targetPage));
            Scene page1Scene = new Scene(loader.load());
            System.out.println("FXML loaded successfully");
            
            OnlineGameController controller = loader.getController();
            controller.setEnemyUsername(enemy.getUserName());
            controller.setMySympol(Sympol);
            
            // Get current stage and set new scene (Page 1) 
            stage.setScene(page1Scene);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void goToPage(ActionEvent event,String targetPage,PlayerDto player){
        
        try {
            System.out.println("You clicked me!");
            //label.setText("Hello World!");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(targetPage));
            Scene page1Scene = new Scene(loader.load());
            EditProfileController editController = loader.getController();
            editController.setData(player);
            // Get current stage and set new scene (Page 1) 
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(page1Scene);
            stage.show();
            
            stage.setOnCloseRequest((e)->{
            
             
                 
                if(NetworkAccessLayer.mySocket!=null){
                    
                   ArrayList arr=new ArrayList();
                   arr.add(Codes.LOGOUT_CODE);

                   System.out.println(arr);

                   NetworkAccessLayer.toServer.println(gsonFile.toJson(arr)); 
                   
                   
                   Platform.runLater(()->{
                       
                       try {
                           NetworkAccessLayer.thread.stop();
                           
                           NetworkAccessLayer.mySocket.close();
                       } catch (IOException ex) {
                           Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   
                   
                   
                   }); 
                    
                    
           
                   
                 
                 }
                 Platform.exit();
             
         });
            
        } catch (IOException ex) {
            Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void luanchInvitation(String targetPage,String pageTitle,PlayerDto sender){
        
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
    public void popUpStage(String sceneName)
    {
        try {
                Stage newStage = new Stage();
                newStage.initModality(Modality.APPLICATION_MODAL);
                Parent root = FXMLLoader.load(getClass().getResource(sceneName));
                Scene scene = new Scene(root);
                newStage.setScene(scene);
                newStage.show();
            }
            catch (IOException ex){
                Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
