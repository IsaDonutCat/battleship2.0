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
            
            if (sunk()) //for some reason sunk is returning false on fast mode
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

    
    /** 
     * @return boolean
     */
    public boolean sunk() // check if sunk.. all ships must be sunk for player 1 to win so check here to lessen running time. return 0 for sunk, 1 for false and 2 for winner
    {
        int cursor; //cursor should be at left top poitn (\) then continue to check down to  /

        //og i tested chickendinner here before realizing...wait i can test it inside the hit
        //System.out.print(reOrient());
        if (reOrient())
        {
            cursor = guessCol;

            while (cursor > 0)
            {
                if (guesses.grid[guessRow][cursor] == '<')
                    break;
                else
                    cursor--;
            }

            while (cursor < colNums)
            {
                if (answers.grid[guessRow][cursor] != 'O' && guesses.grid[guessRow][cursor] == 'O') //if the answer board isn't blank but the guess is, it hasn't been guessed and so not sunk
                    return false; //good on mef or catching a type 
                else if (answers.grid[guessRow][cursor] == '>' && guesses.grid[guessRow][cursor] == '!') //reached the end of the ship
                {
                    do
                    {
                        guesses.grid[guessRow][cursor] = 'X'; //rewinding back to beginning
                        cursor--;
                    }
                    while (cursor > 0 && answers.grid[guessRow][cursor] != '<');

                    guesses.grid[guessRow][cursor] = 'X'; //then once more
                }
                cursor++;
            }
        }
        else
        {
            cursor = guessRow;

            while (cursor > 0)
            {
                if (guesses.grid[cursor][guessCol] == '^')
                    break;
                else
                    cursor--;
            }

            while (cursor < rowNums)
            {
                if (answers.grid[cursor][guessCol] != 'O' && guesses.grid[cursor][guessCol] == 'O') //if the answer board isn't blank but the guess is, it hasn't been guessed and so not sunk
                    return false; //good on mef or catching a type 
                else if (answers.grid[cursor][guessCol] == 'v' && guesses.grid[cursor][guessCol] == '!') //reached the end of the ship
                {
                    do
                    {
                        guesses.grid[cursor][guessCol] = 'X';
                        cursor--;
                    }
                    while (cursor > 0 && answers.grid[cursor][guessCol] != '^');
                    
                    guesses.grid[cursor][guessCol] = 'X'; //then once more
                }
                cursor++;
            }
        }
        return false;
    }

    public boolean reOrient ()//return true for horizontal, false for vertical
    {
        int mousey = guessCol;
        //System.out.print("hi oriental");
        while (mousey >= 0)
        {
            if (answers.grid[guessRow][mousey] == '<') //check the answers since it's clearer anyways. 
                return true;
            else if (answers.grid[guessRow][mousey] == 'O') //there is defnitely no ship ina blank space
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
