import java.io.FileNotFoundException;

public class Crossword {
    public static Square[][] showBoard(char[][] puzzle, boolean[][] boolPuzzle) throws FileNotFoundException {
        // Creating a crossword puzzle full of square objects using
        // the boolean puzzle to check if the spot can hold something or not
        // Then using a Ternary statement, it assigns something to a variable
        // similar to an if statement but in only one line
        Square[][] squares = new Square[puzzle.length][puzzle[0].length];
        for (int i = 0; i < puzzle.length; i++)
            for (int j = 0; j < puzzle[i].length; j++)
                squares[i][j] = (boolPuzzle[i][j]) ? new Square(puzzle[i][j]) : new Square('*');
        return squares;
    }
}