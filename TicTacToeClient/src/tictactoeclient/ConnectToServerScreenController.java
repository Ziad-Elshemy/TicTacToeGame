/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import onlineplaying.NetworkAccessLayer;

/**
 * FXML Controller class
 *
 * @author STW
 */
public class ConnectToServerScreenController implements Initializable {

    @FXML
    private Button okayButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField ipTextArea;
    @FXML
    private Label checkLabel;
    
    Navigator navigator;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        navigator = new Navigator();
    }    

    @FXML
    private void onOkayButton(ActionEvent event) {
        if(ipTextArea.getText().isEmpty())
        {
            checkLabel.setText("Please enter the server IP");
            ipTextArea.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15;");
        }
        else if(isIpValid(ipTextArea.getText())){
            NetworkAccessLayer.serverIP = ipTextArea.getText();
            Stage myStage=  (Stage)okayButton.getScene().getWindow();
            NetworkAccessLayer.isServerOffline=false;
            NetworkAccessLayer.local=false;
            NetworkAccessLayer.startConnectionHandling();
            myStage.close();
            Platform.runLater(()->{
                 navigator.goToPage(TicTacToeClient.mainStage, "LoginScreen.fxml");
             });
            
        }
        else 
        {
            checkLabel.setText("Please enter correct IP");
            ipTextArea.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15;");
        }
    }

    @FXML
    private void onCancelButton(ActionEvent event) {
        
            NetworkAccessLayer.isServerOffline=true;
            NetworkAccessLayer.local=true;
            Stage myStage=  (Stage)okayButton.getScene().getWindow();
            myStage.close();
            Platform.runLater(()->{
                 navigator.goToPage(TicTacToeClient.mainStage, "LoginScreen.fxml");
             });
          
    }
    private boolean isIpValid(String ip)
    {
        String ipPattern = "^(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\\." +
                                               "(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\\." +
                                               "(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\\." +
                                               "(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)$";
        Pattern myPattern = Pattern.compile(ipPattern);
        return myPattern.matcher(ip).matches();
    }
}
