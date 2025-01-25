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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import onlineplaying.NetworkAccessLayer;
import static tictactoeclient.LoginScreenController.stageOfNames;
import utilities.Strings;

/**
 * FXML Controller class
 *
 * @author STW
 */
public class VsComputerSceneController implements Initializable {

    @FXML
    private StackPane rootPane;
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
    private Button playerTurnBtn;
    @FXML
    private Polygon leftPolygon;
    @FXML
    private Polygon rightPolygon;
    @FXML
    private Rectangle gameOverRect;
    @FXML
    private Label gameOverToast;
    @FXML
    private Button humanScoreButton;
    @FXML
    private Button drawScoreButton;
    @FXML
    private Button computerScoreButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button newGameButton;
    @FXML
    private Button easyButton;
    @FXML
    private Button hardButton;
    @FXML
    private Button mediumButton;

    @FXML
    ImageView userImage;

    Alert alert;

    @FXML
    Text username;

    Navigator navigator;
    static int[][] board = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    private Button[] myButtons;
    int move[];
    private char Difficulty;
    private boolean playerTurn = true;
    private boolean wins = false;
    private boolean stalemate = true;
    private boolean firstMove = true;
    int computerWinCounter;
    int drawCounter;
    int humanWinCounter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        userImage.setImage(null);
        username.setText("Human");
        navigator = new Navigator();
        myButtons = new Button[]{btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
        computerWinCounter = 0;
        drawCounter = 0;
        humanWinCounter = 0;
        hideGameStatus();

        Platform.runLater(() -> {

            try {

                Parent root = FXMLLoader.load(getClass().getResource("EnterNamesScreen.fxml"));
                stageOfNames = new Stage();
                Scene scene = new Scene(root);
                stageOfNames.setScene(scene);
                stageOfNames.initModality(Modality.APPLICATION_MODAL);
                stageOfNames.showAndWait();
                if (EnterNamesScreenController.gender != null && EnterNamesScreenController.username != null) {
                    userImage.setImage(EnterNamesScreenController.gender.equals("Male") ? new Image("file:src/Images/boy.png") : EnterNamesScreenController.gender.isEmpty() ? new Image("file:src/Images/x.png") : new Image("file:src/Images/girl.png"));
                    username.setText(EnterNamesScreenController.username);
                }

            } catch (IOException ex) {
                Logger.getLogger(VsComputerSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

    @FXML
    private void onPlayerClick(ActionEvent event) {
        for (int i = 0; i < 9; i++) {
            switch (Difficulty) {
                case 'M':
                    mediumGameLogic(i, event);
                    break;
                case 'E':
                    easyGameLogic(i, event);
                    break;
                case 'H':
                    hardGameLogic(i, event);
                    break;
                default:
                    break;
            }
        }
    }

    @FXML
    void onExitButton(ActionEvent event) {

        alert = new Alert(Alert.AlertType.CONFIRMATION, "You Sure You Want Leave?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();
        if (NetworkAccessLayer.playerData != null) {

            navigator.goToPage(event, "HomeScreen.fxml");

        } else {

            navigator.goToPage(event, "LoginScreen.fxml");

        }

    }

    @FXML
    private void onNewGameButton(ActionEvent event) {
        wins = false;
        stalemate = true;
        firstMove = true;
        Difficulty = '\0';
        hideGameStatus();
        boardInit();
        difficultyButtonEnable();
        buttonsDisable();
        clear();
    }

    @FXML
    private void onEasyButton(ActionEvent event) {
        wins = false;
        stalemate = true;
        firstMove = true;
        buttonsEnable();
        hardButton.setDisable(true);
        mediumButton.setDisable(true);
        clear();
        boardInit();
        Difficulty = 'E';
    }

    @FXML
    private void onMediumButton(ActionEvent event) {
        wins = false;
        stalemate = true;
        firstMove = true;
        boardInit();
        buttonsEnable();
        hardButton.setDisable(true);
        easyButton.setDisable(true);
        clear();
        Difficulty = 'M';
    }

    @FXML
    private void onHardButton(ActionEvent event) {
        wins = false;
        stalemate = true;
        firstMove = true;
        buttonsEnable();
        mediumButton.setDisable(true);
        easyButton.setDisable(true);
        clear();
        boardInit();
        Difficulty = 'H';
    }

    public void buttonsDisable() {
        for (int i = 0; i < 9; i++) {
            myButtons[i].setDisable(true);
        }
    }

    public void buttonsEnable() {
        for (int i = 0; i < 9; i++) {
            myButtons[i].setDisable(false);
        }
    }

    private void clear() {
        for (int i = 0; i < 9; i++) {
            myButtons[i].setText("");
            myButtons[i].setStyle("-fx-background-color: #16697A");
        }
    }

    private int[] getRowCol(int index) {
        int[] arr = new int[2];
        if (index < 3) {
            arr[0] = 0;
            arr[1] = index % 3;
        } else if (index < 6) {
            arr[0] = 1;
            arr[1] = index % 3;
        } else if (index < 9) {
            arr[0] = 2;
            arr[1] = index % 3;
        }
        return arr;
    }

    private int getIndex(int row, int col) {
        if ((row == col) && (row == 0)) {
            return 0;
        } else {
            return (3 * row) + col;
        }

    }

    private void boardInit() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
        playerTurn = true;
    }

    private void difficultyButtonsDisable() {
        easyButton.setDisable(true);
        hardButton.setDisable(true);
        mediumButton.setDisable(true);
    }

    private void difficultyButtonEnable() {
        easyButton.setDisable(false);
        hardButton.setDisable(false);
        mediumButton.setDisable(false);
    }

    private boolean checkForWinner() //Change playerWins status
    {
        checkRows();
        checkColomns();
        checkDiagonals();
        return wins;
    }

    private void checkRows() //Change playerWins status
    {
        for (int i = 0; i < 9; i += 3) {
            if ((myButtons[i].getText().equals(myButtons[i + 1].getText()))
                    && (myButtons[i + 1].getText().equals(myButtons[i + 2].getText())) && (!myButtons[i].getText().isEmpty())) {
                wins(i, i + 1, i + 2);
                wins = true;
            }
        }
    }

    private void checkColomns() //Change playerWins status
    {
        for (int i = 0; i < 3; i++) {
            if ((myButtons[i].getText().equals(myButtons[i + 3].getText()))
                    && (myButtons[i + 3].getText().equals(myButtons[i + 6].getText())) && (!myButtons[i].getText().isEmpty())) {
                wins(i, i + 3, i + 6);
                wins = true;
            }

        }
    }

    private void checkDiagonals() //Change playerWins status
    {
        for (int i = 0; i <= 2; i += 2) {
            if ((myButtons[i].getText().equals(myButtons[4].getText()))
                    && (myButtons[4].getText().equals(myButtons[8 - i].getText())) && (!myButtons[i].getText().isEmpty())) {
                wins(i, 4, 8 - i);
                wins = true;
            }
        }
    }

    private boolean checkDraw() {
        int i = 0;
        for (i = 0; i < 9; i++) {
            if (myButtons[i].getText().isEmpty()) {
                stalemate = false;
                break;
            }
        }
        if (i == 9 && !wins) {
            stalemate = true;
            for (i = 0; i < 9; i++) {
                myButtons[i].setDisable(true);
            }
        }
        return stalemate;
    }

    private void wins(int a, int b, int c) {
        myButtons[a].setStyle("-fx-background-color: green;");
        myButtons[b].setStyle("-fx-background-color: green;");
        myButtons[c].setStyle("-fx-background-color: green;");
        buttonsDisable();
        difficultyButtonsDisable();
    }

    private void mediumGameLogic(int i, ActionEvent event) {
        if (event.getSource() == myButtons[i]) {
            if (myButtons[i].getText().isEmpty()) {
                myButtons[i].setText("X");
                if (checkForWinner()) {
                    try {
                        showVideo(Strings.winnerVideoPath, "You are Winner");
                    } catch (IOException ex) {
                        Logger.getLogger(VsComputerSceneController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    showGameStatus();
                    gameOverToast.setText(username.getText() + " Wins !!!");
                    humanWinCounter += 5;
                    humanScoreButton.setText(Integer.toString(humanWinCounter));
                } else if (checkDraw()) {
                    try {
                        showVideo(Strings.drawVideoPath, "Draw !!");
                    } catch (IOException ex) {
                        Logger.getLogger(VsComputerSceneController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    showGameStatus();
                    gameOverToast.setText("Stalemate !!!");
                    drawCounter++;
                    drawScoreButton.setText(Integer.toString(drawCounter));
                } else {
                    computerMediumMove(i, board);
                    firstMove = false;
                    if (checkForWinner()) {
                        try {
                            showVideo(Strings.loserVideoPath, "You are Loser");
                        } catch (IOException ex) {
                            Logger.getLogger(VsComputerSceneController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        showGameStatus();
                        gameOverToast.setText("Computer Wins !!!");
                        computerWinCounter += 5;
                        computerScoreButton.setText(Integer.toString(computerWinCounter));

                    } else if (checkDraw()) {
                        try {
                            showVideo(Strings.drawVideoPath, "Draw !!");
                        } catch (IOException ex) {
                            Logger.getLogger(VsComputerSceneController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        showGameStatus();
                        gameOverToast.setText("Stalemate !!!");
                        drawCounter++;
                        drawScoreButton.setText(Integer.toString(drawCounter));
                    } else {
                        board[move[0]][move[1]] = -1;
                    }
                }
            }
        }
    }

    private void easyGameLogic(int i, ActionEvent event) {
        if (event.getSource() == myButtons[i]) {
            if (myButtons[i].getText().isEmpty()) {
                myButtons[i].setText("X");
                if (checkForWinner()) {
                    try {
                        showVideo(Strings.winnerVideoPath, "You are Winner");
                    } catch (IOException ex) {
                        Logger.getLogger(VsComputerSceneController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    showGameStatus();
                    gameOverToast.setText(username.getText() + " Wins !!!");
                    humanWinCounter += 5;
                    humanScoreButton.setText(Integer.toString(humanWinCounter));
                } else if (checkDraw()) {
                    try {
                        showVideo(Strings.drawVideoPath, "Draw !!");
                    } catch (IOException ex) {
                        Logger.getLogger(VsComputerSceneController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    showGameStatus();
                    gameOverToast.setText("Stalemate !!!");
                    drawCounter++;
                    drawScoreButton.setText(Integer.toString(drawCounter));
                } else {
                    computerEasyMove(i, board);
                    if (checkForWinner()) {
                        try {
                            showVideo(Strings.loserVideoPath, "You are Loser");
                        } catch (IOException ex) {
                            Logger.getLogger(VsComputerSceneController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        showGameStatus();
                        gameOverToast.setText("Computer Wins !!!");
                        computerWinCounter += 5;
                        computerScoreButton.setText(Integer.toString(computerWinCounter));
                    } else if (checkDraw()) {
                        try {
                            showVideo(Strings.drawVideoPath, "Draw !!");
                        } catch (IOException ex) {
                            Logger.getLogger(VsComputerSceneController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        showGameStatus();
                        gameOverToast.setText("Stalemate !!");
                        drawCounter++;
                        drawScoreButton.setText(Integer.toString(drawCounter));
                    } else {
                        board[move[0]][move[1]] = -1;
                    }
                }
            }
        }
    }

    private void computerEasyMove(int i, int[][] board) {
        int[] arr = getRowCol(i);
        board[arr[0]][arr[1]] = 1;//Update the board with x which was played by Human
        move = PlayAgainstComputer.getRandomMove();
        int index = getIndex(move[0], move[1]);
        myButtons[index].setText("O");
    }

    private void computerMediumMove(int i, int[][] board) {
        int[] arr = getRowCol(i);
        board[arr[0]][arr[1]] = 1;//Update the board with x which was played by Human
        move = PlayAgainstComputer.findBestMove(board);
        int index = getIndex(move[0], move[1]);
        myButtons[index].setText("O");
    }

    void showVideo(String vidoeUrl, String symbol) throws IOException {

        VideoPlayerController.videoUrl = vidoeUrl;
        Parent root = FXMLLoader.load(getClass().getResource("VideoPlayer.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(symbol);
        stage.show();

        stage.setOnCloseRequest((event) -> {

            VideoPlayerController.mediaPlayer.pause();
            TicTacToeClient.mediaPlayer.play();

        });

        VideoPlayerController.mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {

                stage.close();

            }
        });

    }

    private void computerSmartMove(int i, int[][] board) {
        int[] arr = getRowCol(i);
        board[arr[0]][arr[1]] = 1;//Update the board with x which was played by Human
        move = PlayAgainstComputer.enhancedBestMove(board, firstMove);
        int index = getIndex(move[0], move[1]);
        myButtons[index].setText("O");
    }

    private String[][] convertBoardToStringArray(int[][] board) {
        String[][] stringBoard = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == -1) {
                    stringBoard[i][j] = "X";
                } else if (board[i][j] == 1) {
                    stringBoard[i][j] = "O";
                } else {
                    stringBoard[i][j] = "";
                }
            }
        }
        return stringBoard;
    }

    private void hardGameLogic(int i, ActionEvent event) {
        if (event.getSource() == myButtons[i]) {
            if (myButtons[i].getText().isEmpty()) {
                myButtons[i].setText("X");
                board[getRowCol(i)[0]][getRowCol(i)[1]] = -1; // Update the board for human move
                if (checkForWinner()) {
                    try {
                        showVideo(Strings.winnerVideoPath, "You are Winner");
                    } catch (IOException ex) {
                        Logger.getLogger(VsComputerSceneController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    showGameStatus();
                    gameOverToast.setText(username.getText() + " Wins !!!");
                    humanWinCounter += 5;
                    humanScoreButton.setText(Integer.toString(humanWinCounter));
                } else if (checkDraw()) {
                    try {
                        showVideo(Strings.drawVideoPath, "Draw !!");
                    } catch (IOException ex) {
                        Logger.getLogger(VsComputerSceneController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    showGameStatus();
                    gameOverToast.setText("Stalemate !!!");
                    drawCounter++;
                    drawScoreButton.setText(Integer.toString(drawCounter));
                } else {
                    // Use HardLogic to determine the best move
                    HardLogic hardLogic = new HardLogic(convertBoardToStringArray(board));
                    int[] bestMove = hardLogic.findBestMove(9); // Adjust depth limit as needed
                    int index = getIndex(bestMove[0], bestMove[1]);
                    myButtons[index].setText("O");
                    board[bestMove[0]][bestMove[1]] = 1; // Update the board for computer move

                    if (checkForWinner()) {
                        try {
                            showVideo(Strings.loserVideoPath, "You are Loser");
                        } catch (IOException ex) {
                            Logger.getLogger(VsComputerSceneController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        showGameStatus();
                        gameOverToast.setText("Computer Wins !!!");
                        computerWinCounter += 5;
                        computerScoreButton.setText(Integer.toString(computerWinCounter));
                    } else if (checkDraw()) {
                        try {
                            showVideo(Strings.drawVideoPath, "Draw !!");
                        } catch (IOException ex) {
                            Logger.getLogger(VsComputerSceneController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        showGameStatus();
                        gameOverToast.setText("Stalemate !!!");
                        drawCounter++;
                        drawScoreButton.setText(Integer.toString(drawCounter));
                    }
                }
            }
        }
    }

    private void hideGameStatus() {
        leftPolygon.setVisible(false);
        rightPolygon.setVisible(false);
        gameOverRect.setVisible(false);
        gameOverToast.setVisible(false);
    }

    private void showGameStatus() {
        leftPolygon.setVisible(true);
        rightPolygon.setVisible(true);
        gameOverRect.setVisible(true);
        gameOverToast.setVisible(true);
    }
}
