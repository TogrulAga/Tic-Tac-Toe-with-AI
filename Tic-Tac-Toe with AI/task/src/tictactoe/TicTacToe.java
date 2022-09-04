package tictactoe;

import java.util.Scanner;

public class TicTacToe {
    static Scanner scanner = new Scanner(System.in);
    private Board board;
    private Player playerX;
    private Player playerO;

    public TicTacToe() {
        board = new Board();

    }

    public void run() {
        selectMode();

        System.out.println(board);

        while (!board.gameFinished()) {
            if (board.nextMove().equals("X")) {
                playerX.move(board);
            } else {
                playerO.move(board);
            }

            System.out.println(board);
        }

        if (board.checkXWon()) {
            System.out.println("X wins");
        } else if (board.checkOWon()) {
            System.out.println("O wins");
        } else if (board.checkDraw()) {
            System.out.println("Draw");
        } else {
            System.out.println("Game not finished");
        }

        board = new Board();
    }

    private void selectMode() {
        while (true) {
            System.out.print("Input command: ");
            String[] input = scanner.nextLine().split(" ");

            if ("exit".equals(input[0])) {
                System.exit(0);
            }

            if (input.length != 3) {
                System.out.println("Bad parameters!");
                continue;
            } else if (!"start".equals(input[0])) {
                System.out.println("Bad parameters!");
                continue;
            }

            playerX = PlayerFactory.createPlayer("X", input[1]);
            playerO = PlayerFactory.createPlayer("O", input[2]);
            break;
        }
    }
}
