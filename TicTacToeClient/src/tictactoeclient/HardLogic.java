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
    


    private String[][] board;

    public HardLogic(String[][] board) {
        this.board = board;
    }

    /**
     * Finds the best move for the computer using the minimax algorithm.
     *
     * @param depthLimit The maximum depth the minimax algorithm should explore.
     * @return An array of size 2 representing the row and column of the best move.
     */
    public int[] findBestMove(int depthLimit) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];
        int moveScore;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].isEmpty()) {
                    board[i][j] = "O"; // Simulate the computer's move
                    moveScore = minimax(0, depthLimit, false);
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

    /**
     * The minimax algorithm to calculate the optimal score.
     *
     * @param depth        The current depth of the search.
     * @param depthLimit   The maximum depth to search.
     * @param isMaximizing Whether the algorithm is currently maximizing or minimizing.
     * @return The score of the move.
     */
    private int minimax(int depth, int depthLimit, boolean isMaximizing) {
        if (checkWin("O")) {  // If the computer wins
            return 10 - depth;
        }
        if (checkWin("X")) {  // If the player wins
            return -10 + depth;
        }
        if (isBoardFull() || depth >= depthLimit) {  // Draw or reached depth limit
            return 0;
        }

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].isEmpty()) {
                    board[i][j] = isMaximizing ? "O" : "X";
                    int currentScore = minimax(depth + 1, depthLimit, !isMaximizing);
                    bestScore = isMaximizing
                            ? Math.max(bestScore, currentScore)
                            : Math.min(bestScore, currentScore);
                    board[i][j] = ""; // Undo the move
                }
            }
        }
        return bestScore;
    }

    /**
     * Checks if a player has won the game.
     *
     * @param player The player symbol ("X" or "O").
     * @return True if the player has won, false otherwise.
     */
    private boolean checkWin(String player) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0].equals(player) && board[i][1].equals(player) && board[i][2].equals(player)) ||
                (board[0][i].equals(player) && board[1][i].equals(player) && board[2][i].equals(player))) {
                return true;
            }
        }
        return (board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)) ||
               (board[0][2].equals(player) && board[1][1].equals(player) && board[2][0].equals(player));
    }

    /**
     * Checks if the board is full.
     *
     * @return True if all cells are filled, false otherwise.
     */
    private boolean isBoardFull() {
        for (String[] row : board) {
            for (String cell : row) {
                if (cell.isEmpty()) return false;
            }
        }
        return true;
    }

}
