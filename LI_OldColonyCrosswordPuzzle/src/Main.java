/*

Name: Landen Ingerslev

Description: The program keeps asking the user for inputs such as displaying
all clues, displaying one clue, erasing an answer, submitting an answer,
checking the board for completion and if it's correct, and lastly it allows
the user to exit the program. Each input runs different methods in this file
and each method runs different files and methods in those files, information
about the puzzle is originated from the clues.txt file and user input is handled
only in the main class

*/

import java.io.*;
import java.util.Scanner;

public class Main {
    // Private static variables that will be used throughout this file and nowhere else
    private static final Scanner input = new Scanner(System.in);
    private static char[][] Puzzle = new char[0][0];
    private static final boolean[][] boolPuzzle = new boolean[12][12];

    public static void main(String[] args) throws IOException {
        // Assigning the puzzle variable using another file that returns a character array
        // If showAnswer parameter is false, its puzzle is an empty crossword puzzle ready to be filled.
        // And if showAnswer parameter is true, its puzzle is a fully completed
        // crossword puzzle, this is used in bug testing, and so I wouldn't need
        // to fill in the entire board to check one thing.
        Puzzle = Utils.GetStringBoard(false);

        // Fills in the boolean array to be used in other methods
        for (int i = 0; i < boolPuzzle.length; i++)
            for (int j = 0; j < boolPuzzle[i].length; j++)
                boolPuzzle[i][j] = Puzzle[i][j] != '*';

        boolean finishedBoard = false;
        PrintBoard();

        // Loop that goes until the user checks the board with a correct one, or the user exits the program
        do {
            System.out.println("\nWhat would you like to do?");
            System.out.println("Display All Clues (1), Display One Clue(2), Erase An Answer(3), Enter An Answer(4), Check Puzzle(5), or Exit Program(6)");

            // Advanced switch statement to handle all user inputs
            // Advanced is used instead of standard because all but one of the
            // inputs use a single line, it saves space
            switch (input.nextInt()) {
                case 1 -> DisplayClues();
                case 2 -> DisplayClue();
                case 3 -> EraseAnswer();
                case 4 -> SubmitAnswer();
                case 5 -> {
                    // Enters a message if the board is correct or not correct
                    finishedBoard = SubmitBoard();
                    if (!finishedBoard)
                        System.out.println("Your board does not match the answer board, keep trying");
                    else
                        System.out.println("Your board matches the answer board, you win!!!");
                }
                // Stops the program if the user enters 6
                case 6 -> System.exit(0);
                // If a number between 1-6 is not entered, it asks the user
                // to enter a valid number
                default -> System.out.println("Please Enter A Number 1-6");
            }
        // Loops as long as the board is not finished
        } while (!finishedBoard);
    }

    // Method to print the board, by getting content from individual squares in the crossword puzzle
    // This is used throughout this file to show the user the board after specific inputs
    private static void PrintBoard() throws FileNotFoundException {
        Square[][] squares = Crossword.showBoard(Puzzle, boolPuzzle);
        for (Square[] squares1 : squares) {
            for (Square square : squares1)
                System.out.print(square.getContent() + "\t");
            System.out.println();
        }
    }

    // Method to accept all inputs required from the user to make a Clue object
    // This is used throughout the program, so it is its own method
    private static Clue ClueStatsInput() throws FileNotFoundException {
        // Asks the user if the desired spot or clue is horizontal or vertical
        char dir;
        do {
            System.out.println("Enter Clue's Direction [h/v]");
            dir = input.next().charAt(0);
            if (dir != 'h' && dir != 'v')
                System.out.println("That is not a valid response, please enter [h/v]");
        } while (dir != 'h' && dir != 'v');

        // Asks the user what the desired spot or clue's number is
        int num;
        do {
            System.out.println("Enter Clue's Number");
            num = input.nextInt();
            if (num < 1 || num > Utils.GetClues().size())
                System.out.println("That is not a valid response, please enter a clue number");
        } while (num < 1 || num > Utils.GetClues().size());

        // Finds that clue using the information entered and returns it
        for (int i = 0; i < Utils.GetClues().size(); i++) {
            Clue clue = Utils.GetClues().get(i);
            if (clue.getClueNum() == num && clue.getDirection() == dir)
                return clue;
        }

        // If the clue is not returned, meaning it doesn't exist or cannot be
        // found, it displays a message and returns null (nothing)
        System.out.println("Clue cannot be found");
        return null;
    }

