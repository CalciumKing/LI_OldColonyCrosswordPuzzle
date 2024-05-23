import java.io.*;
import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static char[][] puzzle = new char[0][0];
    private static final boolean[][] boolPuzzle = new boolean[12][12];

    public static void main(String[] args) throws IOException {
        puzzle = Utils.getStringBoard(false);

        for (int i = 0; i < boolPuzzle.length; i++)
            for (int j = 0; j < boolPuzzle[i].length; j++)
                boolPuzzle[i][j] = puzzle[i][j] != '*';

        boolean finishedBoard = false;
        boolean attemptFinish = false;
        do {
            Square[][] squares = Crossword.showBoard(puzzle, boolPuzzle);
            for (Square[] squares1 : squares) {
                for (Square square : squares1)
                    System.out.print(square.getContent() + "\t");
                System.out.println();
            }

            System.out.println("What would you like to do?");
            System.out.println("Display All Clues (1), Display One Clue(2), Erase An Answer(3), Enter An Answer(4), Check Puzzle(5), or Exit Program(6)");
            switch (input.nextInt()) {
                case 1 -> displayClues();
                case 2 -> displayClue();
                case 3 -> eraseAnswer();
                case 4 -> submitAnswer();
                case 5 -> {
                    finishedBoard = submitBoard();
                    attemptFinish = true;
                }
                case 6 -> System.exit(0);
                default -> System.out.println("Please Enter A Number 1-6");
            }

            if (attemptFinish && !finishedBoard) {
                System.out.println("Your board does not match the answer board, keep trying");
                attemptFinish = false;
            } else if (attemptFinish)
                System.out.println("Your board matches the answer board, you win!!!");
        } while (!finishedBoard);
    }

    private static Clue clueStatsInput() throws FileNotFoundException {
        char dir;
        do {
            System.out.println("Enter Clue's Direction [h/v]");
            dir = input.next().charAt(0);
            if (dir != 'h' && dir != 'v')
                System.out.println("That is not a valid response, please enter [h/v]");
        } while (dir != 'h' && dir != 'v');

        int num;
        do {
            System.out.println("Enter Clue's Number");
            num = input.nextInt();
            if (num < 1 || num > Utils.getClues().size())
                System.out.println("That is not a valid response, please enter a clue number");
        } while (num < 1 || num > Utils.getClues().size());

        for (int i = 0; i < Utils.getClues().size(); i++) {
            Clue clue = Utils.getClues().get(i);
            if (clue.getClueNum() == num && clue.getDirection() == dir)
                return clue;
        }
        return null;
    }

    private static void displayClues() throws FileNotFoundException {
        System.out.println("Horizontal Clues:");
        for (int i = 0; i < Utils.getClues().size(); i++) {
            Clue clue = Utils.getClues().get(i);
            if (clue.getDirection() == 'h')
                System.out.println(clue.getClueNum() + ": " + clue.getFirstName());
        }
        System.out.println();
        System.out.println("Vertical Clues:");
        for (int i = 0; i < Utils.getClues().size(); i++) {
            Clue clue = Utils.getClues().get(i);
            if (clue.getDirection() == 'v')
                System.out.println(clue.getClueNum() + ": " + clue.getFirstName());
        }
    }

    private static void displayClue() throws FileNotFoundException {
        System.out.println("Which Clue Would You Like To View?");
        Clue clue = clueStatsInput();
        if (clue == null)
            System.out.println("Clue cannot be found");
        else
            System.out.println(clue.getClueNum() + ": " + clue.getFirstName());
    }

    private static void submitAnswer() throws FileNotFoundException {
        Clue clue = clueStatsInput();
        if (clue == null) {
            System.out.println("Clue cannot be found");
            return;
        }
        input.nextLine();
        String response;
        do {
            System.out.println("Enter Your Response");
            response = input.nextLine();
            if (response.length() != clue.getLastName().length())
                System.out.println("Response length does not equal clue length");
        } while (response.length() != clue.getLastName().length());

        int x = clue.getX();
        int y = clue.getY();
        char[] letters = response.toCharArray();
        if (clue.getDirection() == 'h')
            for (int i = 0; i < response.length(); i++)
                puzzle[x][y + i] = letters[i];
        else if (clue.getDirection() == 'v')
            for (int i = 0; i < response.length(); i++)
                puzzle[x + i][y] = letters[i];
    }

    private static void eraseAnswer() throws FileNotFoundException {
        Clue clue = clueStatsInput();
        if (clue == null) {
            System.out.println("Clue cannot be found");
            return;
        }

        int x = clue.getX();
        int y = clue.getY();
        puzzle[x][y] = Integer.toString(clue.getClueNum()).toCharArray()[0];
        if (clue.getDirection() == 'h')
            for (int i = 1; i < clue.getLastName().length(); i++)
                puzzle[x][y + i] = '.';
        else if (clue.getDirection() == 'v')
            for (int i = 1; i < clue.getLastName().length(); i++)
                puzzle[x + i][y] = '.';
    }

    private static boolean submitBoard() throws IOException {
        Square[][] squarePuzzle = Crossword.showBoard(puzzle, boolPuzzle);
        char[][] answerBoard = Utils.getStringBoard(true);
        for (int i = 0; i < answerBoard.length; i++)
            for (int j = 0; j < answerBoard[i].length; j++)
                if (Character.toUpperCase(answerBoard[i][j]) != Character.toUpperCase(squarePuzzle[i][j].getContent()))
                    return false;
        return true;
    }
}