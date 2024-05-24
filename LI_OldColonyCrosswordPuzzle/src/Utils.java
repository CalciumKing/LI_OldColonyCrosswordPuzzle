import java.io.*;
import java.util.*;

public class Utils {
    // Private static variables that will be used throughout this file and nowhere else
    private static final String FILENAME = "src\\clues.txt";
    private static final String DELIMITER = ":";
    private static final File userData = new File(FILENAME);

    public static char[][] GetStringBoard(boolean showAnswer) throws IOException {
        // Initializing a 12x12 array of characters representing the crossword puzzle
        // and filling it with * by default
        char[][] puzzle = new char[12][12];
        for (char[] strings : puzzle)
            Arrays.fill(strings, '*');

        ExistCheck();

        // FileReader will allow program to read file and nothing more
        // and scanner will take the readable file as input
        FileReader fileReader = new FileReader(userData);
        Scanner inputFile = new Scanner(fileReader);
        while (inputFile.hasNextLine()) {
            // Splitting each line in the file to separate strings by the delimiter
            String[] splitLine = inputFile.nextLine().split(DELIMITER);

            // Getting the x and y coordinates of the beginning of each name
            // Turning the last name into an array of characters
            // Setting the spot where the first letter would be to a number like in crossword puzzles
            int x = Integer.parseInt(splitLine[0]);
            int y = Integer.parseInt(splitLine[1]);
            char[] nameChars = splitLine[4].toCharArray();
            puzzle[x][y] = splitLine[3].toCharArray()[0];

            // Checking if the program should show the finished board, or just a blank one
            // Blank board is used in the beginning of the program to make a blank board
            // Finished board is used to check if the players completed board is correct
            if (!showAnswer) {
                if (splitLine[2].equalsIgnoreCase("h"))
                    for (int i = 1; i < nameChars.length; i++)
                        puzzle[x][y + i] = '.';
                else if (splitLine[2].equalsIgnoreCase("v"))
                    for (int i = 1; i < nameChars.length; i++)
                        puzzle[x + i][y] = '.';
            } else {
                if (splitLine[2].equalsIgnoreCase("h"))
                    for (int i = 0; i < nameChars.length; i++)
                        puzzle[x][y + i] = nameChars[i];
                else if (splitLine[2].equalsIgnoreCase("v"))
                    for (int i = 0; i < nameChars.length; i++)
                        puzzle[x + i][y] = nameChars[i];
            }
        }
        // Closing everything and returning the finished or blank puzzle
        inputFile.close();
        fileReader.close();
        return puzzle;
    }

    private static void ExistCheck() {
        // Nothing will work if the file doesn't exist, this checks for that
        if (!userData.exists()) {
            System.out.println("File Not Found, Cannot Build Board");
            System.exit(0);
        }
    }

    public static ArrayList<Clue> GetClues() throws FileNotFoundException {
        // Declaring an empty arraylist that will be used to store all the clues
        ArrayList<Clue> clues = new ArrayList<>();
        ExistCheck();

        // Same concept as file reading and input as GetStringBoard method
        FileReader fileReader = new FileReader(userData);
        Scanner inputFile = new Scanner(fileReader);
        while (inputFile.hasNextLine()) {
            String[] splitLine = inputFile.nextLine().split(DELIMITER);
            char[] characters = splitLine[2].toCharArray();
            // Creates a new clue object with the information given
            // from each item in the clues.txt file
            Clue clue = new Clue(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1]), characters[0], Integer.parseInt(splitLine[3]), splitLine[5], splitLine[4]);
            clues.add(clue);
        }
        // Returning the ArrayList of all the clues to be used in other parts of the program
        return clues;
    }
}