/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.control.Button;
import tictactoeclient.GameTracker.Move;
import utilities.Colors;

/**
 *
 * @author TBARAK
 */
public class GameReplay {
    private Thread gameReplayThread = null ;
    
    public void stopThread()
    {
        if(gameReplayThread != null )
          {
              gameReplayThread.stop();
          }
        
    }

      void replayGame(ArrayList<Move> moves,Button btn1,Button btn2,Button btn3,Button btn4,Button btn5,Button btn6,Button btn7,Button btn8 ,Button btn9)
      {
          if(gameReplayThread != null )
          {
              gameReplayThread.stop();
          }
      
           gameReplayThread = new Thread(() -> {
        
        try {
                 Thread.sleep(700);
                 for (GameTracker.Move move : moves) {
                String buttonId = move.getButton(); 
                char player = move.getPlayer(); 
                Button button = getButtonById(buttonId,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9);
                Platform.runLater(() -> {
                    button.setText(String.valueOf(player));
                    if(player=='X')
                    {
                        button.setTextFill(Colors.X_TEXT);
                    }
                    else
                    {
                        button.setTextFill(Colors.O_TEXT);
                    }
                });

                Thread.sleep(700);  // 
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
    gameReplayThread.start();
}
  private Button getButtonById(String buttonId,Button btn1,Button btn2,Button btn3,Button btn4,Button btn5,Button btn6,Button btn7,Button btn8 ,Button btn9) {
    Button button = null;
    switch (buttonId) {
        case "btn1": button = btn1; break;
        case "btn2": button = btn2; break;
        case "btn3": button = btn3; break;
        case "btn4": button = btn4; break;
        case "btn5": button = btn5; break;
        case "btn6": button = btn6; break;
        case "btn7": button = btn7; break;
        case "btn8": button = btn8; break;
        case "btn9": button = btn9; break;
        
    }
    return button;
}
}
