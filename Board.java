System.util.Scanner;
System.lang.Math;

public class Board
{
    char[][] guesses;
    char[][] answers;
    int numRow, numCol,;
    boolean playType; // true = normal, false = fast

    public Board (int numrows, int numcols, boolean playchosen) // constructor file
    {
        numRow = numrows;
        numCol = numcols;
        playType = playchosen;
        grid = new char[numRow][numCol];
        for (int a = 0; a < numRow; a++) //intializing th array
        {
            for (int b = 0; b < numCol; b++)
                grid[a][b] = "O";
        }
    }

    public void printBoard()
    {
        for (int c = 0; c < numRow * 2 - 1; c ++)
        {   
            if (a % 2 == 0)
            {
                for (int d = 0; d < numCol * 2 - 1; d++)
                {
                    if (d % 2 == 0)
                        System.out.print(grid[c][d]);
                    else  
                        System.out.print("|");
                }
                System.out.println();
            }
            else
            {
                for (int e = 0; e < numCol * 2 - 1; e++)
                    System.out.print("-");
                System.out.println();
            }
        }

        return;
    }

    public void placePieces (Ship[] arr)
    {   
        for (Ship x : arr)
        {
            if (playType)
                playship(x);
            else
                ranShip(x);
        }

        return;
    }

    public void playShip (Ship boat)
    {
        
    }

    public void ranShip(Ship boat)
    {

    }
}