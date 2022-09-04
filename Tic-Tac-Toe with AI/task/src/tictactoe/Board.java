package tictactoe;

import java.util.Arrays;

public class Board {
    private final String[][] board = new String[3][3];

    public Board() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = " ";
            }
        }
    }

    @Override
    public String toString() {
        // Upper boundary
        StringBuilder string = new StringBuilder();
        string.append("---------\n");

        // Rows and columns
        for (int i = 0; i < 3; i++) {
            string.append("| ");
            for (int j = 0; j < 3; j++) {
                string.append(String.format("%s ", board[i][j]));
            }
            string.append("|\n");
        }

        // Lower boundary
        string.append("---------\n");

        return string.toString();
    }

    public boolean gameFinished() {
        boolean hasXWon = checkXWon();
        boolean hasOWon = checkOWon();
        boolean isDraw = checkDraw();

        return hasXWon || hasOWon || isDraw;
    }

    public boolean checkDraw() {
        return getCountEmptyCells() == 0;
    }

    private int getCountEmptyCells() {
        int countEmptyCells = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals(" ")) {
                    countEmptyCells++;
                }
            }
        }
        return countEmptyCells;
    }

    public boolean checkXWon() {
        return checkSymbolWon("X");
    }

    public boolean checkOWon() {
        return checkSymbolWon("O");
    }

    private boolean checkSymbolWon(String symbol) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (symbol.equals(board[i][0]) && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2])) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (symbol.equals(board[0][j]) && board[0][j].equals(board[1][j]) && board[1][j].equals(board[2][j])) {
                return true;
            }
        }

        //Check main and side diagonals
        if (symbol.equals(board[0][0]) && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) {
            return true;
        } else return symbol.equals(board[0][2]) && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]);
    }

    public String nextMove() {
        int countX = 0;
        int countO = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("X")) {
                    countX++;
                } else if (board[i][j].equals("O")) {
                    countO++;
                }
            }
        }

        return countX <= countO ? "X" : "O";
    }

    /**
     * Checks if the cell with given row and column number is occupied
     * @param row - 1 indexed
     * @param column - 1 indexed
     * @return boolean value returned
     */
    public boolean isCellOccupied(int row, int column) {
        return !board[row - 1][column - 1].equals(" ");
    }

    /**
     * Sames as @isCellOccupied but indices start from 0
     * @param row - 0 indexed
     * @param column - 0 indexed
     * @return boolean value returned
     */
    public boolean isCellOccupied0Indexed(int row, int column) {
        return isCellOccupied(row + 1, column + 1);
    }

    public void markBoard(int row, int column) {
        board[row - 1][column - 1] = nextMove();
    }

    public void markBoard(int[] cell) {
        markBoard(cell[0] + 1, cell[1] + 1);
    }

    public int[][] getEmptyCells() {
        int[][] emptyCells = new int[getCountEmptyCells()][2];

        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals(" ")) {
                    emptyCells[count][0] = i;
                    emptyCells[count][1] = j;
                    count++;
                }
            }
        }

        return emptyCells;
    }

    public int[] checkTwoInARow(String mark) {
        // Check 2 on the same row
        for (int i = 0; i < 3; i++) {
            int count = 0;
            int emptyCell = -1;
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals(mark)) {
                    count++;
                    continue;
                }

                if (!isCellOccupied0Indexed(i, j)) {
                    emptyCell = j;
                }
            }

            if (count == 2 && emptyCell != -1) {
                return new int[] {i, emptyCell};
            }
        }

        // Check 2 in a row in columns
        for (int i = 0; i < 3; i++) {
            int count = 0;
            int emptyCell = -1;
            for (int j = 0; j < 3; j++) {
                if (board[j][i].equals(mark)) {
                    count++;
                    continue;
                }

                if (!isCellOccupied0Indexed(j, i)) {
                    emptyCell = j;
                }
            }

            if (count == 2 && emptyCell != -1) {
                return new int[] {emptyCell, i};
            }
        }

        // Check main diagonal
        int count = 0;
        int[] emptyCell = new int[] {-1, -1};
        int[][] mainDiagonal = new int[][] {{0, 0}, {1, 1}, {2, 2}};
        for (int i = 0; i < 3; i++) {
            emptyCell = new int[] {-1, -1};
            if (board[mainDiagonal[i][0]][mainDiagonal[i][1]].equals(mark)) {
                count++;
                continue;
            }

            if (!isCellOccupied0Indexed(mainDiagonal[i][0], mainDiagonal[i][1])) {
                emptyCell = mainDiagonal[i];
            }
        }

        if (count == 2 && !Arrays.equals(emptyCell, new int[]{-1, -1})) {
            return emptyCell;
        }

        // Check side diagonal
        count = 0;
        emptyCell = new int[] {-1, -1};
        int[][] sideDiagonal = new int[][] {{0, 2}, {1, 1}, {2, 0}};
        for (int i = 0; i < 3; i++) {
            emptyCell = new int[] {-1, -1};
            if (board[sideDiagonal[i][0]][sideDiagonal[i][1]].equals(mark)) {
                count++;
                continue;
            }
            if (!isCellOccupied0Indexed(sideDiagonal[i][0], sideDiagonal[i][1])) {
                emptyCell = sideDiagonal[i];
            }
        }

        if (count == 2 && !Arrays.equals(emptyCell, new int[]{-1, -1})) {
            return emptyCell;
        }

        return null;
    }

    public boolean isMovesLeft() {
        return getCountEmptyCells() > 0;
    }

    public void undoMove(int i, int j) {
        board[i][j] = " ";
    }
}
