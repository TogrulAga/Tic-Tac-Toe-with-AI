package tictactoe;

public abstract class Player {
    protected String mark;

    public Player(String mark) {
        this.mark = mark;
    }
    public abstract void move(Board board);
}
