package onlineplaying;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import tictactoeclient.Navigator;
import utilities.Codes;


public class ConnectionsHandler {
    
    private static final Object lock = new Object();
    Socket serverSocket;
    DataInputStream ear;
    PrintStream mouth;
    PlayerDto playerData = null;
    Gson gson = new Gson();
    Stage stage;
    private static List<Object> responseData = Collections.synchronizedList(new ArrayList<>());
    Navigator navigator;
    
    public ConnectionsHandler(Stage stage){
            this.stage = stage;
            navigator = new Navigator();
            try{
                serverSocket = new Socket("127.0.0.1", 5005);
                ear = new DataInputStream(serverSocket.getInputStream());
                mouth = new PrintStream(serverSocket.getOutputStream());
                                
                Thread th = new Thread(){
                    @Override
                    public void run() {
                        try {
                            while (true) {                                
                                String json = ear.readLine();
                                System.out.println(""+json);
                                responseData = gson.fromJson(json, ArrayList.class);
                                double code = (double) responseData.get(0);
                                
                                if(code == Codes.REGESTER_CODE){
                                    registerationResponse(stage);
                                    
                                }else if(code == Codes.LOGIN_CODE){
                                    LoginResponse(stage); 
                                }
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(ConnectionsHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };  
                th.start();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionsHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void sendRequest(String json){
        mouth.println(json);
        System.out.println("from sendRequest");
    }
    
    public void registerationResponse(Stage stage) {
        new Thread(()->{
            synchronized(lock){
                // wait until the server response and fill the arraylist with data
                while(responseData.isEmpty()){
                    try {
                        lock.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ConnectionsHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            Object result = responseData.get(1);
            PlayerDto registrationResult = null;

            if (result instanceof LinkedTreeMap) {
                String jsonResult = gson.toJson(result); 
                registrationResult = gson.fromJson(jsonResult, PlayerDto.class); 
            }   
            PlayerDto finalRegistrationResult = registrationResult;
            Platform.runLater(()->{
                    if (finalRegistrationResult!=null) {
                            new Alert(Alert.AlertType.CONFIRMATION, "You Successfully Create An Account ;)", ButtonType.OK).showAndWait();
                            navigator.goToPage(stage, "/tictactoeclient/HomeScreen.fxml");
                    }else{
                        
                        new Alert(Alert.AlertType.ERROR, "User Already Registered Try To login", ButtonType.OK).showAndWait();

                    }
                    
                });
            }
            
        }).start();
    }
    
    public void LoginResponse(Stage stage) {
        new Thread(()->{
            synchronized(lock){
                while(responseData.isEmpty()){
                    try {
                        lock.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ConnectionsHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            Object result = responseData.get(1);
            PlayerDto loginResult = null;

            if (result instanceof LinkedTreeMap) {
                String jsonResult = gson.toJson(result); 
                loginResult = gson.fromJson(jsonResult, PlayerDto.class); 
            }   
            PlayerDto finalLoginResult = loginResult;
            Platform.runLater(()->{
                    if (finalLoginResult!=null) {
                            new Alert(Alert.AlertType.CONFIRMATION, "You Successfully Login ;)", ButtonType.OK).showAndWait();
                            navigator.goToPage(stage, "/tictactoeclient/HomeScreen.fxml");
                    }else{
                        
                            new Alert(Alert.AlertType.ERROR, "Player Not Found Please Register", ButtonType.OK).showAndWait();

                    }
                    
                });
            }
            
        }).start();
    }
    
    
}
