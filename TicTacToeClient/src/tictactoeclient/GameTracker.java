/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.io.Serializable;
import java.util.ArrayList;


/**
 *
 * @author TBARAK
 */
public class GameTracker implements Serializable{
    private ArrayList<Move> moves = new ArrayList<>();
    
    // class to represent a move
    public class Move implements Serializable{

        String button;
        char player;

        public Move(String button, char player) {
            this.button = button;
            this.player = player;
        }

        public String getButton() {
            return button;
        }

        public char getPlayer() {
            return player;
        }
        
    }

    //  record a move
    public void recordMove(String button, char player) {
        moves.add(new Move(button, player));
        //System.out.println("button ="+button+"player = "+player);
    }

    // get moves
    public ArrayList<Move> getMoves() {
        return moves;
    }

    // clear moves
    public void clearMoves() {
        moves.clear();
    }
    //sava to file
    public void saveToFile(String path){
        ///System.out.println("MOVES is GameRecod" + moves.toString());
        RecordFile.addToFile(moves,path);

    }
}

