import java.io.*;
import java.util.*;

public class Utils {
    private static final String FILENAME = "src\\clues.txt";
    private static final String DELIMITER = ":";
    private static final File userData = new File(FILENAME);

    public static char[][] getStringBoard(boolean showAnswer) throws IOException {
        char[][] puzzle = new char[12][12];
        for (char[] strings : puzzle)
            Arrays.fill(strings, '*');

        if (!userData.exists()) {
            System.out.println("File Not Found, Cannot Build Board");
            System.exit(0);
        }
        FileReader fileReader = new FileReader(userData);
        Scanner inputFile = new Scanner(fileReader);
        while (inputFile.hasNextLine()) {
            String[] splitLine = inputFile.nextLine().split(DELIMITER);

            int x = Integer.parseInt(splitLine[0]);
            int y = Integer.parseInt(splitLine[1]);
            char[] nameChars = splitLine[4].toCharArray();
            puzzle[x][y] = splitLine[3].toCharArray()[0];
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
        inputFile.close();
        fileReader.close();
        return puzzle;
    }

    public static ArrayList<Clue> getClues() throws FileNotFoundException {
        ArrayList<Clue> clues = new ArrayList<>();
        if (!userData.exists()) {
            System.out.println("File Not Found, Cannot Build Board");
            System.exit(0);
        }

        Scanner fileReader = new Scanner(userData);
        while (fileReader.hasNextLine()) {
            String[] splitLine = fileReader.nextLine().split(DELIMITER);
            char[] characters = splitLine[2].toCharArray();
            Clue clue = new Clue(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1]), characters[0], Integer.parseInt(splitLine[3]), splitLine[5], splitLine[4]);
            clues.add(clue);
        }
        return clues;
    }
}