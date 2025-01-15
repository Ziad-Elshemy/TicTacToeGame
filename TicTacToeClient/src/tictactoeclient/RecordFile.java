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
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.util.converter.LocalDateTimeStringConverter;
import tictactoeclient.GameTracker.Move;

/**
 *
 * @author HANY
 */
public class RecordFile {
    File file;
    FileOutputStream fileOutput;
    ObjectOutputStream objectOutput;
    static String localDate = getDate();
    
    
     public static void addToFile(ArrayList<Move> moves) {
        String fileName = localDate;  // Use current date and time as file name
        File file = new File("src/games/" + fileName);

        try (FileOutputStream fileOutput = new FileOutputStream(file);
             ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput)) {

            System.out.println("MOVES in RecordFile class: " + moves.toString());
            objectOutput.writeObject(moves);  // Write the list of moves to the file

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static ArrayList<Move> readFromFile() {
        ArrayList<Move> moves = null;
        System.out.println("Current Date ="+localDate);
        File file=new File( "src/games/"+localDate);
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