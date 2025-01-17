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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import tictactoeclient.Listener;
import utilities.Codes;

/**
 *
 * @author abbdullahraed
 */
public abstract class NetworkAccessLayer 
{
    public static Socket mySocket;
    private static DataInputStream fromServer;
    private static PrintStream toServer;
    private static PlayerDto playerData ;
    private static Gson gsonFile = new Gson();
    private static Listener myRef;
    
    public static void startConnectionHandling( )
    {
        try{
            mySocket = new Socket("127.0.0.1", 5005);
            fromServer = new DataInputStream(mySocket.getInputStream());
            toServer = new PrintStream(mySocket.getOutputStream());
            Thread th = new Thread(){
                @Override
                public void run() 
                {
                    try {
                        while (true) 
                        {                                
                            String serverResponse = fromServer.readLine();
                            System.out.println("server respone data is :"+serverResponse);
                            //Convert string coming from the server to ArrayList
                            ArrayList responseData = gsonFile.fromJson(serverResponse, ArrayList.class);
                            double code = (double) responseData.get(0);
                            if(code == Codes.REGESTER_CODE)
                            {
                                registerationResponse(responseData);
                            }
                            else if(code == Codes.CHANGE_PASSWORD_CODE)
                            {
                                System.out.println("the edit response data: "+ responseData);
                                editProfileRespond(responseData);
                            }
                            else if(code == Codes.SEND_INVITATION_CODE)
                            {
                                String playerData = (String) responseData.get(1);
                                //PlayerDto player = gsonFile.fromJson(playerData, PlayerDto.class);
                                System.out.println("the invite response data: "+ responseData);
                                recieveInvitationResponse(responseData);
                            }
                            else if(code == Codes.INVITATION_REPLY_CODE)
                            {
                                double isAccepted = (double) responseData.get(1);
                                String playerData = (String) responseData.get(2);
                                //PlayerDto player = gsonFile.fromJson(playerData, PlayerDto.class);
                                System.out.println("the invite response data: "+ responseData);
                                recieveReplyOnInvitationResponse(responseData);
                            }
                        }
                    } 
                    catch (IOException ex) 
                    {
                        Logger.getLogger(NetworkAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };  
            th.start();
        } catch (IOException ex) {
            Logger.getLogger(NetworkAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // noooooooooooooooooooooooooooooooooooooooooooteeeeeeeeeeeeeeeeeeeee
    public static void setRef(Listener ref){
        myRef = ref;
    }
    
    public static void sendRequest(String clientRequest)
    {
        //client request is an Arraylist contain code and the information , converted to string using Gson class
        //myRef=ref;
        
        toServer.println(clientRequest);
        
        System.out.println("from sendRequest function");
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
    
    public static void recieveInvitationResponse(ArrayList responseData){
        myRef.onServerResponse(true, responseData);
        
    }
    
    public static void recieveReplyOnInvitationResponse(ArrayList responseData){
        double isAccepted = (double) responseData.get(1);
        if (isAccepted == 1) 
        {
            myRef.onServerResponse(true,responseData);
        }
        else
        {
             myRef.onServerResponse(false,responseData);
        }
        
    }
}
