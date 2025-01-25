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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import onlineplaying.NetworkAccessLayer;

public class EnterNamesScreenController implements Initializable{

    @FXML
    private TextField usernameTextField;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button submitButton;

    @FXML
    private ComboBox<?> genderDropDownList;

    @FXML
    private Label usernameLabel1;

    @FXML
    private ImageView logoImage;

    @FXML
    private Label usernameLabel2;
    
    @FXML
    private Label genderError;

    @FXML
    private Label nameError;
    
    static String gender;
    static String username;
    Navigator navigator;
    
    
    
    

    @FXML
    void onDropDownListChecked(ActionEvent event) {
        
        gender=genderDropDownList.getValue().toString();

    }

    @FXML
    void onSubmitClick(ActionEvent event) {
        
        if(usernameTextField.getText().isEmpty()){
            nameError.setText("Please Enter Your Name"); 
            usernameTextField.setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-border-radius: 15; -fx-background-radius: 15;");
        }
         
      
        if(gender==null){
            
            
            
           genderError.setText("Please Choose Gender ");
        
        
        
        }
        
        if(!usernameTextField.getText().isEmpty() && gender!=null){
            
 
            username=usernameTextField.getText();
            
            LoginScreenController.stageOfNames.close();
            

        
        
        
        
        }
        

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        ArrayList gender=new ArrayList();
        gender.add("Male");
        gender.add("Female");
        genderDropDownList.setItems(FXCollections.observableArrayList(gender));
        
        nameError.setText("");
        genderError.setText(""); 
    }

}
