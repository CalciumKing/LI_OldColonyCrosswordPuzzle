public class Clue {
    private final int x;
    private final int y;
    private final char direction;
    private final int clueNum;
    private final String firstName;
    private final String lastName;

    // A constructor creating a Clue object with all the necessary information
    public Clue(int x, int y, char direction, int clueNum, String firstName, String lastName) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.clueNum = clueNum;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters to allow all files to access all the Clue private variables
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public char getDirection() {
        return direction;
    }
    public int getClueNum() {
        return clueNum;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
}