package tictactoe;

public class PlayerFactory {
    public static Player createPlayer(String mark, String name) {
        switch (name) {
            case "user":
                return new User(mark);
            case "easy":
            case "medium":
            case "hard":
                return new AI(mark, name);
            default:
                return null;
        }
    }
}
