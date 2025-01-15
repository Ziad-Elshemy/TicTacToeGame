/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.util.Random;

/**
 *
 * @author STW
 */
public class PlayAgainstComputer
{
    static final int Maximizer =1; // the player which will play first either Me or PC (xTurn)
    static final int Minimizer=-1; // The opponent (oTurn)
    public static boolean isMovesLeft(int board[][])
    {
        for(int i=0 ; i<3 ; i++)
        {
            for(int j=0 ; j<3 ; j++)
            {
                if(board[i][j]==0)
                    return true;
            }
        }
        return false;
    }

    private static int evaluateTheBoardState(int board[][])
    {
        for(int row=0 ; row<3 ; row++)
        {
            if(checkRaws(board))
            {
                if(board[row][0]==Maximizer)
                    return 10;
                else if(board[row][0]==Minimizer)
                    return -10;
            }
        }
        for(int col=0 ; col<3 ; col++)
        {
            if(checkColomns(board))
            {
                if(board[0][col]==Maximizer)
                    return 10;
                else if(board[0][col]==Minimizer)
                    return -10;    
            }
        }
        if(checkDiagonals(board))
        {
            if(board[1][1]==Maximizer)
                return 10;
            else  if(board[1][1]==Minimizer)
                return -10;
        }
        return 0;
    }

    private static int minimax(int board[][], int depth, boolean isMax) 
    {
            int score = evaluateTheBoardState(board);
            // If Maximizer has won
            if (score == 10)
                return score - depth;
            // If Minimizer has won
            if (score == -10)
                return score + depth;
            // If no moves left and no winner
            if (!isMovesLeft(board) || depth>=9)
                return 0;
            // If this is the maximizer's move
            if (isMax) 
            {
                int best = Integer.MIN_VALUE;
                // Traverse all cells
                for (int row = 0; row < 3; row++) 
                {
                    for (int col = 0; col < 3; col++) 
                    {
                        // Check if cell is empty
                        if (board[row][col] == 0) 
                        {
                            // Make the move
                            board[row][col] = Maximizer;
                            // Call minimax recursively and choose the maximum value
                            best = Math.max(best, minimax(board,depth + 1, false));
                            // Undo the move
                            board[row][col] = 0;
                        }
                    }
                }
                return best;
            } 
            else 
            {
                // If this is the minimizer's move
                int best = Integer.MAX_VALUE;
                // Traverse all cells
                for (int row = 0; row < 3; row++) 
                {
                    for (int col = 0; col < 3; col++) 
                    {
                        // Check if cell is empty
                        if (board[row][col] == 0) 
                        {
                            // Make the move
                            board[row][col] = Minimizer;
                            // Call minimax recursively and choose the minimum value
                            best = Math.min(best, minimax(board,depth + 1, true));
                            // Undo the move
                            board[row][col] = 0;
                        }
                    }
                }
                return best;
            }
    }

   
    public static int[] findBestMove(int board[][]) 
    {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};
        // Traverse all cells, evaluate minimax function for all empty cells
        for (int row = 0; row < 3; row++) 
        {
            for (int col = 0; col < 3; col++) 
            {
                // Check if cell is empty
                if (board[row][col] == 0) 
                {
                    // Make the move
                    board[row][col] = Maximizer;
                    // Compute evaluation function for this move
                    int moveVal = minimax(board, 0, false);
                    // Undo the move
                    board[row][col] = 0;
                    // If the value of the current move is more than the best value, update best
                    if (moveVal > bestVal) 
                    {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }
    
    private static boolean checkRaws(int board[][])
    {
        for(int row=0 ; row<3 ; row++)
        {
            if((board[row][0]==board[row][1]) && (board[row][1]==board[row][2]) && (board[row][0]!=0))
            {
                return true;
            }
        }
        return false;
    }
    
    private static boolean checkColomns(int board[][])
    {
        for(int col=0 ; col<3 ; col++)
        {
            if((board[0][col]==board[1][col]) && (board[1][col]==board[2][col]) && (board[0][col]!=0))
            {
                return true;
            }
        }
        return false;
    }
    
    private static boolean checkDiagonals(int board[][])
    {
        for(int i=0 ; i<=2 ;i+=2)
        {
            if((board[0][i]==board[1][1]) && (board[1][1]==board[2][2-i]) && (board[1][1]!=0))
            {
                return true;
            }
        }
        return false;
    }
    
    static int[] getRandomMove()
    {
        int[] bestMove = new int[2];
        Random random = new Random();
        do {
            // Random row index
            bestMove[0] = random.nextInt(3);
            // Random column index
            bestMove[1] = random.nextInt(3);
            } 
            while (!isValidMove(bestMove[0], bestMove[1])); // Ensure the move is valid
        
        return bestMove;
    }
    
    private static boolean isValidMove(int x, int y) 
    {
        return (VsComputerSceneController.board[x][y]==0); 
    }
    
    public static int[] enhancedBestMove(int board[][],boolean firstMove) 
    {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};
        // Traverse all cells, evaluate minimax function for all empty cells
        if((board[0][0]==0) && firstMove)
        {
            bestMove[0]=0;
            bestMove[1]=0;
            return bestMove;
        }
        else if(board[1][1]==0 && firstMove)
        {
            bestMove[0]=1;
            bestMove[1]=1;
            return bestMove;
        }
        for (int row = 0; row < 3; row++) 
        {
            for (int col = 0; col < 3; col++) 
            {
                // Check if cell is empty
                if (board[row][col] == 0) 
                {
                    // Make the move
                    board[row][col] = Maximizer;
                    // Compute evaluation function for this move
                    int moveVal = minimax(board, 0, false);
                    // Undo the move
                    board[row][col] = 0;
                    // If the value of the current move is more than the best value, update best
                    if (moveVal > bestVal) 
                    {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }
}