/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Ziad-Elshemy
 */
public class FXMLGameScreenController implements Initializable {
    
    int counter;
    int playerXScore;
    int playerOScore;
    Navigator navigator;
    ArrayList<String> boardState;

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
    private Button playerXScoreBtn;
    @FXML
    private Button drawScoreBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private Button newGameBtn;
    @FXML
    private Button playerTurnBtn;
    @FXML
    private Button playerOScoreBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //exitBtn.setStyle("-fx-background-color: linear-gradient(from 100% 0% to 0% 0%, #CC8282, #EDF6F9);");
        navigator = new Navigator();
        playerXScore = 0;
        playerOScore = 0;
        initializeBoardState();
        disableBoard();
        counter = 0;
    }    


    @FXML
    private void exitBtnAction(ActionEvent event) {
        navigator.goToPage(event, "Login.fxml");
    }

    @FXML
    private void newGameBtnAction(ActionEvent event) {
        enableBoard();
        playerTurnBtn.setVisible(true);
        newGameBtn.setVisible(false);
        playerTurnBtn.setText("X-TURN");
        playerTurnBtn.setStyle("-fx-background-color: #83C5BE");
        initializeBoardState();
    }


    @FXML
    private void onPlayerClick(ActionEvent event) {
        Button button = (Button)event.getSource();
        String playerSympol = "";
        if(!button.getText().toString().isEmpty()){
            return;
        }
        if(counter %2 == 0){
            playerSympol = "X";
            button.setText(playerSympol);
            button.setTextFill(Colors.X_TEXT);
            playerTurnBtn.setText("O-TURN");
            playerTurnBtn.setStyle("-fx-background-color: #FFA62B");
        }else{
            playerSympol = "O";
            button.setText(playerSympol);
            button.setTextFill(Colors.O_TEXT);
            playerTurnBtn.setText("X-TURN");
            playerTurnBtn.setStyle("-fx-background-color: #83C5BE");
        }
        counter++;
        
        writePlayerSymolInArray(button, playerSympol);
        
        if(checkWinner("X")){
            playerXScore+=10;
            playerXScoreBtn.setText(""+playerXScore);
            //initializeBoardState();
            playerTurnBtn.setVisible(false);
            newGameBtn.setVisible(true);
            //disableBoard();
            counter=0;
        }else if(checkWinner("O")){
            playerOScore+=10;
            playerOScoreBtn.setText(""+playerOScore);
            //initializeBoardState();
            playerTurnBtn.setVisible(false);
            newGameBtn.setVisible(true);
            //disableBoard();
            counter=0;
        }else if(counter == 9){
            playerXScore+=5;
            playerOScore+=5;
            playerXScoreBtn.setText(""+playerXScore);
            playerOScoreBtn.setText(""+playerOScore);
            //initializeBoardState();
            playerTurnBtn.setVisible(false);
            newGameBtn.setVisible(true);
            //disableBoard();
            counter=0;
        }
        
    }
    
    void writePlayerSymolInArray(Button button,String playerSympol){
        if(button.getId().equals(btn1.getId())){
            boardState.set(0, playerSympol);
        }else if(button.getId().equals(btn2.getId())){
            boardState.set(1, playerSympol);
        }else if(button.getId().equals(btn3.getId())){
            boardState.set(2, playerSympol);
        }else if(button.getId().equals(btn4.getId())){
            boardState.set(3, playerSympol);
        }else if(button.getId().equals(btn5.getId())){
            boardState.set(4, playerSympol);
        }else if(button.getId().equals(btn6.getId())){
            boardState.set(5, playerSympol);
        }else if(button.getId().equals(btn7.getId())){
            boardState.set(6, playerSympol);
        }else if(button.getId().equals(btn8.getId())){
            boardState.set(7, playerSympol);
        }else if(button.getId().equals(btn9.getId())){
            boardState.set(8, playerSympol);
        }
    }
    
    private void disableBoard(){
        btn1.setDisable(true);
        btn2.setDisable(true);
        btn3.setDisable(true);
        btn4.setDisable(true);
        btn5.setDisable(true);
        btn6.setDisable(true);
        btn7.setDisable(true);
        btn8.setDisable(true);
        btn9.setDisable(true);
    }
    private void enableBoard(){
        btn1.setDisable(false);
        btn2.setDisable(false);
        btn3.setDisable(false);
        btn4.setDisable(false);
        btn5.setDisable(false);
        btn6.setDisable(false);
        btn7.setDisable(false);
        btn8.setDisable(false);
        btn9.setDisable(false);
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
    
    private void initializeBoardState(){
    
        boardState = new ArrayList<>();
        
        for(int i=0; i<9; i++){
            boardState.add("");
        }
        reInitializeBoard();
        
    }

    //["X","x","X",
    // "o","X","o",
    // "X","o","X"]
    private boolean checkWinner(String playerSympol) {
        for(int i=0; i<9; i+=3){
            // check for rows winner
            if(boardState.get(i).toString().equals(playerSympol)&&
               boardState.get(i+1).toString().equals(playerSympol)&&
               boardState.get(i+2).toString().equals(playerSympol)){
                System.out.println("winner by rows");
                if(i==0){
                    btn1.setStyle("-fx-background-color: #00FF00");
                    btn2.setStyle("-fx-background-color: #00FF00");
                    btn3.setStyle("-fx-background-color: #00FF00");
                }
                if(i==3){
                    btn4.setStyle("-fx-background-color: #00FF00");
                    btn5.setStyle("-fx-background-color: #00FF00");
                    btn6.setStyle("-fx-background-color: #00FF00");
                }
                if(i==6){
                    btn7.setStyle("-fx-background-color: #00FF00");
                    btn8.setStyle("-fx-background-color: #00FF00");
                    btn9.setStyle("-fx-background-color: #00FF00");
                }
                return true;
            }   
        }
        for(int i=0; i<3; i++){
            // check for columns winner
            if(boardState.get(i).toString().equals(playerSympol)&&
               boardState.get(i+3).toString().equals(playerSympol)&&
               boardState.get(i+6).toString().equals(playerSympol)){
                
                System.out.println("winner by columns");
                return true;
            }   
        }
        // check for diagonals winner
        if(boardState.get(0).toString().equals(playerSympol)&&
           boardState.get(4).toString().equals(playerSympol)&&
           boardState.get(8).toString().equals(playerSympol)){
            System.out.println("winner by diagonals");
            return true;
        }
        // check for diagonals winner
        if(boardState.get(2).toString().equals(playerSympol)&&
           boardState.get(4).toString().equals(playerSympol)&&
           boardState.get(6).toString().equals(playerSympol)){
            System.out.println("winner by diagonals");
            return true;
        }
    return false;
    }
    
}