/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import com.google.gson.Gson;
import utilities.Colors;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import onlineplaying.NetworkAccessLayer;
import utilities.Codes;
import utilities.Strings;

/**
 * FXML Controller class
 *
 * @author Ziad-Elshemy
 */
public class OnlineGameController implements Initializable,Listener {
    
    int counter;
    int playerXScore;
    int playerOScore;
    int drawScore;
    boolean isGameEnded = false;
    Navigator navigator;
    ArrayList<String> boardState;
    
    GameTracker tracker; /// record
    boolean isRecording;   //// record

    Alert alert;
    
    
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
    @FXML
    private Polygon leftPolygon;
    @FXML
    private Polygon rightPolygon;
    @FXML
    private Label gameOverToast;
    @FXML
    private Rectangle gameOverRect;
    @FXML
    private StackPane rootPane;
    @FXML
    private Button RecordBtn;
    private VBox recordFilesListBox;
    
    String enemyUserName;
    String mySympol;
    Gson gson;
    @FXML
    private GridPane gridPaneId;
    @FXML
    private Label playerXNameLabel;
    @FXML
    private Label playerONameLabel;
    
    @FXML
    private Text playerOneUsername;
    
    @FXML
    private Text playerTwoUsername;

    @FXML
    private ImageView playerTwoImage;
    
    @FXML
    private ImageView playerOneImage;
    
    @FXML
    private Text playerOneScore;

    @FXML
    private Text playerTwoScore;
    
    @FXML
    private ImageView muteImg;
    
    String nameOfCurrenyPlayer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        playerOneUsername.setText(NetworkAccessLayer.playerData.getUserName());
        playerTwoUsername.setText(NetworkAccessLayer.enemyData.getUserName());
        playerOneImage.setImage(NetworkAccessLayer.playerData.getGender().equals("Male")?new Image("file:src/Images/boy.png"):NetworkAccessLayer.playerData.getGender().isEmpty()?new Image("file:src/Images/x.png"):new Image("file:src/Images/girl.png"));
        playerTwoImage.setImage(NetworkAccessLayer.playerData.getGender().equals("Male")?new Image("file:src/Images/boy.png"):NetworkAccessLayer.playerData.getGender().isEmpty()?new Image("file:src/Images/x.png"):new Image("file:src/Images/girl.png"));
        playerOneScore.setText("Score: "+String.valueOf(NetworkAccessLayer.playerData.getScore()));
        playerTwoScore.setText("Score: "+String.valueOf(NetworkAccessLayer.enemyData.getScore())); 


        NetworkAccessLayer.setRef(this);
        gson = new Gson();
        
        tracker = new GameTracker();  
        
        navigator = new Navigator();
        playerXScore = 0;
        playerOScore = 0;
        drawScore = 0;
        
        enableBoard();
        isGameEnded = false;
        playerTurnBtn.setVisible(true);
        newGameBtn.setText("Play Again");
        newGameBtn.setVisible(false); 
        playerTurnBtn.setText("X-TURN");
        playerTurnBtn.setStyle("-fx-background-color: #83C5BE");
        initializeBoardState();
        
        RecordBtn.setDisable(false);////record
        tracker.clearMoves();
        
        initializeBoardState();
        counter = 0;
        isRecording = false; //record
        
