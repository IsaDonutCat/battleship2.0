public class Winner {

    Board answers, guesses;
    int rowNums, colNums, maxTries, guessRow, guessCol;
    int tryNum = 1; // to adjust for human counting

    public Winner (Board play1, Board play2, int retries)
    {
        answers = play1;
        guesses = play2;
        maxTries = retries;
        rowNums = answers.grid.length;
        colNums = answers.grid[0].length;
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
        guessRow = rowG;
        guessCol = colG;
        
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

        if (guesses.grid[guessRow][colG] != 'O')
        {
            System.out.println("You already guessed that spot! Try again. ");
            tryNum--;
            guesses.printBoard();
            return false;
        }

        if (answers.grid[guessRow][guessCol] != 'O')
        {
            guesses.grid[guessRow][guessCol] = '!'; // backslash has value 92 and i'm not dealing w/ the headache of escape codes
            
            if (sunk() == 0)
                System.out.println("Sunk!");
            else if (sunk() == 2)
            {

                return true;
            }
            else
            {
                System.out.println("Hit!");
            }

        }// close if landed somewhere important loop
        else
        {
            guesses.grid[guessRow][guessCol] = '.';
            System.out.println("Miss!");
        }
        guesses.printBoard();
        return false;
    }

    public int sunk() // check if sunk.. all ships must be sunk for player 1 to win so check here to lessen running time. return 0 for sunk, 1 for false and 2 for winner
    {
        int cursor,start; //cursor should be at left top poitn (\) then continue to check down to  /
        
        if (chickendinner())
        {
            return 2;
        }
        else
        {
            if (reOrient())
            {
                cursor = guessCol;//remember for horizontal, the columns are the ones changing
                while (cursor >= 1 && answers.grid[guessRow][cursor - 1] != '<') //scroll all the way to the start
                {    cursor--; }

                start = cursor;

                while (cursor < colNums)
                {
                    if (answers.grid[guessRow][cursor] != 'O' && guesses.grid[guessRow][cursor] == 'O')
                        return 1;
                    else if (answers.grid[guessRow][cursor] == '>' && guesses.grid[guessRow][cursor] == '!')
                    {
                        do
                        {
                            guesses.grid[guessRow][start] = 'X';
                            start++;
                        }
                        while (answers.grid[guessRow][start] != 'O');
                        return 0;
                    }
                    cursor++;
                }
            }
            else
            {
                cursor = guessRow;//remember for horizontal, the columns are the ones changing
                start = cursor;
                while (cursor >= 1 && answers.grid[cursor - 1][guessCol] != '^') //scroll all the way to the start
                {    
                    cursor--;
                }

                while (cursor < rowNums)
                {
                    if (answers.grid[cursor][guessCol] != 'O' && guesses.grid[cursor][guessCol] == 'O')
                        return 1;
                    else if (answers.grid[cursor][guessCol] == '>' && guesses.grid[cursor][guessCol] == '!')
                    {
                        do
                        {
                            guesses.grid[guessRow][start] = 'X';
                            start++;
                        }
                        while (answers.grid[guessRow][start] != 'O');
                        return 0;
                    }
                    cursor++;
                }
            }
        }
        return 1;
    }

    public boolean reOrient ()//return true for horizontal, false for vertical
    {
        int mousey = guessCol;

        while (mousey >= 0)
        {
            if (answers.grid[guessRow][guessCol] == '<') //check the answers since it's clearer anyways. 
                return true;
            else if (answers.grid[guessRow][guessCol] == 'O') //there is defnitely no ship ina blank space
                return false;
            mousey--;
        }

        return false;
    }
}
