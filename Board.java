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

    public void printBoard() //print board 
    {
        char temp;

        System.out.print("  ");
        for (int d = 0; d < numRow; d++)
        {
            temp = (char) (d + 65);
            System.out.print(" " + temp);
        }
            System.out.println(); //originally this was insnide the loop but im moving it outside since it only needs to run once anyways at the beginning

        for (int c = 0; c < numRow * 2 - 1; c ++) //runs less than one so there isn't a line at the very bottom
        {   

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
            }//every other
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

    
    /** 
     * @param arr
     */
    public void placePieces (Ship[] arr) //cycles thru the array bc im lazy
    {   
        for (Ship x : arr)
        {
            if (playType)
                playShip(x);
            else
                ranShip(x);
            //System.out.println(x.getName());
        }
        printBoard();
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
                ans = inputSource.nextLine().toLowerCase().trim(); //get rid of whitespace
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

            firColPlay = (int) ans.charAt(0) - 97; //lowercase 'a' = 97
            firRowPlay = Integer.parseInt(ans.substring(1)) - 1; //machine coding
        }
        while (!checkBoard(firColPlay, firRowPlay, boat.getSize(), orientPlay));

        return;
    }

    public void ranShip(Ship boat) //places ran ship
    {
        //System.out.println("running");
        int orientRan, firRowRan, firColRan = 5;

        do
        {
            orientRan = (int) (Math.random() * 2); // either horizontal or vertical, so only 2 needed
            firColRan = (int) (Math.random() * numCol); //ok so this kept on placing in the same spot! turns out i truncates the random to 0 before it multiplies :()
            firRowRan = (int) (Math.random() * numRow);
        }
        while (!checkBoard(firColRan, firRowRan, boat.getSize(), orientRan));
        
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

        if (startc < 0 || startR < 0 || finR >= numRow + 1|| finC >= numCol + 1) //changed this to > or equal.at most fin should BE numCol since numCol is in human counting
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
                    System.out.println(startR + startc);
                        //System.out.println("Another ship is occupying that space!");
                    return false;
                }
            }
        }

        for (int g = startR; g < finR; g++) //only changes board after confirming it isn't colliding
        {
            for (int h = startc; h < finC; h++)
            {
                if (g == startR && h == startc) 
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
                        grid[g][h] = '>'; //useful for checking if a ship is sunk
                }
                else
                {
                    grid[g][h] = '*';
                }
            }
        }

        if(playType)
            this.printBoard();//so player doesn't see comp on fast mode
        return true;
    }

    public boolean checkCoords(String coordGiven) //like ethan's stuff, makes sure the coords are valid before trying them
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
            System.out.println("No column coordinate found");
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