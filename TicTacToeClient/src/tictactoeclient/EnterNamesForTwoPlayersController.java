/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author esraa.m.mosaad
 */
public class EnterNamesForTwoPlayersController implements Initializable {
 
    
    @FXML
    private TextField playerOneTextField;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button submitButton;

    @FXML
    private ComboBox<?> genderDropDownList2;

    @FXML
    private Label usernameLabel1;

    @FXML
    private ImageView logoImage;

    @FXML
    private Label genderError2;

    @FXML
    private Label nameError2;

    @FXML
    private Label usernameLabel2;

    @FXML
    private TextField playerTwoTextField;

    @FXML
    private Label nameError1;

    @FXML
    private ComboBox<?> genderDropDownList1;

    @FXML
    private Label usernameLabel11;

    @FXML
    private Label genderError1;
    
    static String genderOne;
    static String genderTwo;
    static String nameOne;
    static String nameTwo;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ArrayList gender1=new ArrayList();
        gender1.add("Male");
        gender1.add("Female");
        genderDropDownList1.setItems(FXCollections.observableArrayList(gender1));
        ArrayList gender2=new ArrayList();
        gender2.add("Male");
        gender2.add("Female");
        genderDropDownList2.setItems(FXCollections.observableArrayList(gender2));
        
        nameError1.setText("");
        nameError2.setText("");
        genderError1.setText(""); 
        genderError2.setText("");
        
        
        
    } 

    @FXML
    void onDropDownListChecked(ActionEvent event) {
        
        genderOne=genderDropDownList1.getValue().toString();

    }
    
   @FXML
    void onDropDownListChecked2(ActionEvent event) {
        
        genderTwo=genderDropDownList2.getValue().toString();

    }

    @FXML
    void onSubmitClick(ActionEvent event) {
        
        if(playerOneTextField.getText().isEmpty()){
            nameError1.setText("Please Enter Your Name"); 
            playerOneTextField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15;");
        }
         
      
        if(genderOne==null){
            
            
           genderError1.setText("Please Choose Gender ");
        
        
        
        }
        
        if(genderTwo==null){
            
            
           genderError2.setText("Please Choose Gender ");
        
        
        
        }
        
        if(playerTwoTextField.getText().isEmpty()){
            nameError2.setText("Please Enter Your Name"); 
            playerTwoTextField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15;");
        }
        
        
        
        if(!playerOneTextField.getText().isEmpty() && genderOne!=null && !playerTwoTextField.getText().isEmpty() && genderTwo!=null){
            
 
            nameOne=playerOneTextField.getText();
            nameTwo=playerTwoTextField.getText();
            GameScreenController.stageOfNames.close();

        }
        
        

    }
    
}
