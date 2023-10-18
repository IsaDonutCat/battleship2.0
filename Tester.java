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

        inputMain.close();

        if (answered.equals("fast"))
        {
            rows = 8;
            cols = 8;
            shipSet = Arrays.copyOfRange(shipSet,0,3);
            choice = false;
        }
        else
        {
            rows = 10;
            cols = 10;
            choice = true;
        }

        Board answers = new Board(rows, cols, choice);
        answers.placePieces(shipSet);

        
    }   
}