    // Method to display all clues using the Utils file to read off the
    // txt file and use the ArrayList of clues to get information
    // about each individual clue
    private static void DisplayClues() throws FileNotFoundException {
        System.out.println("\nHorizontal Clues:");
        for (int i = 0; i < Utils.GetClues().size(); i++) {
            Clue clue = Utils.GetClues().get(i);
            if (clue.getDirection() == 'h')
                System.out.println(clue.getClueNum() + ": " + clue.getFirstName());
        }
        System.out.println("\nVertical Clues:");
        for (int i = 0; i < Utils.GetClues().size(); i++) {
            Clue clue = Utils.GetClues().get(i);
            if (clue.getDirection() == 'v')
                System.out.println(clue.getClueNum() + ": " + clue.getFirstName());
        }
    }

    // Method to display a single clue using the Clue file
    private static void DisplayClue() throws FileNotFoundException {
        System.out.println("\nWhich Clue Would You Like To View?");
        Clue clue = ClueStatsInput();

        // If the clue returned exists, display the clue
        // If the clue does not exist, a message saying so has already
        // been displayed from the ClueStatsInput method
        if (clue != null)
            System.out.println(clue.getClueNum() + ": " + clue.getFirstName());
    }

    // Method to allow the user to enter an answer on the board
    private static void SubmitAnswer() throws FileNotFoundException {
        // Creates a clue using the return information from another method
        Clue clue = ClueStatsInput();
        if (clue == null)
            return;
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
                Puzzle[x][y + i] = letters[i];
        else if (clue.getDirection() == 'v')
            for (int i = 0; i < response.length(); i++)
                Puzzle[x + i][y] = letters[i];
        PrintBoard();
    }

    // Method to erase an answer from the board in case of a mistake
    private static void EraseAnswer() throws FileNotFoundException {
        // If the clue does not exist, an answer cannot be erased,
        // so it stops the method and returns to the main method
        Clue clue = ClueStatsInput();
        if (clue == null)
            return;

        // Getting the x and y coordinates of the beginning of each name
        // Turning the last name into an array of characters
        // Setting the spot where the first letter would be to a number like in crossword puzzles
        int x = clue.getX();
        int y = clue.getY();
        Puzzle[x][y] = Integer.toString(clue.getClueNum()).toCharArray()[0];
        if (clue.getDirection() == 'h')
            for (int i = 1; i < clue.getLastName().length(); i++)
                Puzzle[x][y + i] = '.';
        else if (clue.getDirection() == 'v')
            for (int i = 1; i < clue.getLastName().length(); i++)
                Puzzle[x + i][y] = '.';
        PrintBoard();
    }

    // Method to check if the board is finished using a fully completed board
    private static boolean SubmitBoard() throws IOException {
        Square[][] squarePuzzle = Crossword.showBoard(Puzzle, boolPuzzle);
        // Entering true on GetStringBoard returns a fully completed board
        char[][] answerBoard = Utils.GetStringBoard(true);

        // Checks each spot on the board, if one is not correct the
        // program returns false, meaning they are not the same
        for (int i = 0; i < answerBoard.length; i++)
            for (int j = 0; j < answerBoard[i].length; j++)
                if (Character.toUpperCase(answerBoard[i][j]) != Character.toUpperCase(squarePuzzle[i][j].getContent()))
                    return false;

        // If nothing is returned after the loops, the boards are the same and true is returned
        return true;
    }
}