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

        gsonFile = new Gson();
    }

    public void goToPage(ActionEvent event, String targetPage) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(targetPage));
            Scene page1Scene = new Scene(loader.load());
            // Get current stage and set new scene (Page 1) 
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(page1Scene);
            stage.show();
            onPlayerLogout(stage);
            setPositionOfTheStage(stage);

        } catch (IOException ex) {
            Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goToPage(Stage stage, String targetPage) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(targetPage));
            Scene page1Scene = new Scene(loader.load());
            // Get current stage and set new scene (Page 1) 
            stage.setScene(page1Scene);
            stage.show();

            onPlayerLogout(stage);
            setPositionOfTheStage(stage);

        } catch (IOException ex) {
            Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void luanchOnlineGame(Stage stage, String targetPage, PlayerDto enemy, String Sympol) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(targetPage));
            Scene page1Scene = new Scene(loader.load());

            OnlineGameController controller = loader.getController();
            controller.setEnemyUsername(enemy.getUserName());
            controller.setMySympol(Sympol);

            // Get current stage and set new scene (Page 1) 
            stage.setScene(page1Scene);
            stage.show();
            stage.setOnCloseRequest((e) -> {
                controller.onClose();
                if (NetworkAccessLayer.mySocket != null) {

                    ArrayList arr = new ArrayList();
                    arr.add(Codes.LOGOUT_CODE);

                    System.out.println(arr);

                    NetworkAccessLayer.toServer.println(gsonFile.toJson(arr));

                    Platform.runLater(() -> {

                        try {
                            NetworkAccessLayer.thread.stop();
                            NetworkAccessLayer.mySocket.close();
                            NetworkAccessLayer.playerData = null;
                        } catch (IOException ex) {
                            Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });

                }
                Platform.exit();

            });

            setPositionOfTheStage(stage);

        } catch (IOException ex) {
            Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goToPage(ActionEvent event, String targetPage, PlayerDto player) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(targetPage));
            Scene page1Scene = new Scene(loader.load());
            EditProfileController editController = loader.getController();
            //editController.setData(player);
            // Get current stage and set new scene (Page 1) 
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(page1Scene);
            stage.show();

            onPlayerLogout(stage);
            setPositionOfTheStage(stage);

        } catch (IOException ex) {
            Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void luanchInvitation(String targetPage, String pageTitle, PlayerDto sender) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(targetPage));
            Parent root = loader.load();

            FXMLInvitationAlertController controller = loader.getController();
            controller.setSenderLabel(sender);

            Stage popupStage = new Stage();
            popupStage.setTitle(pageTitle);
            popupStage.setScene(new Scene(root));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void luanchWaiting(String targetPage, String pageTitle, String reciever) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(targetPage));
            Parent root = loader.load();

            FXMLWaitingAlertController controller = loader.getController();
            controller.setRecieverLabel(reciever);

            Stage popupStage = new Stage();
            popupStage.setTitle(pageTitle);
            popupStage.setScene(new Scene(root));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void popUpStage(String sceneName) {
        try {
            Stage newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            Parent root = FXMLLoader.load(getClass().getResource(sceneName));
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(RegisterScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPositionOfTheStage(Stage stage) {

        stage.xProperty().addListener((obs, oldVal, newVal) -> TicTacToeClient.primaryX = newVal.doubleValue());
        stage.yProperty().addListener((obs, oldVal, newVal) -> TicTacToeClient.primaryY = newVal.doubleValue());
        stage.widthProperty().addListener((obs, oldVal, newVal) -> TicTacToeClient.primaryWidth = newVal.doubleValue());
        stage.heightProperty().addListener((obs, oldVal, newVal) -> TicTacToeClient.primaryHeight = newVal.doubleValue());

    }

    public void onPlayerLogout(Stage stage) {

        stage.setOnCloseRequest((e) -> {

            if (NetworkAccessLayer.mySocket != null) {

                ArrayList arr = new ArrayList();
                arr.add(Codes.LOGOUT_CODE);

                System.out.println(arr);

                NetworkAccessLayer.toServer.println(gsonFile.toJson(arr));

                Platform.runLater(() -> {

                    try {
                        NetworkAccessLayer.thread.stop();
                        NetworkAccessLayer.mySocket.close();
                        NetworkAccessLayer.playerData = null;
                    } catch (IOException ex) {
                        Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });

            }
            Platform.exit();

        });

    }
}
