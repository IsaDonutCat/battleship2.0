public class Winner {

    Board answers, guesses;
    int rowNums, colNums, maxTries;
    int tryNum = 1; // to adjust for human counting

    public Winner (Board play1, Board play2, int retries)
    {
        answers = play1;
        guesses = play2;
        maxTries = retries;
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

    public bolean match( int rowG, int colG) // check if hit
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
    
        if (answers.grid[rowG][colG] != 'O')
        {
            if (sunk())
                System.out.println("Sunk!");
            else
            { 
                if (answers.grid[rowG][colG] == '*')
                    guesses.grid[rowG][colG] == 'x';
                else if (answers.grid[rowG][colG] == 'v' || answers.grid[rowG][colG] == 'v')
                    guesses.grid[rowG][colG] == '';
            }

        }

        guesses.printBoard();
        return false;
    }

    public boolean sunk() // check if sunk.. all ships must be sunk for player 1 to win so check here to lessen running time
    {
        int cursor = 0;


        
        if (chickendinner())
        {
            System.out.println("Player 2 wins!");
            return true;
        }
        else
            return false;
    }
}
