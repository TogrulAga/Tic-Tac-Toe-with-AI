package tictactoe;

import java.util.Scanner;

public class User extends Player{

    public User(String mark) {
        super(mark);
    }

    @Override
    public void move(Board board) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter the coordinates:");

            int row;
            int column;

            try {
                String[] input = scanner.nextLine().split(" ");
                String rowString = input[0];
                String columnString = input[1];

                row = Integer.parseInt(rowString);
                column = Integer.parseInt(columnString);
            } catch (Exception e) {
                System.out.println("You should enter numbers!");
                continue;
            }

            if (row < 1 || row > 3 || column < 1 || column > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            if (board.isCellOccupied(row, column)) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            board.markBoard(row, column);

            break;
        }
    }
}
