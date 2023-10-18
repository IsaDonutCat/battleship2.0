import java.util.Scanner;
import java.lang.Math;

public class Board
{
    public char[][] grid;
    int numRow, numCol;
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
                grid[a][b] = 'O';
        }
    }

    public void printBoard()
    {
        for (int c = 0; c < numRow * 2 - 1; c ++)
        {   
            
            if (c % 2 == 0)
            {
                if (c == 0)
                {
                    for (int f = 0; f < numCol * 2-1; f++)
                    {
                        for (int d = 0; d < numCol * 2 - 1; d++)
                        {
                            if (d % 2 == 0)
                                System.out.print((char) (d/2 + 65));
                            else  
                                System.out.print("|");
                        }
                    }   
                }

                for (int d = 0; d < numCol * 2 - 1; d++)
                {
                    if (d % 2 == 0)
                        System.out.print(grid[c][d]);
                    else  
                        System.out.print("|");
                }
            }
            else
            {
                for (int e = 0; e < numCol * 2 - 1; e++)
                    System.out.print("-");
            }

            System.out.println();
        }

        return;
    }

    public void placePieces (Ship[] arr)
    {   
        for (Ship x : arr)
        {
            if (playType)
                playShip(x);
            else
                ranShip(x);
        }

        return;
    }

    public void playShip (Ship boat)
    {
        String ans;
        Scanner inputPlayShip = new Scanner(System.in);
        int orientPlay, firRowPlay, firColPlay;
        
        do
        {
            System.out.println("Place your " + boat.getName() +". It is " + boat.getSize() + " units long.");
            do 
            {
                System.out.print("Enter orientation (horizontal/vertical):");
                ans = inputPlayShip.nextLine().toLowerCase().trim();

                
            }
            while (!ans.equals("vertical") && !ans.equals("horizontal"));

            if (ans.equals("vertical"))
                orientPlay = 0;
            else
                orientPlay = 1;
            
            char[] check;

            do 
            {   
                System.out.print("Enter Coordinates for the Upper Left corner(A1, B1, etc.): ");
                ans = inputPlayShip.nextLine().toLowerCase().trim();
                check = ans.toCharArray();
            }
            while (1 < ans.length() && ans.length() < 4 && 
            (ans.length() == 2 && Character.isLetter(check[0]) && Character.isDigit(check[1])) 
            || (ans.length() == 3 && Character.isLetter(check[0]) && Character.isDigit(check[1]) && Character.isDigit(check[2])));

            firColPlay = (int) check[0] - 97;
            firRowPlay = Integer.parseInt(ans.substring(1)) - 1;
        }
        while (!checkBoard(firColPlay, firRowPlay, boat.getSize(), orientPlay));
        
        inputPlayShip.close();
        return;
    }

    public void ranShip(Ship boat)
    {
        int orientPlay, firRowPlay, firColPlay;
        do
        {
            orientPlay = (int) Math.random() * 2;
            firColPlay = (int) Math.random() * numCol;
            firRowPlay = (int) Math.random() * numRow;
        }
        while (!checkBoard(firColPlay, firRowPlay, boat.getSize(), orientPlay));
        
        return;
    }

    public boolean checkBoard (int startc, int startR, int shipSize, int orientcheck)
    {
        int finR, finC;
        if (orientcheck == 0)
        {
            finC = startc+1;
            finR = startR + shipSize;
        }
        else
        {
            finR = startR + 1;
            finC = startc + shipSize;
        }

        if (startc < 0 || startR < 0 || finR > numRow + 1 || finC > numCol + 1)
        {
            if (playType)
                System.out.println("The ship would be hanging off the board");
            return false;
        }

        for (int g = startR; g < finR; g++)
        {
            for (int h = startc; h < finC; h++)
            {
                if (grid[g][h] != 'O')
                {   
                    if (playType)
                        System.out.println("Another ship is occupying that space!");
                    return false;
                }
                else if (g == startR && h == startc)
                {
                    if (orientcheck == 0)
                        grid[g][h] = '^';
                    else
                        grid[g][h] = '<';
                }  
                else if (g == finR - 1 && h == finC - 1)
                {
                    if (orientcheck == 0)
                        grid[g][h] = 'v';
                    else
                        grid[g][h] = '>';
                }
                else
                {
                    grid[g][h] = '*';
                }
            }
        }

        this.printBoard();
        return true;
    }
}