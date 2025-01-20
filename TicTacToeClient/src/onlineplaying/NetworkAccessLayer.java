/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineplaying;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import tictactoeclient.HomeScreenController;
import tictactoeclient.Listener;
import utilities.Codes;

/**
 *
 * @author abbdullahraed
 */
public abstract class NetworkAccessLayer{
    public static Socket mySocket;
    public static DataInputStream fromServer;
    public static PrintStream toServer;
    public static PlayerDto playerData ; 
    private static final Gson gsonFile = new Gson();
    public static Listener myRef;
    public static Listener ref;

    public static boolean isServerOffline;
    public static Thread thread;
    public static ArrayList<PlayerDto> onlinePlayers;
    
    
    
    
    public static void startConnectionHandling( )
    {
        try{
            mySocket = new Socket("127.0.0.1", 5005);
            fromServer = new DataInputStream(mySocket.getInputStream());
            toServer = new PrintStream(mySocket.getOutputStream());
             thread = new Thread(){
                @Override
                public void run() 
                {
                    try {
                        while (true) 
                        {   
                            
                        if(!mySocket.isClosed() && fromServer!=null){
                            String serverResponse = fromServer.readLine();
                            System.out.println("server respone dta is :"+serverResponse);
                            ArrayList responseData = gsonFile.fromJson(serverResponse, ArrayList.class);
                            double code = (double) responseData.get(0);
                            if(code==Codes.REGESTER_CODE){ 
                                    registerationResponse(responseData);
                            }
                            else if(code == Codes.CHANGE_PASSWORD_CODE){
                                    editProfileRespond(responseData);
                            }
                            else if(code == Codes.LOGIN_CODE){
                                    LoginResponse(responseData);
                            
                            }else if(code == Codes.GET_ONLINE_PLAYERS){
                                
                                
                                getOnlinePlayersResponse(responseData); 
                            
                            
                            
                            }

                            }
                        }
                    } 
                    catch (IOException ex) 
                    {
                        isServerOffline=true;
                    }
                }
            };  
            thread.start();
        } catch (IOException ex) {
            
            isServerOffline=true;
            

        }
    }
    
    public static void sendRequest(String clientRequest , Listener ref)
    {
        toServer.println(clientRequest);
        myRef=ref;
        System.out.println("from sendRequest");
    }
    
    public static void registerationResponse(ArrayList responseData){
        
        Object result = responseData.get(1);
        PlayerDto registerResult = null;
        playerData=null;
        

        if (result instanceof LinkedTreeMap) {
            String jsonResult = gsonFile.toJson(result); 
            registerResult = gsonFile.fromJson(jsonResult, PlayerDto.class); 
        }   
        playerData = registerResult;
        Platform.runLater(()->{
            
                if (playerData!=null) {
                    
                        myRef.onServerResponse(true);

                }else{
                        myRef.onServerResponse(false);

                }

            });
      
    }
    public static void editProfileRespond(ArrayList responseData)
    {
        double editProfileResult = (double) responseData.get(1);
        if (editProfileResult == 1) 
        {
            myRef.onServerResponse(true);
        }
        else
        {
             myRef.onServerResponse(false);
        }
    }
    
    
        
    public static void LoginResponse(ArrayList responseData) {
       
            
            Object result = responseData.get(1);
            PlayerDto loginResult = null;
            playerData=null;


            if (result instanceof LinkedTreeMap) {
                String jsonResult = gsonFile.toJson(result); 
                loginResult = gsonFile.fromJson(jsonResult, PlayerDto.class); 
            }   
            playerData = loginResult;
            Platform.runLater(()->{
                    if (playerData!=null) {
                            myRef.onServerResponse(true);
                            
                    }else{
                            myRef.onServerResponse(false);

                    }
                    
                });
           
    }
    
    public static void getOnlinePlayersResponse(ArrayList responseData) {
        
      Platform.runLater(()->{
        ArrayList response = (ArrayList) responseData.get(1);
        onlinePlayers = new ArrayList<>();

        for (Object obj : response) {
            if (obj instanceof LinkedTreeMap) {
                String jsonResult = gsonFile.toJson(obj);
                try {
                    PlayerDto player = gsonFile.fromJson(jsonResult, PlayerDto.class);
                    onlinePlayers.add(player);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        }
        Platform.runLater(()->{
            
         if(ref!=null){
       
            if (!onlinePlayers.isEmpty()) {
                  System.out.println("Online players: " + onlinePlayers);
                  ref.onOnlinePlayersUpdate(onlinePlayers);

            } else {
                ref.onOnlinePlayersUpdate(null);

            }
        }
        });
       });

}
    

}
