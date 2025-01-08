/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author TBARAK
 */
public class EditProfileController implements Initializable {

    @FXML
    private Circle avatar;
    @FXML
    private ImageView userImage;
    @FXML
    private Circle avatar1;
    @FXML
    private Text username;
    @FXML
    private Text score;
    @FXML
    private TextField userNameField;
    @FXML
    private Button submitUpdateButton;
    @FXML
    private Button SeeYourRecordsButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField passWordField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onSubmitUpdateClicked(ActionEvent event) {
    }

    @FXML
    private void onSeeYourRecordsButtonClicked(ActionEvent event) {
    }

    @FXML
    private void onCancelButtonClicked(ActionEvent event) {
    }


    
}
