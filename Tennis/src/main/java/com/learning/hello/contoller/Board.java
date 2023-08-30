package com.learning.hello.contoller;

import java.util.ArrayList;
import java.util.List;

public class Board {

	public Tile[][] board;//2d array

	 

    int grids = 4;

 

    int border = 0;

 

    public int score = 0;

 

    FibbonacciController fb = new FibbonacciController(0);

 

    public Board()
    {
        board = new Tile[4][4];//size of 4

        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                board[i][j] = new Tile();
            }
        }
    }

 

//    public Tile[][] getBoard()
//    {
//        return board;
//    }

    public Tile[] getBoard() {
        Tile[] linearBoard = new Tile[16];
        int k =0;
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)

            {
            	linearBoard[(i*4) + j] = board[i][j];

            }
        }
        return linearBoard;
    }

 

    public int getScore()
    {
        return score;
    }

 

 

    public int getHighTile()
    {
        int high = board[0][0].getValue();
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                if ( board[i][j].getValue() > high )
                {
                    high = board[i][j].getValue();
                }
            }
        }
        return high;
    }

 

    public void print()
    {
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                String s = board[i][j].toString() + " ";
                System.out.print( s );
            }
            System.out.println( "" );
        }
        System.out.println( "Score: " + score );
    }

 

 

    public String toString()
    {
        String s = "";
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                s += board[i][j].toString() + " ";
            }
            s += "\n";
        }
        return s;
    }

 

 

    public void spawn() {
        List<int[]> emptyCells = new ArrayList<>();
        for (int row = 0; row < grids; row++) {
            for (int col = 0; col < grids; col++) {
                if (board[row][col].getValue() == 0) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }

 

        if (!emptyCells.isEmpty()) {
            int[] randomCell = emptyCells.get((int) (Math.random() * emptyCells.size()));
            board[randomCell[0]][randomCell[1]].setValue(1);
        }
    }

 

    public boolean blackOut()
    {
        int count = 0;
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                if ( board[i][j].getValue() > 0 )
                {
                    count++;
                }
            }
        }
        if ( count == 16 )
        {
            return true;
        }
        return false;
    }

 

    public void up() {
        for (int col = 0; col < grids; col++) {
            for (int row = 1; row < grids; row++) {
                if (board[row][col].getValue() != 0) {
                    int currentRow = row;
                    while (currentRow > 0 && (board[currentRow - 1][col].getValue() == 0 || fb.compareTiles(board[currentRow - 1][col].getValue(), board[currentRow][col].getValue()))) {
                        if (fb.compareTiles(board[currentRow - 1][col].getValue(), board[currentRow][col].getValue())) {
                            int combinedValue = fb.nextFibbonacciValue(board[currentRow][col].getValue());
                            score += combinedValue;
                            board[currentRow - 1][col].setValue(combinedValue);
                            board[currentRow][col].setValue(0);
                            break;
                        } else if (board[currentRow - 1][col].getValue() == 0) {
                            board[currentRow - 1][col].setValue(board[currentRow][col].getValue());
                            board[currentRow][col].setValue(0);
                            currentRow--;
                        }
                    }
                }
            }
        }
    }
    public void down() {
        for (int col = 0; col < grids; col++) {
            for (int row = grids - 2; row >= 0; row--) {
                if (board[row][col].getValue() != 0) {
                    int currentRow = row;
                    while (currentRow < grids - 1 && (board[currentRow + 1][col].getValue() == 0 || fb.compareTiles(board[currentRow + 1][col].getValue(), board[currentRow][col].getValue()))) {
                        if (fb.compareTiles(board[currentRow + 1][col].getValue(), board[currentRow][col].getValue())) {
                            int combinedValue = fb.nextFibbonacciValue(board[currentRow][col].getValue());
                            score += combinedValue;
                            board[currentRow + 1][col].setValue(combinedValue);
                            board[currentRow][col].setValue(0);
                            break; 
                        } else if (board[currentRow + 1][col].getValue() == 0) {
                            board[currentRow + 1][col].setValue(board[currentRow][col].getValue());
                            board[currentRow][col].setValue(0);
                            currentRow++;
                        }
                    }
                }
            }
        }
    }

    public void right() {
        for (int row = 0; row < grids; row++) {
            for (int col = grids - 2; col >= 0; col--) {
                if (board[row][col].getValue() != 0) {
                    int currentCol = col;
                    while (currentCol < grids - 1 && (board[row][currentCol + 1].getValue() == 0 || fb.compareTiles(board[row][currentCol + 1].getValue(), board[row][currentCol].getValue()))) {
                        if (fb.compareTiles(board[row][currentCol + 1].getValue(), board[row][currentCol].getValue())) {
                            int combinedValue = fb.nextFibbonacciValue(board[row][currentCol].getValue());
                            score += combinedValue;
                            board[row][currentCol + 1].setValue(combinedValue);
                            board[row][currentCol].setValue(0);
                            break;
                        } else if (board[row][currentCol + 1].getValue() == 0) {
                            board[row][currentCol + 1].setValue(board[row][currentCol].getValue());
                            board[row][currentCol].setValue(0);
                            currentCol++;
                        }
                    }
                }
            }
        }
    }

    public void left() {
        for (int row = 0; row < grids; row++) {
            for (int col = 1; col < grids; col++) {
                if (board[row][col].getValue() != 0) {
                    int currentCol = col;
                    while (currentCol > 0 && (board[row][currentCol - 1].getValue() == 0 || fb.compareTiles(board[row][currentCol - 1].getValue(), board[row][currentCol].getValue()))) {
                        if (fb.compareTiles(board[row][currentCol - 1].getValue(), board[row][currentCol].getValue())) {
                            int combinedValue = fb.nextFibbonacciValue(board[row][currentCol].getValue());
                            score += combinedValue;
                            board[row][currentCol - 1].setValue(combinedValue);
                            board[row][currentCol].setValue(0);
                            break;
                        } else if (board[row][currentCol - 1].getValue() == 0) {
                            board[row][currentCol - 1].setValue(board[row][currentCol].getValue());
                            board[row][currentCol].setValue(0);
                            currentCol--;
                        }
                    }
                }
            }
        }
    }

 

 

    public boolean gameOver() {
        for (int i = 0; i < grids; i++) {
            for (int j = 0; j < grids; j++) {
                if (board[i][j].getValue() == 0 ||
                    (i > 0 && board[i][j].getValue() == board[i - 1][j].getValue()) ||
                    (j > 0 && board[i][j].getValue() == board[i][j - 1].getValue())) {
                    return false; 
                }
            }
        }
        return true; 
    }

 

}