/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.io.File;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import onlineplaying.NetworkAccessLayer;
import utilities.Strings;

/**
 * FXML Controller class
 *
 * @author Ziad-Elshemy
 */
public class GameScreenController implements Initializable {

    int counter;
    int playerXScore;
    int playerOScore;
    int drawScore;
    boolean isGameEnded = false;
    Navigator navigator;
    ArrayList<String> boardState;

    GameTracker tracker; /// record
    boolean isRecording;   //// record
    GameReplay gamereplay;

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
    @FXML
    private Button allRecordsBtn;
    @FXML
    private VBox recordFilesListBox;
    @FXML
    private Label file1Lable;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView playerTwoImage;

    @FXML
    private Text playerTwoUsername;

    @FXML
    private ImageView playerOneImage;

    @FXML
    private Text playerOneUsername;

    static Stage stageOfNames;
    @FXML
    private AnchorPane onExitButton;
    @FXML
    private Circle avatar;
    @FXML
    private Text username1;
    @FXML
    private Circle avatar1;

    Alert alert;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        playerOneImage.setImage(null);
        playerOneUsername.setText("X Player");
        playerTwoImage.setImage(null);
        playerTwoUsername.setText("O Player");
        // TODO
        //exitBtn.setStyle("-fx-background-color: linear-gradient(from 100% 0% to 0% 0%, #CC8282, #EDF6F9);");
        tracker = new GameTracker();  // record

        navigator = new Navigator();
        playerXScore = 0;
        playerOScore = 0;
        drawScore = 0;
        initializeBoardState();
        disableBoard();
        counter = 0;
        isRecording = false; //record
        gamereplay = new GameReplay();

