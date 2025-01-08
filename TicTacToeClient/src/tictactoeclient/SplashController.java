/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author youse
 */
public class SplashController implements Initializable {

    @FXML
    private ProgressBar progressBar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        
//        // Timeline to gradually fill the progress bar
//        Timeline timeline = new Timeline(
//            new KeyFrame(Duration.seconds(0.1), e -> {
//                progressBar.setProgress(progressBar.getProgress() + 0.01);
//            })
//        );
//
//        timeline.setCycleCount(100); // 100 steps (10 seconds total)

// Duration of the entire animation
    double totalDuration = 5.0; // 5 seconds
    int steps = 100; // Number of steps in the animation
    
    // Timeline to gradually fill the progress bar
    Timeline timeline = new Timeline();
    
    // Add keyframes to fill the progress bar over 5 seconds
    for (int i = 0; i < steps; i++) {
        double progressIncrement = 1.0 / steps; // Progress increment per step
        KeyFrame keyFrame = new KeyFrame(
            Duration.seconds(i * (totalDuration / steps)), 
            e -> progressBar.setProgress(progressBar.getProgress() + progressIncrement)
        );
        timeline.getKeyFrames().add(keyFrame);
    }

        // After loading, switch to main page
        timeline.setOnFinished(e -> {
            try {
                Stage stage = (Stage) progressBar.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                Scene mainScene = new Scene(loader.load());
                stage.setScene(mainScene);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        timeline.play();
    
    }    


    
}
