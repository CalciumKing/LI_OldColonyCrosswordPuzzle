import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Utils {
    private static final String FILENAME = "src\\clues.txt";
    private static final String DELIMITER = ":";
    public static char[][] getStringBoard() throws FileNotFoundException {
        char[][] puzzle = new char[12][12];
        for (char[] strings : puzzle) {
            Arrays.fill(strings, '*');
        }

        File userData = new File(FILENAME);
        if(!userData.exists()) {
            System.out.println("File Not Found, Cannot Build Board");
            System.exit(0);
        }
        
        Scanner fileReader = new Scanner(userData);
        while(fileReader.hasNextLine()) {
            String[] splitLine = fileReader.nextLine().split(DELIMITER);

            int x = Integer.parseInt(splitLine[0]);
            int y = Integer.parseInt(splitLine[1]);
            String[] name = splitLine[4].split("");

            puzzle[x][y] = splitLine[3].toCharArray()[0];
            // puzzle[x][y] = name[i];
            if(splitLine[2].equalsIgnoreCase("h"))
                for (int i = 1; i < name.length; i++)
                    puzzle[x][y + i] = '.';
            else if(splitLine[2].equalsIgnoreCase("v"))
                for (int i = 1; i < name.length; i++)
                    puzzle[x + i][y] = '.';
        }
        fileReader.close();
        return puzzle;
    }
    public static ArrayList<Clue> getClues() throws FileNotFoundException {
        ArrayList<Clue> clues = new ArrayList<>();
        File userData = new File(FILENAME);
        if(!userData.exists()) {
            System.out.println("File Not Found, Cannot Build Board");
            System.exit(0);
        }

        Scanner fileReader = new Scanner(userData);
        while(fileReader.hasNextLine()) {
            String[] splitLine = fileReader.nextLine().split(DELIMITER);
            char[] characters = splitLine[2].toCharArray();
            Clue clue = new Clue(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1]), characters[0], Integer.parseInt(splitLine[3]), splitLine[4], splitLine[5]);
            clues.add(clue);
        }
        return clues;
    }
}