        Platform.runLater(() -> {

            try {

                Parent root = FXMLLoader.load(getClass().getResource("EnterNamesForTwoPlayers.fxml"));      
                stageOfNames = new Stage();
                Scene scene = new Scene(root);
                stageOfNames.setScene(scene);
                stageOfNames.initModality(Modality.WINDOW_MODAL);
                stageOfNames.showAndWait();
                if (EnterNamesForTwoPlayersController.genderOne != null && EnterNamesForTwoPlayersController.nameOne != null) {
                    playerOneImage.setImage(EnterNamesForTwoPlayersController.genderOne.equals("Male") ? new Image(getClass().getResource("/Images/boy.png").toString()) : EnterNamesForTwoPlayersController.genderOne.isEmpty() ? new Image(getClass().getResource("/Images/x.png").toString()) : new Image(getClass().getResource("/Images/girl.png").toString()));
                    playerOneUsername.setText(EnterNamesForTwoPlayersController.nameOne);

                }

                if (EnterNamesForTwoPlayersController.genderTwo != null && EnterNamesForTwoPlayersController.nameTwo != null) {
                    playerTwoImage.setImage(EnterNamesForTwoPlayersController.genderTwo.equals("Male") ? new Image(getClass().getResource("/Images/boy.png").toString()) : EnterNamesForTwoPlayersController.genderOne.isEmpty() ? new Image(getClass().getResource("/Images/o.png").toString()) : new Image(getClass().getResource("/Images/girl.png").toString()));
                    playerTwoUsername.setText(EnterNamesForTwoPlayersController.nameTwo);

                }

            } catch (IOException ex) {
                Logger.getLogger(VsComputerSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

    @FXML
    private void exitBtnAction(ActionEvent event) {

        alert = new Alert(Alert.AlertType.CONFIRMATION, "You Sure You Want Leave?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (NetworkAccessLayer.playerData != null) {

            navigator.goToPage(event, "HomeScreen.fxml");

        } else {

            navigator.goToPage(event, "LoginScreen.fxml");

        }
    }

    @FXML
    ////it must clear draw after Record Shown
    private void newGameBtnAction(ActionEvent event) {
        enableBoard();
        isGameEnded = false;
        playerTurnBtn.setVisible(true);
        newGameBtn.setVisible(false);
        playerTurnBtn.setText("X-TURN");
        playerTurnBtn.setStyle("-fx-background-color: #83C5BE");
        initializeBoardState();
        RecordBtn.setDisable(false);////record
        if(!isRecording){
            RecordBtn.setText("Record");
        }
        tracker.clearMoves();
        recordFilesListBox.setDisable(true);
        allRecordsBtn.setDisable(true);
        gamereplay.stopThread();
    }

    @FXML
    private void onPlayerClick(ActionEvent event) throws IOException {

        Button button = (Button) event.getSource();
        String playerSympol = "";
        if (!button.getText().toString().isEmpty() || isGameEnded) {
            return;
        }
        if (counter % 2 == 0) {
            playerSympol = "X";
            button.setText(playerSympol);
            button.setTextFill(Colors.X_TEXT);
            playerTurnBtn.setText(!playerTwoUsername.equals("O Player") ? playerTwoUsername.getText() + " Turn" : "O-TURN");
            playerTurnBtn.setStyle("-fx-background-color: #FFA62B");
        } else {
            playerSympol = "O";
            button.setText(playerSympol);
            button.setTextFill(Colors.O_TEXT);
            playerTurnBtn.setText(!playerOneUsername.equals("X Player") ? playerOneUsername.getText() + " Turn" : "X-TURN");
            playerTurnBtn.setStyle("-fx-background-color: #83C5BE");
        }
        counter++;

        if (counter > 0) // check if the game is at the beginning
        {
            RecordBtn.setDisable(true);
        }

        if (isRecording) {
            tracker.recordMove(button.getId(), playerSympol.charAt(0));
        }

        writePlayerSymolInArray(button, playerSympol);

        if (checkWinner("X")) {
            playerXScore += 1;
            playerXScoreBtn.setText("" + playerXScore);
            //initializeBoardState();
            playerTurnBtn.setVisible(false);
            newGameBtn.setVisible(true);
            String text = !playerOneUsername.equals("X Player") ? playerOneUsername.getText() + " Win" : "Player X win";
            showGameOverToast(text);
            if(isRecording)
            {
                 tracker.saveToFile("src/games/","local");  ////add record to file
                 isRecording = false; ///
            }
            allRecordsBtn.setDisable(false);
            //disableBoard();
            counter = 0;

            showVideo(Strings.winnerVideoPath, !playerOneUsername.equals("X Player") ? playerOneUsername.getText() + " Win" : "Player X win");
            //showVideo(Strings.loserVideoPath, "O - loser"); 
        } else if (checkWinner("O")) {
            playerOScore += 1;
            playerOScoreBtn.setText("" + playerOScore);
            //initializeBoardState();
            playerTurnBtn.setVisible(false);
            newGameBtn.setVisible(true);
            String text = !playerTwoUsername.equals("O Player") ? playerTwoUsername.getText() + " Win" : "Player O win";
            showGameOverToast(text);
            if (isRecording) {
                tracker.saveToFile("src/games/", "local");  ////add record to file
                isRecording = false; ///
            }
            allRecordsBtn.setDisable(false);
            //disableBoard();
            counter = 0;
            // check for draw
            showVideo(Strings.winnerVideoPath, !playerTwoUsername.equals("O Player") ? playerTwoUsername.getText() + " Win" : "Player O win");
            //showVideo(Strings.loserVideoPath, "X - loser");
        } else if (counter == 9) {
            //playerXScore+=5;
            //playerOScore+=5;
            drawScore += 1;
            playerXScoreBtn.setText("" + playerXScore);
            playerOScoreBtn.setText("" + playerOScore);
            drawScoreBtn.setText("" + drawScore);
            //initializeBoardState();
            playerTurnBtn.setVisible(false);
            newGameBtn.setVisible(true);
            String text = "It's draw";
            showGameOverToast(text);
            if(isRecording)
            {
                 tracker.saveToFile("src/games/","local");  ////add record to file
                 isRecording = false; ///
            }
            allRecordsBtn.setDisable(false);
            //disableBoard();
            counter = 0;
            showVideo(Strings.drawVideoPath, "Draw");
        }

    }

    void showVideo(String vidoeUrl, String symbol) throws IOException {

        VideoPlayerController.videoUrl = vidoeUrl;
        Parent root = FXMLLoader.load(getClass().getResource("VideoPlayer.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(symbol);
        stage.show();

        stage.setOnCloseRequest((event) -> {

            VideoPlayerController.mediaPlayer.pause();
            if (!TicTacToeClient.isMuted) {
                TicTacToeClient.mediaPlayer.play();

            }

        });

        VideoPlayerController.mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {

                stage.close();
                if (!TicTacToeClient.isMuted) {
                    TicTacToeClient.mediaPlayer.play();

                }

            }
        });

    }

    void writePlayerSymolInArray(Button button, String playerSympol) {
        if (button.getId().equals(btn1.getId())) {
            boardState.set(0, playerSympol);
        } else if (button.getId().equals(btn2.getId())) {
            boardState.set(1, playerSympol);
        } else if (button.getId().equals(btn3.getId())) {
            boardState.set(2, playerSympol);
        } else if (button.getId().equals(btn4.getId())) {
            boardState.set(3, playerSympol);
        } else if (button.getId().equals(btn5.getId())) {
            boardState.set(4, playerSympol);
        } else if (button.getId().equals(btn6.getId())) {
            boardState.set(5, playerSympol);
        } else if (button.getId().equals(btn7.getId())) {
            boardState.set(6, playerSympol);
        } else if (button.getId().equals(btn8.getId())) {
            boardState.set(7, playerSympol);
        } else if (button.getId().equals(btn9.getId())) {
            boardState.set(8, playerSympol);
        }
    }

    private void disableBoard() {
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

    private void enableBoard() {
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

    private void stopEditBoard() {
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

    private void reInitializeBoard() {
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

    private void initializeBoardState() {

        boardState = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            boardState.add("");
        }
        hideGameOverToast();
        reInitializeBoard();

    }

    private void showGameOverToast(String text) {
        leftPolygon.setVisible(true);
        rightPolygon.setVisible(true);
        gameOverRect.setVisible(true);
        gameOverToast.setVisible(true);
        gameOverToast.setText("Game Over. " + text);
    }

    private void hideGameOverToast() {
        leftPolygon.setVisible(false);
        rightPolygon.setVisible(false);
        gameOverRect.setVisible(false);
        gameOverToast.setVisible(false);
    }

    //["X","x","X",
    // "o","X","o",
    // "X","o","X"]
    private boolean checkWinner(String playerSympol) {
        for (int i = 0; i < 9; i += 3) {
            // check for rows winner
            if (boardState.get(i).toString().equals(playerSympol)
                    && boardState.get(i + 1).toString().equals(playerSympol)
                    && boardState.get(i + 2).toString().equals(playerSympol)) {
                System.out.println("winner by rows");
                if (i == 0) {
                    btn1.setStyle("-fx-background-color: #008000");
                    btn2.setStyle("-fx-background-color: #008000");
                    btn3.setStyle("-fx-background-color: #008000");
                }
                if (i == 3) {
                    btn4.setStyle("-fx-background-color: #008000");
                    btn5.setStyle("-fx-background-color: #008000");
                    btn6.setStyle("-fx-background-color: #008000");
                }
                if (i == 6) {
                    btn7.setStyle("-fx-background-color: #008000");
                    btn8.setStyle("-fx-background-color: #008000");
                    btn9.setStyle("-fx-background-color: #008000");
                }
                isGameEnded = true;
                return true;

            }
        }
        for (int i = 0; i < 3; i++) {
            // check for columns winner
            if (boardState.get(i).toString().equals(playerSympol)
                    && boardState.get(i + 3).toString().equals(playerSympol)
                    && boardState.get(i + 6).toString().equals(playerSympol)) {

                System.out.println("winner by columns");
                if (i == 0) {
                    btn1.setStyle("-fx-background-color: #008000");
                    btn4.setStyle("-fx-background-color: #008000");
                    btn7.setStyle("-fx-background-color: #008000");
                }
                if (i == 1) {
                    btn2.setStyle("-fx-background-color: #008000");
                    btn5.setStyle("-fx-background-color: #008000");
                    btn8.setStyle("-fx-background-color: #008000");
                }
                if (i == 2) {
                    btn3.setStyle("-fx-background-color: #008000");
                    btn6.setStyle("-fx-background-color: #008000");
                    btn9.setStyle("-fx-background-color: #008000");
                }
                isGameEnded = true;
                return true;
            }
        }
        // check for diagonals winner
        if (boardState.get(0).toString().equals(playerSympol)
                && boardState.get(4).toString().equals(playerSympol)
                && boardState.get(8).toString().equals(playerSympol)) {
            System.out.println("winner by diagonals");
            btn1.setStyle("-fx-background-color: #008000");
            btn5.setStyle("-fx-background-color: #008000");
            btn9.setStyle("-fx-background-color: #008000");
            isGameEnded = true;
            return true;
        }
        // check for diagonals winner
        if (boardState.get(2).toString().equals(playerSympol)
                && boardState.get(4).toString().equals(playerSympol)
                && boardState.get(6).toString().equals(playerSympol)) {
            System.out.println("winner by diagonals");
            btn3.setStyle("-fx-background-color: #008000");
            btn5.setStyle("-fx-background-color: #008000");
            btn7.setStyle("-fx-background-color: #008000");
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

    @FXML
    private void onallRecordsBtnAction(ActionEvent event) {
        recordFilesListBox.getChildren().clear();
        recordFilesListBox.setDisable(false);
        ShowFiles();

    }

    private void ShowFiles() {
        File directory = new File("src/games");
        File[] files = directory.listFiles();

        //files = RecordsList.getRecordsFiles();
        if (files != null) {

            //file1Lable.setText(files[0].getName());
            for (File file : files) {
                //int count
                //System.out.println("File "+count+ " : " +file.getName());
                Separator separator = new Separator();
                Label lable = new Label(file.getName());
                lable.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-padding: 5px; -fx-font-weight: bold;");
                lable.setOnMouseEntered((e) -> {
                    lable.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-padding: 5px; -fx-font-weight: bold;");

                });
                lable.setOnMouseExited((e) -> {
                    lable.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-padding: 5px; -fx-font-weight: bold;");

                });
                lable.setOnMouseClicked((e) -> {
                    initializeBoardState();
                    disableBoard();
                    RecordBtn.setDisable(false);
                    startReplayGame(file.getName());
                });
                Platform.runLater(() -> {
                    recordFilesListBox.getChildren().add(lable);
                    recordFilesListBox.getChildren().add(separator);

                });
            }
        }
    }

    private void startReplayGame(String fileName) {

        ArrayList<GameTracker.Move> moves = RecordFile.readFromFile("src/games/" + fileName);

        gamereplay.replayGame(moves, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9);
        RecordBtn.setText("Record");
        RecordBtn.setDisable(true);

    }

}
