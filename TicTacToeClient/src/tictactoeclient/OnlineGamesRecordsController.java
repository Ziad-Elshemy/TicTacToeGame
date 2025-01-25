/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author HANY
 */
public class OnlineGamesRecordsController implements Initializable {
    Navigator navigator;

    @FXML
    private StackPane rootPane;
    @FXML
    private Button allRecordsBtn;
    @FXML
    private Button btn1;
    @FXML
    private Button btn4;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button btn9;
    @FXML
    private Button exitBtn;
    @FXML
    private Button playerTurnBtn;
    @FXML
    private VBox recordFilesListBox;
    @FXML
    private Label file1Lable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        navigator  = new Navigator();
        
    
    }  
    
    



    @FXML
    private void onallRecordsBtnAction(ActionEvent event) {
        recordFilesListBox.getChildren().clear();
        ShowFiles();
    }


    @FXML
    private void exitBtnAction(ActionEvent event) {
        navigator.goToPage(event, "FXMLEditProfile.fxml");
        
    }
    private void ShowFiles ()
    {
        File directory = new File("src/onlineGames");
        File[] files = directory.listFiles();
        
        //files = RecordsList.getRecordsFiles();
        if(files != null)
        {
            
            for(File file :files)
            {
                //System.out.println("file :::"+file.getName());
                Label lable = new Label(file.getName());
                lable.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-padding: 5px; -fx-font-weight: bold;");
               lable.setOnMouseEntered((e)->{
                     lable.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-padding: 5px; -fx-font-weight: bold;");
              
               });
               lable.setOnMouseExited((e)->{
                      lable.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-padding: 5px; -fx-font-weight: bold;");

               });
               
                lable.setOnMouseClicked((e)->{
                   reInitializeBoard();
                    startReplayGame(file.getName());
                    
                });
                Platform.runLater(()->{
                recordFilesListBox.getChildren().add(lable);
                    
                });
            }
        }
        else {System.out.println("Error On File Loading");}
    }
 private void startReplayGame(String fileName)
 {
    ArrayList<GameTracker.Move> moves = RecordFile.readFromFile("src/onlineGames/"+fileName);
    GameReplay gamereplay = new GameReplay();
    gamereplay.replayGame(moves,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9);

 }
 
     private void reInitializeBoard(){
        btn1.setText("");
        btn1.setStyle("-fx-background-color: #16697A");
        btn2.setText("");
        btn2.setStyle("-fx-background-color: #16697A");
        btn3.setText("");
        btn3.setStyle("-fx-background-color: #16697A");
        btn4.setText("");
        btn4.setStyle("-fx-background-color: #16697A");
        btn5.setText("");
        btn5.setStyle("-fx-background-color: #16697A");
        btn6.setText("");
        btn6.setStyle("-fx-background-color: #16697A");
        btn7.setText("");
        btn7.setStyle("-fx-background-color: #16697A");
        btn8.setText("");
        btn8.setStyle("-fx-background-color: #16697A");
        btn9.setText("");
        btn9.setStyle("-fx-background-color: #16697A");
    }

    @FXML
    private void onPlayerClick(ActionEvent event) {
    }
}
