/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import onlineplaying.NetworkAccessLayer;

/**
 * FXML Controller class
 *
 * @author STW
 */
public class ServerDisconnectController implements Initializable {

    @FXML
    private Button okayButton;
     Navigator navigator ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         navigator = new Navigator();
    }    

    @FXML
    private void onOkayButton(ActionEvent event) {
        
        NetworkAccessLayer.isServerOffline=true;
        NetworkAccessLayer.local=true;
        NetworkAccessLayer.playerData = null ;
        Stage myStage=  (Stage)okayButton.getScene().getWindow();
        myStage.close();
        Platform.runLater(()->{
             navigator.goToPage(TicTacToeClient.mainStage, "LoginScreen.fxml");
         });
    }
    
}
