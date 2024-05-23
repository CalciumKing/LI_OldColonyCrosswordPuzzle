import java.io.FileNotFoundException;

public class Crossword {
    public static Square[][] showBoard(char[][] puzzle, boolean[][] boolPuzzle) throws FileNotFoundException {
        Square[][] squares = new Square[12][12];

        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                Square square;
                if (boolPuzzle[i][j])
                    square = new Square(puzzle[i][j]);
                else
                    square = new Square('*');
                squares[i][j] = square;
            }
        }
        return squares;
    }
}