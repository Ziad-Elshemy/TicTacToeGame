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
import javafx.application.Platform;
import tictactoeclient.Listener;
import utilities.Codes;

/**
 *
 * @author abbdullahraed
 */
public abstract class NetworkAccessLayer 
{
    public static Socket mySocket;
    public static DataInputStream fromServer;
    public static PrintStream toServer;
    public static PlayerDto playerData ; 
    private static Gson gsonFile = new Gson();
    private static Listener myRef;
    public static Listener ref;
    public static boolean isServerOffline;
    public static Thread thread;
    public static ArrayList<PlayerDto> onlinePlayers;
    public static String serverIP  ;
    public static boolean local = false  ;
    
    public static void startConnectionHandling( )
    {
        try{
            mySocket = new Socket(serverIP, 5005);
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
                            System.out.println("server respone data is :"+serverResponse);
                            ArrayList responseData = gsonFile.fromJson(serverResponse, ArrayList.class);
                            int code = ((Double) responseData.get(0)).intValue();
                            if(code == Codes.REGESTER_CODE)
                            {
                                registerationResponse(responseData);

                                
                            }
                            else if(code == Codes.CHANGE_PASSWORD_CODE)
                            {
                               // System.out.println("the edit response data: "+ responseData);
                                editProfileRespond(responseData);
                            }
                            else if(code == Codes.SELECT_DATA_FOR_EDIT_PROFILE_CODE)
                            {
                                selectDatatForEditProfileRespond(responseData);
                            }
                            
                            else if(code == Codes.SEND_INVITATION_CODE)
                            {
                               // String playerData = (String) responseData.get(1);
                                System.out.println("the invite response data: "+ responseData);
                                recieveInvitationResponse(responseData);
                            }
                            else if(code == Codes.INVITATION_REPLY_CODE)
                            {
                                double isAccepted = (double) responseData.get(1);
                                //String playerData = (String) responseData.get(2);
                                System.out.println("the invite response data: "+ responseData);
                                recieveReplyOnInvitationResponse(responseData);
                            }else if(code == Codes.LOGIN_CODE){
                                    LoginResponse(responseData);
                            
                            } 
                            
                            
                            if(code == Codes.GET_ONLINE_PLAYERS){
                                
                                
                                getOnlinePlayersResponse(responseData); 
                            
                            
                            
                            }
                          }
                        }
                    } 
                    catch (IOException ex) 
                    {
                         isServerOffline=true;
                         System.out.println(ex.toString());
                    }
                }
            };  
            thread.start();
        } catch (IOException ex) {
            
                        isServerOffline=true;
                        System.out.println(ex.toString());

        }
    }
    
    public static void setRef(Listener ref){
        myRef = ref;
    }
    
    public static void sendRequest(String clientRequest)
    {
        toServer.println(clientRequest);
        System.out.println("from sendRequest");
    }
    
    public static void registerationResponse(ArrayList responseData)
    {
        double registerationResult = (double) responseData.get(1);
        if (registerationResult == 1) 
        {
             myRef.onServerResponse(true,responseData);
        }
        else
        {
             myRef.onServerResponse(false,responseData);
        }
      
    }
    public static void editProfileRespond(ArrayList responseData)
    {
        double editProfileResult = (double) responseData.get(1);
        if (editProfileResult == 1) 
        {
            myRef.onServerResponse(true,responseData);
        }
        else
        {
             myRef.onServerResponse(false,responseData);
        }
    }
    public static void selectDatatForEditProfileRespond(ArrayList responseData)
    {
        System.out.println("selectDatatForEditProfileRespond: "+responseData.get(1));
        //to convert from linkedTreeMap to PlayerDto
        PlayerDto player = gsonFile.fromJson(responseData.get(1).toString(), PlayerDto.class);
        System.out.println("player : "+responseData);

        if (responseData !=null) 
        {
            myRef.onServerResponse(true,responseData);
            System.out.println("select For Edit True");
            System.out.println("OnNetworkAccess SelectForEdit : "+player.getName());
        }
        else
        {
             myRef.onServerResponse(false,responseData);
             System.err.println("select For Edit FALSE");
        }
    }
    
    public static void recieveInvitationResponse(ArrayList responseData){
        myRef.onServerResponse(true, responseData);
        
    }
    
    public static void recieveReplyOnInvitationResponse(ArrayList responseData){
        double isAccepted = (double) responseData.get(1);
        if (isAccepted == 1.0) 
        {
            myRef.onServerResponse(true,responseData);
        }
        else
        {
             myRef.onServerResponse(false,responseData);
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
                            myRef.onServerResponse(true,responseData);
                            
                    }else{
                            myRef.onServerResponse(false,responseData);

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
