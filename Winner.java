public class Winner {

    Board answers, guesses;
    int rowNums, colNums, maxTries;
    int tryNum = 1; // to adjust for human counting

    public Winner (Board play1, Board play2, int retries)
    {
        answers = play1;
        guesses = play2;
        maxTries = retries;
        rowNums = answers.length;
        colNums = answers[0].length;
    }

    public boolean chickendinner () // check win condition
    {   
        for (int i = 0; i < rowNums; i++)
        {
            for (int j = 0; j < colNums; j++)
            {
                if (answers.grid[i][j] != 'O' && guesses.grid[i][j] == 'O') // a ship has not been guessed yet
                    return false;
            }
        } // if this return false and max tries exceeded, player 1/computer won.
        
        return true;
        
    }

    public boolean match( int rowG, int colG) // check if hit
    {
        tryNum++;
        
        if (tryNum > maxTries)
        {
            if (chickendinner())
                System.out.println("Player 2 wins!");
            else
                System.out.println("Player 1 wins!");
            return true;
        }
        else if (tryNum == maxTries)
            System.out.println("Final shot!");
        else
            System.out.println("Guess #" + tryNum);

        if (guesses.grid[rowG][colG] != 'O')
        {
            System.out.println("You already guessed that spot! Try again. ");
            tryNum--;
            guesses.printBoard();
            return false;
        }

        if (answers.grid[rowG][colG] != 'O')
        {
            if (answers.grid[rowG][colG] == '*')
                guesses.grid[rowG][colG] = '!';
            else if (answers.grid[rowG][colG] == 'v' || answers.grid[rowG][colG] == '>')
                guesses.grid[rowG][colG] = '/'; 
            else
                guesses.grid[rowG][colG] = 92; // backslash has value 92 and i'm not dealing w/ the headache of escape codes
            
            if (sunk(rowG,colG) == 0)
                System.out.println("Sunk!");
            else if (sunk(rowG,colG) == 2)
            {
                System.out.println("Player 2 wins!");
                return true;
            }
            else
            {
                System.out.println("Hit!");
            }

        }// close if landed somewhere important loop
        else
        {
            guesses.grid[rowG][colG] = '.';
            System.out.println("Miss!");
        }
        guesses.printBoard();
        return false;
    }

    public int sunk(int rowS, colS) // check if sunk.. all ships must be sunk for player 1 to win so check here to lessen running time. return 0 for sunk, 1 for false and 2 for winner
    {
        int cursor; //cursor should be at left top poitn (\) then continue to check down to  /
        
        if (chickendinner())
        {
            System.out.println("Player 2 wins!");
            return 2;
        }
        else
        {
            if (reOrient)
            {
                cursor = colS;//remember for horizontal, the columns are the ones changing
                while (answers.grid[rowS][cursor] != '<') //scroll all the way to the start
                    cursor--;

                while (cursor < colNums)
                {
                    if (guesses.grid[rowS][cursor] == '/')
                        return 0;
                    else if (guess.grid[rowS][cursor] == 'O')
                        return 1;
                    cursor++;
                }
            }
            else
            {
                cursor = rowS;//remember for horizontal, the columns are the ones changing
                while (answers.grid[cursor][colS] != '^') //scroll all the way to the start
                    cursor--;

                while (cursor < rowNums)
                {
                    if (guesses.grid[cursor][colS] == '/')
                        return 0;
                    else if (guess.grid[cursor][colS] == 'O')
                        return 1;
                    cursor++;
                }
            }
        }
        return 1;
    }

    public boolean reOrient (rowO, colO )//return true for horizontal, false for vertical
    {
        int mousey = colO;

        while (mousey >= 0)
        {
            if (answers.grid[rowO][colO] == '<') //check the answers since it's clearer anyways. 
                return true;
            else if (answers.grid[rowO][colO] == 'O') //there is defnitely no ship ina blank space
                return false;
            mousey--;
        }

        return false;
    }
}
