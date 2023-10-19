import java.util.Scanner;
import java.lang.Math;

public class Board
{
    public char[][] grid;
    int numRow, numCol;
    boolean playType; // true = normal, false = fast
    Scanner inputSource;

    public Board (int numrows, int numcols, boolean playchosen, Scanner mainScanner) // constructor file
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
        inputSource = mainScanner; //clones it
    }

    public void printBoard()
    {
        char temp;

        for (int c = 0; c < numRow * 2 - 1; c ++)
        {   

            if (c == 0) // runo nly once
            {
                System.out.print("  ");
                for (int d = 0; d < numRow; d++)
                {
                    temp = (char) (d + 65);
                    System.out.print(" " + temp);
                }
                System.out.println();
            }

            

            if (c % 2 == 0)
            {
                System.out.print((c/2+1) + " ");
                if (c/2 + 1 < 10)
                    System.out.print(" ");
                for (int e = 0; e < numRow; e++)
                {
                    System.out.print(grid[c/2][e]);
                    if (e < numRow - 1)
                        System.out.print("|");
                }
            }
            else
            {
                System.out.print("   ");
                for (int f = 0; f < numRow * 2 - 1; f++)
                    System.out.print("-");
            }
            System.out.println(); //creates a new row for printing
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
        int orientPlay, firRowPlay, firColPlay;
        
        do
        {
            System.out.println("Place your " + boat.getName() +". It is " + boat.getSize() + " units long.");
            do 
            {
                System.out.print("Enter orientation (horizontal/vertical):");
                ans = inputSource.nextLine().toLowerCase().trim();

                
            }
            while (!ans.equals("vertical") && !ans.equals("horizontal"));

            if (ans.equals("vertical"))
                orientPlay = 0;
            else
                orientPlay = 1;

            do 
            {   
                System.out.print("Enter Coordinates for the Upper Left corner(A1, B1, etc.): ");
                ans = inputSource.nextLine().toLowerCase().trim();
            }
            while (!checkCoords(ans));

            firColPlay = (int) ans.charAt(0) - 97;
            firRowPlay = Integer.parseInt(ans.substring(1)) - 1;
        }
        while (!checkBoard(firColPlay, firRowPlay, boat.getSize(), orientPlay));

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

    public boolean checkCoords(String coordGiven)
    {
        char[] checker = coordGiven.toCharArray();
        int len = checker.length;

        if (len < 2)
        {
            System.out.println("Missing input");
            return false;
        }
        else if (len > 3)
        {
            System.out.println("The input is too big.");
            return false;
        }
        else if (!Character.isLetter(checker[0]))
        {
            System.out.print("No column coordinate found");
            return false;
        }
        else if ((len == 3 && (!Character.isDigit(checker[1]) || !Character.isDigit(checker[2]))) || (len == 2 && !Character.isDigit(checker[1])))
        {
            System.out.println("Invalid row coordinate");
            return false;
        }
        else
        {
            return true;
        }
    }
}