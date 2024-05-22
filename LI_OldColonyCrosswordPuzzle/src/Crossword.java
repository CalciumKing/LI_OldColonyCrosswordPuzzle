import java.io.FileNotFoundException;

public class Crossword {
    public static void showBoard() throws FileNotFoundException {
        char[][] puzzle = Utils.getStringBoard();
        Square[][] squares = new Square[12][12];

        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                Square square;
                if (puzzle[i][j] != '*')
                    square = new Square(puzzle[i][j]);
                else
                    square = new Square('*');
                squares[i][j] = square;
            }
        }

        for(Square[] squares1 : squares) {
            for(Square square : squares1) {
                System.out.print(square.getContent() + "\t");
            }
            System.out.println();
        }
    }
}