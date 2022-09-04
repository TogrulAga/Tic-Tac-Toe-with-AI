package tictactoe;

import java.util.Random;

public class AI extends Player{
    private final String mode;

    public AI(String mark, String mode) {
        super(mark);
        this.mode = mode;
    }

    @Override
    public void move(Board board) {
        System.out.printf("Making move level \"%s\"%n", mode);

        switch (mode) {
            case "easy":
                moveEasy(board);
                break;
            case "medium":
                moveMedium(board);
                break;
            case "hard":
                moveHard(board);
                break;
        }
    }

    private void moveEasy(Board board) {
        int[][] emptyCells = board.getEmptyCells();

        Random random = new Random();
        int[] randomCell = emptyCells[random.nextInt(emptyCells.length)];

        board.markBoard(randomCell);
    }

    private void moveMedium(Board board) {
        // Play opponent's next best move if it has two in a row
        int[] opponentBestMove = board.checkTwoInARow(mark.equals("X") ? "O" : "X");
        if (opponentBestMove != null) {
            board.markBoard(opponentBestMove);
            return;
        }

        // If there is a winning move
        int[] emptyCell = board.checkTwoInARow(mark);
        if (emptyCell != null) {
            board.markBoard(emptyCell);
            return;
        }

        moveEasy(board);
    }

    private void moveHard(Board board) {
        int[] bestMove = findBestMove(board);

        board.markBoard(bestMove);
    }

    private int[] findBestMove(Board board) {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!board.isCellOccupied0Indexed(i, j)) {
                    board.markBoard(i + 1, j + 1);

                    int moveVal = minimax(board, 0, false);

                    board.undoMove(i, j);

                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        return bestMove;
    }

    private int minimax(Board board, int depth, Boolean isMax) {
        int score = evaluate(board);

        if (score == 10) {
            return score;
        } else if (score == -10) {
            return score;
        } else if (!board.isMovesLeft()) {
            return 0;
        }

        int best;
        if (isMax) {
            best = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (!board.isCellOccupied0Indexed(i, j)) {
                        board.markBoard(i + 1, j + 1);

                        best = Math.max(best, minimax(board, depth + 1, false));

                        board.undoMove(i, j);
                    }
                }
            }

        } else {
            best = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (!board.isCellOccupied0Indexed(i, j)) {
                        board.markBoard(i + 1, j + 1);

                        best = Math.min(best, minimax(board, depth + 1, true));

                        board.undoMove(i, j);
                    }
                }
            }
        }

        return best;
    }

    private int evaluate(Board board) {
        if ("X".equals(mark)) {
            if (board.checkXWon()) {
                return 10;
            } else if (board.checkOWon()) {
                return -10;
            } else if (board.checkDraw()) {
                return 0;
            }
        } else if ("O".equals(mark)) {
            if (board.checkXWon()) {
                return -10;
            } else if (board.checkOWon()) {
                return 10;
            } else if (board.checkDraw()) {
                return 0;
            }
        }

        return 0;
    }
}
