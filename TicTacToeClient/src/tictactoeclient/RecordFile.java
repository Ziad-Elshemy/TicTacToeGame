/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import tictactoeclient.GameTracker.Move;

/**
 *
 * @author HANY
 */
public class RecordFile {
    
    
    
    
     public static void addToFile(ArrayList<Move> moves,String path,String AgainestPlayerNae) {
        String localDate = getDate();
        String fileName = localDate;  // Use current date and time as file name
        File file = new File(path  +AgainestPlayerNae+"_" + fileName); //src/games/

        try (FileOutputStream fileOutput = new FileOutputStream(file);
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput)) {
            objectOutput.writeObject(moves);  // Write the list of moves to the file

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static ArrayList<Move> readFromFile(String FileName) {
        ArrayList<Move> moves = null;
        //System.out.println("Current Date ="+localDate);
        File file=new File(FileName); //"src/games/"
        try (
              
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            moves = (ArrayList<Move>) objectInputStream.readObject(); // read ArrayList of moves

        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecordFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecordFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RecordFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        return moves;
    }
            private static String getDate()
    {
      Date  myDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        
        return dateFormat.format(myDate);
    }
    
}