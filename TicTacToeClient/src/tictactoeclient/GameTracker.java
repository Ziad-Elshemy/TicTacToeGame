/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;

/**
 *
 * @author TBARAK
 */
public class GameTracker {
    private List<Move> moves = new ArrayList<>();
    
    // class to represent a move
    public static class Move implements Serializable{
        transient Button button;
        char player;

        public Move(Button button, char player) {
            this.button = button;
            this.player = player;
        }

        public Button getButton() {
            return button;
        }

        public char getPlayer() {
            return player;
        }
    }

    //  record a move
    public void recordMove(Button button, char player) {
        moves.add(new Move(button, player));
    }

    // get moves
    public List<Move> getMoves() {
        return moves;
    }

    // clear moves
    public void clearMoves() {
        moves.clear();
    }
    //sava to file
    public void saveToFile(){
        RecordFile recordFile = new RecordFile(moves);

    }
}