        if (!TicTacToeClient.isMuted) {

            muteImg.setImage(new Image("file:src/Images/volume.png"));

        } else {

            muteImg.setImage(new Image("file:src/Images/mute.png"));

        }
    }   
    
     @FXML
    void onMuteBtnClicked(ActionEvent event) {

        if (TicTacToeClient.isMuted) {
            TicTacToeClient.mediaPlayer.play();
            muteImg.setImage(new Image("file:src/Images/volume.png"));
            TicTacToeClient.isMuted = false;

        } else {
            TicTacToeClient.mediaPlayer.pause();
            muteImg.setImage(new Image("file:src/Images/mute.png"));
            TicTacToeClient.isMuted = true;

        }

    }

    public void setEnemyUsername(String enemyUsername){
        this.enemyUserName = enemyUsername;
        System.out.println("My Enemy is: "+enemyUserName);
    }
    public void setMySympol(String sympol){
        this.mySympol = sympol;
        System.out.println("My Sympol is: "+mySympol);
        if(counter==0 && mySympol.equals("O")){
            disableMouseClick();
        }else{
            enableMouseClick();
        }
    }

    @FXML
    private void exitBtnAction(ActionEvent event) {
        
        
        if(isGameEnded){
        Platform.runLater(()->{
            
            
            alert= new Alert(Alert.AlertType.CONFIRMATION, "You Sure You Want Leave?", ButtonType.YES,ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                               
                ArrayList arr=new ArrayList();
                arr.add(Codes.LEAVE_GAME_CODE);
                System.out.println(arr);
                NetworkAccessLayer.toServer.println(gson.toJson(arr)); 

                navigator.goToPage(event, "HomeScreen.fxml");

//                NetworkAccessLayer.playerData.setIsPlaying(false);           
            }
        
        });
        }else{
            
            Platform.runLater(()->{
            alert= new Alert(Alert.AlertType.CONFIRMATION, "If you leave now, your score will decrease by one", ButtonType.YES,ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                
                ArrayList requestArr = new ArrayList();
                requestArr.add(Codes.UPDATE_PLAYER_SCORE);
                System.out.println("hi "+ NetworkAccessLayer.playerData.getScore());  
                int newScore = NetworkAccessLayer.playerData.getScore() - 1;
                requestArr.add(newScore);

                String jsonRegisterationRequest = gson.toJson(requestArr);
                NetworkAccessLayer.sendRequest(jsonRegisterationRequest);
                               
                ArrayList arr=new ArrayList();
                arr.add(Codes.LEAVE_GAME_CODE);
                System.out.println(arr);
                NetworkAccessLayer.toServer.println(gson.toJson(arr)); 

                navigator.goToPage(TicTacToeClient.mainStage, "HomeScreen.fxml");

            }  });
        
        
        
        
        }
        
        
    }

    @FXML
    private void newGameBtnAction(ActionEvent event) {
        if(counter==0 && mySympol.equals("O")){
            disableMouseClick();
        }else{
            enableMouseClick();
        }
        enableBoard();
        isGameEnded = false;
        playerTurnBtn.setVisible(true);
        newGameBtn.setVisible(false);
        playerTurnBtn.setText("X-TURN");
        playerTurnBtn.setStyle("-fx-background-color: #83C5BE");
        initializeBoardState();
        
        RecordBtn.setDisable(false);////record
        tracker.clearMoves();
        
        //send to the other player
        ArrayList requestArr = new ArrayList();
        requestArr.add(Codes.PLAY_AGAIN_CODE);
        requestArr.add(enemyUserName);
        String jsonRegisterationRequest = gson.toJson(requestArr);
        NetworkAccessLayer.sendRequest(jsonRegisterationRequest);
    }


    @FXML
    private void onPlayerClick(ActionEvent event) throws IOException {
        
        Button button = (Button)event.getSource();
        String playerSympol = "";
        
        
        ArrayList<String> gameData = new ArrayList();
        gameData.add(enemyUserName);
        gameData.add(mySympol);
        gameData.add(button.getId());
        
        ArrayList requestArr = new ArrayList();
        requestArr.add(Codes.SEND_PLAY_ON_BOARD_CODE);
        System.out.println("hi "+ enemyUserName);        
        requestArr.add(gson.toJson(gameData));
        
        String jsonRegisterationRequest = gson.toJson(requestArr);
        NetworkAccessLayer.sendRequest(jsonRegisterationRequest);
        
        if(!button.getText().toString().isEmpty()||isGameEnded){
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
        disableMouseClick();
        
        if (counter > 0)  // check if the game is at the beginning
        {
            RecordBtn.setDisable(true);
        }
        
        if(isRecording)
        {
            tracker.recordMove(button.getId(), playerSympol.charAt(0));
        }
        
        writePlayerSymolInArray(button, playerSympol);
        
        if(checkWinner("X","-fx-background-color: #008000")){
            playerXScore+=1;
            playerXScoreBtn.setText(""+playerXScore);
            //initializeBoardState();
            playerTurnBtn.setVisible(false);
            newGameBtn.setVisible(true);
            String text = "Winner";
            showGameOverToast(text);
            if(isRecording)
            {
                 tracker.saveToFile("src/onlineGames/","");  ////add record to file ///???????????
                 isRecording = false; ///
            }
            //disableBoard();
            counter=0;

            showVideo(Strings.winnerVideoPath,"X - Winner");
            //showVideo(Strings.loserVideoPath, "O - loser"); 
            
            updatePlayerScore();
            
        }else if(checkWinner("O","-fx-background-color: #008000")){
            playerOScore+=1;
            playerOScoreBtn.setText(""+playerOScore);
            //initializeBoardState();
            playerTurnBtn.setVisible(false);
            newGameBtn.setVisible(true);
            String text = "Winner";
            showGameOverToast(text);
            if(isRecording)
            {
                 tracker.saveToFile("src/onlineGames/","");  ////add record to file ///???????????
                 isRecording = false; ///
            }
            //disableBoard();
            counter=0;
            // check for draw
            showVideo(Strings.winnerVideoPath,"O - Winner");
            //showVideo(Strings.loserVideoPath, "X - loser");
            updatePlayerScore();
        }else if(counter == 9){
            //playerXScore+=5;
            //playerOScore+=5;
            drawScore+=1;
            playerXScoreBtn.setText(""+playerXScore);
            playerOScoreBtn.setText(""+playerOScore);
            drawScoreBtn.setText(""+drawScore);
            //initializeBoardState();
            playerTurnBtn.setVisible(false);
            newGameBtn.setVisible(true);
            String text = "It's draw";
            showGameOverToast(text);
            if(isRecording)
            {
                 tracker.saveToFile("src/onlineGames/","");  ////add record to file ///???????????
                 isRecording = false; ///
            }
            //disableBoard();
            counter=0;
            showVideo(Strings.drawVideoPath, "Draw");
        }
        
    }
    
    
    void showVideo(String vidoeUrl , String symbol) throws IOException{
        
        VideoPlayerController.videoUrl=vidoeUrl;
        Parent root = FXMLLoader.load(getClass().getResource("VideoPlayer.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY); 
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(symbol);
        stage.show();

      


        stage.setX(TicTacToeClient.primaryX + (TicTacToeClient.primaryWidth - stage.getWidth()) / 2);
        stage.setY(TicTacToeClient.primaryY + (TicTacToeClient.primaryHeight - stage.getHeight()) / 2);
        
        
        
        
        
        
        stage.setOnCloseRequest((event)->{
            
            VideoPlayerController.mediaPlayer.pause();
            if(!TicTacToeClient.isMuted){
               TicTacToeClient.mediaPlayer.play();
            
            }

        });
        
       VideoPlayerController.mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                
                stage.close();
                if(!TicTacToeClient.isMuted){
                   TicTacToeClient.mediaPlayer.play();
            
            }
                
                
            }
        });
        
        
        

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
    
    private void enableMouseClick(){
        btn1.setMouseTransparent(false);
        btn2.setMouseTransparent(false);
        btn3.setMouseTransparent(false);
        btn4.setMouseTransparent(false);
        btn5.setMouseTransparent(false);
        btn6.setMouseTransparent(false);
        btn7.setMouseTransparent(false);
        btn8.setMouseTransparent(false);
        btn9.setMouseTransparent(false);
    }
    
    private void disableMouseClick(){
        btn1.setMouseTransparent(true);
        btn2.setMouseTransparent(true);
        btn3.setMouseTransparent(true);
        btn4.setMouseTransparent(true);
        btn5.setMouseTransparent(true);
        btn6.setMouseTransparent(true);
        btn7.setMouseTransparent(true);
        btn8.setMouseTransparent(true);
        btn9.setMouseTransparent(true);
    }
    
    private void stopEditBoard(){
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
        hideGameOverToast();
        reInitializeBoard();
        
    }
    
    private void showGameOverToast(String text){
        leftPolygon.setVisible(true);
        rightPolygon.setVisible(true);
        gameOverRect.setVisible(true);
        gameOverToast.setVisible(true);
        gameOverToast.setText("Game Over. "+ text);
    }
    
    private void hideGameOverToast(){
        leftPolygon.setVisible(false);
        rightPolygon.setVisible(false);
        gameOverRect.setVisible(false);
        gameOverToast.setVisible(false);
    }

    //["X","x","X",
    // "o","X","o",
    // "X","o","X"]
    private boolean checkWinner(String playerSympol,String color) {
        for(int i=0; i<9; i+=3){
            // check for rows winner
            if(boardState.get(i).toString().equals(playerSympol)&&
               boardState.get(i+1).toString().equals(playerSympol)&&
               boardState.get(i+2).toString().equals(playerSympol)){
                System.out.println("winner by rows");
                if(i==0){
                    btn1.setStyle(color);
                    btn2.setStyle(color);
                    btn3.setStyle(color);
                }
                if(i==3){
                    btn4.setStyle(color);
                    btn5.setStyle(color);
                    btn6.setStyle(color);
                }
                if(i==6){
                    btn7.setStyle(color);
                    btn8.setStyle(color);
                    btn9.setStyle(color);
                }
                isGameEnded = true;
                return true;
                
            }   
        }
        for(int i=0; i<3; i++){
            // check for columns winner
            if(boardState.get(i).toString().equals(playerSympol)&&
               boardState.get(i+3).toString().equals(playerSympol)&&
               boardState.get(i+6).toString().equals(playerSympol)){
                
                System.out.println("winner by columns");
                if(i==0){
                    btn1.setStyle(color);
                    btn4.setStyle(color);
                    btn7.setStyle(color);
                }
                if(i==1){
                    btn2.setStyle(color);
                    btn5.setStyle(color);
                    btn8.setStyle(color);
                }
                if(i==2){
                    btn3.setStyle(color);
                    btn6.setStyle(color);
                    btn9.setStyle(color);
                }
                isGameEnded = true;
                return true;
            }   
        }
        // check for diagonals winner
        if(boardState.get(0).toString().equals(playerSympol)&&
           boardState.get(4).toString().equals(playerSympol)&&
           boardState.get(8).toString().equals(playerSympol)){
            System.out.println("winner by diagonals");
            btn1.setStyle(color);
            btn5.setStyle(color);
            btn9.setStyle(color);
            isGameEnded = true;
            return true;
        }
        // check for diagonals winner
        if(boardState.get(2).toString().equals(playerSympol)&&
           boardState.get(4).toString().equals(playerSympol)&&
           boardState.get(6).toString().equals(playerSympol)){
            System.out.println("winner by diagonals");
            btn3.setStyle(color);
            btn5.setStyle(color);
            btn7.setStyle(color);
            isGameEnded = true;
            return true;
        }
    return false;
    }

    @FXML
    private void RecordBtnAction(ActionEvent event) {
        tracker.clearMoves();
        isRecording = true;
        RecordBtn.setText("Recording");
    }

//    private void playrecordBtnAction(ActionEvent event) {
//        if(!tracker.getMoves().isEmpty())
//        {
//             initializeBoardState();
//             disableBoard();
//            /// startReplayGame();
//             RecordBtn.setDisable(false);
//        }
//        
//    }

//    private void onallRecordsBtnAction(ActionEvent event) {  
//        recordFilesListBox.getChildren().clear();
//        ShowFiles();
//        
//    }
    
    
//    private void ShowFiles ()
//    {
//        File directory = new File("src/onlineGames");
//        File[] files = directory.listFiles();
//        
//        //files = RecordsList.getRecordsFiles();
//        if(files != null)
//        {
//            
//            //file1Lable.setText(files[0].getName());
//            
//            for(File file :files)
//            {
//
//                                //int count
//                //System.out.println("File "+count+ " : " +file.getName());
//                Separator separator = new Separator();
//               Label lable = new Label(file.getName());
//               lable.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-padding: 5px; -fx-font-weight: bold;");
//               lable.setOnMouseEntered((e)->{
//                     lable.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-padding: 5px; -fx-font-weight: bold;");
//              
//               });
//               lable.setOnMouseExited((e)->{
//                      lable.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-padding: 5px; -fx-font-weight: bold;");
//
//               });
//                lable.setOnMouseClicked((e)->{
//                    initializeBoardState();
//                    disableBoard();
//                    RecordBtn.setDisable(false);
//                    startReplayGame(file.getName());
//                });
//                Platform.runLater(()->{
//                recordFilesListBox.getChildren().add(lable);
//                recordFilesListBox.getChildren().add(separator);
//                    
//                });
//            }
//        }
//    }
// private void startReplayGame(String fileName)
// {
//    ArrayList<GameTracker.Move> moves = RecordFile.readFromFile("src/onlineGames/"+fileName);
//    GameReplay gamereplay = new GameReplay();
//    gamereplay.replayGame(moves,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9);
//    RecordBtn.setText("Record");
//    RecordBtn.setDisable(true);
// }

    @Override
    public void onServerResponse(boolean success, ArrayList responseData) {
        
        if((double)responseData.get(0)==(Codes.SEND_PLAY_ON_BOARD_CODE)&&success){
            RecordBtn.setDisable(true);
            enableMouseClick();
            System.out.println("hi from onlineGame response data: " + responseData.toString());
            System.out.println("sympol to be added : " + responseData.get(2).toString());
            String button_sympol = (String)responseData.get(2);
            String button_id = (String)responseData.get(3);
            System.out.println("the Sympol : " + button_sympol);
            System.out.println("the button_id : " + button_id.toString());
            if(isRecording)
            {
                tracker.recordMove(button_id, button_sympol.charAt(0));
            }
            GridPane gridPane = (GridPane) rootPane.lookup("#gridPaneId");
            for (Node node : gridPane.getChildren()) {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    //System.out.println("button id: " + button.getId());
                    if (button.getId().equals(""+button_id)) {
                        Platform.runLater(()->{
                            if(button_sympol.equals("O")){
                                button.setTextFill(Colors.O_TEXT);
                            }else{
                                button.setTextFill(Colors.X_TEXT);
                            }
                            button.setText(button_sympol);
                            writePlayerSymolInArray(button, button_sympol);
                            counter++;
                            checkWinner(button_sympol,"-fx-background-color: #ff6060");
                            if(checkWinner(button_sympol,"-fx-background-color: #ff6060")){
                                try {
                                    if(button_sympol.equals("X")){
                                        playerXScore+=1; 
                                        playerXScoreBtn.setText(""+playerXScore);
                                        NetworkAccessLayer.enemyData.setScore(NetworkAccessLayer.enemyData.getScore()+1);
                                        playerTwoScore.setText("Score: "+String.valueOf(NetworkAccessLayer.enemyData.getScore())); 

                                        //initializeBoardState();
                                        playerTurnBtn.setVisible(false);
                                        newGameBtn.setVisible(true);
                                        String text = "Loser";
                                        showGameOverToast(text);
                                        if(isRecording)
                                        {
                                            tracker.saveToFile("src/onlineGames/","");  ////add record to file ///???????????
                                        }
                                        //disableBoard();
                                        counter=0;

                                        showVideo(Strings.loserVideoPath,"X - Winner");
                                    }
                                    // o is the winner
                                    else{
                                        playerOScore+=1;
                                        playerOScoreBtn.setText(""+playerOScore);
                                        NetworkAccessLayer.enemyData.setScore(NetworkAccessLayer.enemyData.getScore()+1);
                                        playerTwoScore.setText("Score: "+String.valueOf(NetworkAccessLayer.enemyData.getScore()));
                                        //initializeBoardState();
                                        playerTurnBtn.setVisible(false);
                                        newGameBtn.setVisible(true);
                                        String text = "Loser";
                                        showGameOverToast(text);
                                        if(isRecording)
                                        {
                                            tracker.saveToFile("src/onlineGames/","");  ////add record to file ///???????????
                                            isRecording = false; ///
                                        }
                                        //disableBoard();
                                        counter=0;

                                        showVideo(Strings.loserVideoPath,"O - Winner");
                                    }
                                    
                                    //showVideo(Strings.loserVideoPath, "O - loser"); 
                                } catch (IOException ex) {
                                    Logger.getLogger(OnlineGameController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
//                            else if(checkWinner("O","-fx-background-color: #ff6060")){
//                                try {
//                                    playerOScore+=1;
//                                    playerOScoreBtn.setText(""+playerOScore);
//                                    //initializeBoardState();
//                                    playerTurnBtn.setVisible(false);
//                                    newGameBtn.setVisible(true);
//                                    String text = "Loser";
//                                    showGameOverToast(text);
//                                    if(isRecording)
//                                    {
//                                        tracker.saveToFile("src/games/");  ////add record to file
//                                        isRecording = false; ///
//                                    }
//                                    //disableBoard();
//                                    counter=0;
//                                    // check for draw
//                                    showVideo(Strings.winnerVideoPath,"O - Winner");
//                                    //showVideo(Strings.loserVideoPath, "X - loser");
//                                } catch (IOException ex) {
//                                    Logger.getLogger(OnlineGameController.class.getName()).log(Level.SEVERE, null, ex);
//                                }
//                            }
                                else if(counter == 9){
                                try {
                                    //playerXScore+=5;
                                    //playerOScore+=5;
                                    drawScore+=1;
                                    playerXScoreBtn.setText(""+playerXScore);
                                    playerOScoreBtn.setText(""+playerOScore);
                                    drawScoreBtn.setText(""+drawScore);
                                    //initializeBoardState();
                                    playerTurnBtn.setVisible(false);
                                    newGameBtn.setVisible(true);
                                    String text = "It's draw";
                                    showGameOverToast(text);
                                    if(isRecording)
                                    {
                                        tracker.saveToFile("src/games/","");  ////add record to file ///???????????
                                        isRecording = false; ///
                                    }
                                    //disableBoard();
                                    counter=0;
                                    showVideo(Strings.drawVideoPath, "Draw");
                                } catch (IOException ex) {
                                    Logger.getLogger(OnlineGameController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        System.out.println("Sympol texted wrote successfully");
                    }
                }
            }       
            
            
            
        }else if((double)responseData.get(0)==(Codes.PLAY_AGAIN_CODE)&&success){
            Platform.runLater(()->{
                if(counter==0 && mySympol.equals("O")){
                disableMouseClick();
                }else{
                    enableMouseClick();
                }
                enableBoard();
                isGameEnded = false;
                playerTurnBtn.setVisible(true);
                newGameBtn.setVisible(false);
                playerTurnBtn.setText("X-TURN");
                playerTurnBtn.setStyle("-fx-background-color: #83C5BE");
                initializeBoardState();

                RecordBtn.setDisable(false);////record
                tracker.clearMoves();
            });
            
        }
        else if((double)responseData.get(0)==(Codes.UPDATE_PLAYER_SCORE)&&success){
            
            
            
            System.out.println("The score updated successfully");
            NetworkAccessLayer.playerData.setScore(isGameEnded?NetworkAccessLayer.playerData.getScore()+1:NetworkAccessLayer.playerData.getScore()-1);
            playerOneScore.setText("Score: "+String.valueOf(NetworkAccessLayer.playerData.getScore()));
        }
        
        else if((double)responseData.get(0)==(Codes.LEAVE_GAME_CODE)&&success){
            
            System.out.println("LEAVE_GAME_CODE successfully");
            Platform.runLater(()->{
                navigator.goToPage(TicTacToeClient.mainStage, "HomeScreen.fxml");
            });
            
        }
        
    }

    private void updatePlayerScore() {
                
        ArrayList requestArr = new ArrayList();
        requestArr.add(Codes.UPDATE_PLAYER_SCORE);
        System.out.println("hi "+ NetworkAccessLayer.playerData.getScore());  
        int newScore = NetworkAccessLayer.playerData.getScore() + 1;
        requestArr.add(newScore);
        
        String jsonRegisterationRequest = gson.toJson(requestArr);
        NetworkAccessLayer.sendRequest(jsonRegisterationRequest);
    }
    
    public void onClose(){
        
        
            alert= new Alert(Alert.AlertType.CONFIRMATION, "You Sure You Want Leave?", ButtonType.YES,ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                               
                ArrayList arr=new ArrayList();
                arr.add(Codes.LEAVE_GAME_CODE);
                System.out.println(arr);
                NetworkAccessLayer.toServer.println(gson.toJson(arr)); 

                navigator.goToPage(TicTacToeClient.mainStage, "HomeScreen.fxml");

            }
        
    }
     @Override
    public void onServerCloseResponse(boolean serverClosed) {
       if(serverClosed)
       {
           Platform.runLater(()->{
               navigator.popUpStage("ServerDisconnect.fxml");
               try {
                   NetworkAccessLayer.mySocket.close();
               } catch (IOException ex) {
                   Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
               }
           });
       }
    }
}