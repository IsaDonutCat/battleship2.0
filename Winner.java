public class Winner {

    Board answers, guesses;
    int rowNums, colNums, maxTries, guessRow, guessCol;
    int tryNum = 1; // to adjust for human counting

    public Winner (Board play1, Board play2, int retries) //constructor
    {
        answers = play1;
        guesses = play2;
        maxTries = retries;
        rowNums = answers.grid.length;
        colNums = answers.grid[0].length;
    }

    
    /** 
     * @param rowG
     * @param colG
     * @return boolean
     */
    public boolean match( int rowG, int colG) // check if hit
    {
        tryNum++;
        guessRow = rowG; //i originall didn't do this but then i would end up having to pass the inputs back  and forth between functions inside here so. yea. 
        guessCol = colG;//not worth it and too much of a headache to keep straight.
        
        if (tryNum > maxTries)
        {
            if (chickendinner())
                System.out.println("Player 2 wins!");
            else
                System.out.println("Player 1 wins!");
            return true;
        }
        else if (tryNum == maxTries)
            System.out.println("Final shot!");//hehe pun
        else
            System.out.println("Guess #" + tryNum);

        if (guesses.grid[guessRow][colG] != 'O')
        {
            System.out.println("You already guessed that spot! Try again. ");
            tryNum--; //gives them their try back
            guesses.printBoard();
            return false;
        }

        if (answers.grid[guessRow][guessCol] != 'O')
        {
            guesses.grid[guessRow][guessCol] = '!'; // originally. i set the ends of ships to a different sunk. but that's not very fair. so boo :(
            
            if (sunk())
            {
                if (chickendinner())
                {
                   System.out.println("Player 2 wins!");
                   return true;//ends it early since p2 won
                }
                System.out.println("Sunk!"); // since it returns no need for else part
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
        guesses.printBoard(); //let's person see the board and help them decide where to judge next
        return false;
    }

    public boolean sunk() // check if sunk.. all ships must be sunk for player 1 to win so check here to lessen running time. return 0 for sunk, 1 for false and 2 for winner
    {
        int cursor,start; //cursor should be at left top poitn (\) then continue to check down to  /

        //og i tested chickendinner here before realizing...wait i can test it inside the hit

        if (reOrient())
        {
            cursor = guessCol;//remember for horizontal, the columns are the ones changing
            while (cursor >= 1 && answers.grid[guessRow][cursor - 1] != '<') //scroll all the way to the start
            {    
                cursor--; 
            }

        start = cursor;

            while (cursor < colNums)
            {
                if (answers.grid[guessRow][cursor] != 'O' && guesses.grid[guessRow][cursor] == 'O')
                    return false;
                else if (answers.grid[guessRow][cursor] == '>' && guesses.grid[guessRow][cursor] == '!')
                {
                    do
                    {
                        guesses.grid[guessRow][start] = 'X';
                         start++;
                    }
                    while (answers.grid[guessRow][start] != 'O');
                    return true;
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
                        return false;
                    else if (answers.grid[cursor][guessCol] == '>' && guesses.grid[cursor][guessCol] == '!')
                    {
                        do
                        {
                            guesses.grid[guessRow][start] = 'X';
                            start++;
                        }
                        while (answers.grid[guessRow][start] != 'O');
                        return true;
                    }
                    cursor++;
                }
            }
        return false;
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

    public boolean chickendinner () // check win condition
    {   //also i moved this to the end bc. thats where ud expect the win condition right??SS
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
}
