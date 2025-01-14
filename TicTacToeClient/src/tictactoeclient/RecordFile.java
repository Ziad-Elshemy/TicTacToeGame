/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import tictactoeclient.GameTracker.Move;

/**
 *
 * @author HANY
 */
public class RecordFile {
    //FileChooser fileChooser;
    File file;
    FileOutputStream fileOutput;
    DataOutputStream dataOutput;
    ObjectOutputStream objectOutput;
    public RecordFile(List<Move> mov)
    {
        //fileChooser = new FileChooser();
        //file = fileChooser.showSaveDialog(null);
        if(file !=null)
        {
            try {
                fileOutput = new FileOutputStream(new File("C:/Users/TBARAK/Desktop/file1"));
                dataOutput = new DataOutputStream(fileOutput);
                objectOutput = new ObjectOutputStream(fileOutput);
                String msg = "added to file";
                ///dataOutput.writeUTF(msg);
                objectOutput.writeObject(mov);
                dataOutput.close();
                fileOutput.close();
                
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(RecordFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RecordFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
