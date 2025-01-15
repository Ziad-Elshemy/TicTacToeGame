/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

/**
 *
 * @author STW
 */
public class HardLogic {
    int depthLimit = 9;
    /* public int[] findBestMove(int depthlimit,int [][]board) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];
        int moveScore;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].isEmpty()) {
                    board[i][j] = -1; // Computer's symbol
                    moveScore = minimax(0, depthlimit, false);
                    board[i][j] = ""; // Undo the move
                    if (moveScore > bestScore) {
                        bestScore = moveScore;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }
     
      private int minimax(int depth, int depthlimit, boolean isMaximizing) {
        if (checkWin("O")) {  // If the computer wins
            return 10 - depth;
        }
        if (checkWin("X")) {  // If the player wins
            return -10 + depth;
        }
        if (isBoardFull() || depth >= depthlimit) {  // Draw or reached depth limit
            return 0;
        }

        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].isEmpty()) {
                        board[i][j] = "O";
                        bestScore = Math.max(bestScore, minimax(depth + 1, depthlimit, false));
                        board[i][j] = "";
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].isEmpty()) {
                        board[i][j] = "X";
                        bestScore = Math.min(bestScore, minimax(depth + 1, depthlimit, true));
                        board[i][j] = "";
                    }
                }
            }
        }
        return bestScore;
    }
    private boolean checkWin(String player) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0].equals(player) && board[i][1].equals(player) && board[i][2].equals(player)) ||
                (board[0][i].equals(player) && board[1][i].equals(player) && board[2][i].equals(player))) {
                return true;
            }
        }
        // Check diagonals
        if ((board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
            (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player))) {
            return true;
        }
        return false;
    }*/
}
