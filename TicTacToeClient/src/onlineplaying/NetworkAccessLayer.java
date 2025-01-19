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
                            System.out.println("server respone dta is :"+serverResponse.getClass());
                            //Convert string coming from the server to ArrayList
                            ArrayList responseData = gsonFile.fromJson(serverResponse, ArrayList.class);
                            double code = (double) responseData.get(0);
                            if(code == Codes.REGESTER_CODE)
                            {
                                registerationResponse(responseData);
                            }
                            else if(code == Codes.CHANGE_PASSWORD_CODE)
                            {
                                editProfileRespond(responseData);
                            }
                            else if(code == Codes.SELECT_DATA_FOR_EDIT_PROFILE_CODE)
                            {
                                selectDatatForEditProfileRespond(responseData);
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
    
    public static void sendRequest(String clientRequest , Listener ref)
    {
        //client request is an Arraylist contain code and the information , converted to string using Gson class
        toServer.println(clientRequest);
        myRef=ref;
        System.out.println("from sendRequest fun.");
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
           // System.out.println("OnNetworkAccess SelectForEdit : "+player.getName());
        }
        else
        {
             myRef.onServerResponse(false,responseData);
             System.err.println("select For Edit FALSE");
        }
    }
}
