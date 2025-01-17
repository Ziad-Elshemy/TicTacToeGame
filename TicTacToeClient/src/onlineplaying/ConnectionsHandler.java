/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineplaying;

import com.google.gson.Gson;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tictactoeclient.Navigator;
import utilities.Codes;

/**
 *
 * @author Ziad-Elshemy
 */
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
                
                //mouth.println("Player No.");
                
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
                
                double registerationResult = (double) responseData.get(1);
                Platform.runLater(()->{
                    if (registerationResult == 1) {
                            navigator.goToPage(stage, "/tictactoeclient/LoginScreen.fxml");
                    }else if (registerationResult == 0) {
                        System.out.println("please change you username");
                    }else{
                        
                    }
                    
                });
            }
            
        }).start();
    }
    
    
}
