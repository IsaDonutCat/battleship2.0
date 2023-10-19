import java.util.Scanner;
import java.util.Arrays; // i jsut want copy to range :(

public class Tester
{
    public static void main(String[] args)
    {
        Ship[] shipSet = { new Ship(2,"PT Boat"), new Ship(3,"Submarine"), 
            new Ship(3,"Destroyer"), new Ship(4,"Battleship"), new Ship(5, "Carrier") };
        
        int rows, cols;
        Scanner inputMain = new Scanner(System.in);
        String answered;
        boolean choice;
        do 
        {
            System.out.print("Welcome to Battleship!\n What version would you like to play? (Fast/Regular)");
            answered = inputMain.nextLine().toLowerCase().trim();
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
        System.out.print("Hit enter to proceed");
        answered = inputMain.nextLine();
        inputMain.close();

        Board answers = new Board(rows, cols, choice);
        answers.placePieces(shipSet);
        Board guesses = new Board(rows, cols, choice);
        Scanner mainInput2 = new Scanner(System.in);
        Winner referee;

        if (choice)
        {
           
            System.out.print("# of guesses: ");
            referee = new Winner(answers, guesses, mainInput2.nextInt());
            answered = mainInput2.nextLine(); //clears the buffer
            System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); //clears the screen bc im not learning
        }
        else
        {
            referee = new Winner(answers, guesses, 20);
        }
        
        int guessRow, guessCol,len;
        char[] checker;

        do
        {   do 
            {
                System.out.print("Enter a coordinate to guess:");
                answered = mainInput2.nextLine();
                checker = ans.toCharArray();
                len = answered.length();
            }
            while (1 < len && len < 4 && 
            (len == 2 && Character.isLetter(checker[0]) && Character.isDigit(checker[1])) 
            || (len == 3 && Character.isLetter(checker[0]) && Character.isDigit(checker[1]) && Character.isDigit(checker[2])));

            guessCol = (int) checker[0] - 97;
            guessRow = Integer.parseInt(ans.substring(1)) - 1;
        }
        while(!referee.match(guessRow, guessCol));

        mainInput2.close();
    }   
}