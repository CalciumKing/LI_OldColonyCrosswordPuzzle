import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        char[][] puzzle = Utils.getStringBoard();
        boolean[][] boolPuzzle = new boolean[12][12];

        for (int i = 0; i < boolPuzzle.length; i++)
            for (int j = 0; j < boolPuzzle[i].length; j++)
                boolPuzzle[i][j] = puzzle[i][j] != '*';

        Scanner input = new Scanner(System.in);
        while(true) {
            Crossword.showBoard();
            int response = input.nextInt();
            switch(response) {
                case 1 -> displayClues();
                case 2 -> displayClue();
                case 3 -> eraseAnswer();
                case 4 -> submit();
                case 5 -> System.exit(0);
                default -> System.out.println("Please Enter A Number 1-5");
            };
        }
    }
    private static void displayClues() throws FileNotFoundException {
        System.out.println("Horizontal:");
        for(int i = 0; i < Utils.getClues().size(); i++) {
            Clue clue = Utils.getClues().get(i);
            if(clue.getDirection() == 'h')
                System.out.println(clue.getClueNum() + ": " + clue.getFirstName());
        }

        System.out.println("Vertical:");
        for(int i = 0; i < Utils.getClues().size(); i++) {
            Clue clue = Utils.getClues().get(i);
            if(clue.getDirection() == 'v')
                System.out.println(clue.getClueNum() + ": " + clue.getFirstName());
        }
    }
    private static void displayClue() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("Which Clue Would You Like To View?");
        char dir;
        do {
            System.out.println("Enter Clue's Direction [h/v]");
            dir = input.next().charAt(0);
            if(dir != 'h' && dir != 'v')
                System.out.println("That is not a valid response, please enter [h/v]");
        } while(dir != 'h' && dir != 'v');

        int num;
        do {
            System.out.println("Enter Clue's Number");
            num = input.nextInt();
            if(num != 'h' && num != 'v')
                System.out.println("That is not a valid response, please enter a clue number");
        } while(num != 'h' && num != 'v');

        Clue clue = Utils.getClues().get(num);
        if(clue.getDirection() == dir)
            System.out.println(clue.getClueNum() + ": " + clue.getFirstName());

        String response;
        do {
            System.out.println("Enter Your Response");
            response = input.nextLine();
            if(response.length() == clue.getLastName().length())
                System.out.println("Response length does not equal clue length");
        } while(response.length() == clue.getLastName().length())
    }
}