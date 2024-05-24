public class Square {
    // A very small class to make square objects that old content
    // and a getter to let other files access the only variable in the file
    private final char content;

    public Square(char content) {
        this.content = content;
    }

    public char getContent() {
        return content;
    }
}