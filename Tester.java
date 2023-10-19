import java.util.Scanner;
import java.util.Arrays; // i jsut want copy to range :(

public class Tester
{

    public static void main(String[] args)
    {
        Scanner intoMain = new Scanner(System.in);

        Ship[] shipSet = { new Ship(2,"PT Boat"), new Ship(3,"Submarine"), 
            new Ship(3,"Destroyer"), new Ship(4,"Battleship"), new Ship(5, "Carrier") };
        
        int rows, cols;
        String answered;
        boolean choice;
        do 
        {
            System.out.print("Welcome to Battleship!\n What version would you like to play? (Fast/Regular)");
            answered = intoMain.nextLine().toLowerCase().trim();
        }
        while (!answered.equals("fast") && !answered.equals("regular"));

        if (answered.equals("fast"))
        {
            System.out.println("Welcome to fast mode!");
            System.out.println("In this mode, the computer will place 3 ships: a submarine (3 units long), a battleship (4 units long), and a carrier (5 units long).  ");
            System.out.println("Then, you will have 20 guesses. An 'O' will represent an unknown location, \na '.' will represent a miss, a '!' will represent a hit and a 'x' will represent a sunk ship.");
            System.out.println("If you manage to sink all the ships in the given number of guesses, you will win. Otherwise, the computer will win.");
            rows = 8;
            cols = 8;
            shipSet = Arrays.copyOfRange(shipSet,2,5);
            choice = false;
        }
        else
        {
            System.out.println("Welcome to regular mode!");
            System.out.println("In this mode, player 1 will place 5 ships: a PT Boat (2 units long), a submarine (3 units long),\na destroyer (3 units long), a battleship (4 units long), and a carrier (5 units long).  ");
            System.out.println("Then, player 2 will have a given number of guesses. An 'O' will represent an unknown location,\na '.' will represent a miss, a '!' will represent a hit and a 'x' will represent a sunk ship.");
            System.out.println("If player 2 manages to sink all the ships in the given number of guesses, player 2 will win. Otherwise, player 1 will win.");
            rows = 10;
            cols = 10;
            choice = true;
        }

        System.out.print("Ready to proceed? Enter any key");
        answered = intoMain.nextLine();
        
        Board answers = new Board(rows, cols, choice, intoMain);
        answers.placePieces(shipSet);

        Board guesses = new Board(rows, cols, choice, intoMain);
        Winner referee;

        if (choice)
        {
           
            System.out.print("# of guesses: ");
            referee = new Winner(answers, guesses, intoMain.nextInt());
            answered = intoMain.nextLine(); //clears the buffer
            System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); //clears the screen bc im not learning
        }
        else
        {
            referee = new Winner(answers, guesses, 20);
        }
        
        int guessRow, guessCol;
        guesses.printBoard();

        do  
        {   
            
            do 
            {
                System.out.print("Enter a coordinate to guess:");
                answered = intoMain.nextLine();
            }
            while (!guesses.checkCoords(answered));

            guessCol = (int) answered.charAt(0) - 97;
            guessRow = Integer.parseInt(answered.substring(1)) - 1;
        }
        while(!referee.match(guessRow, guessCol));

        intoMain.close();
    }   
}