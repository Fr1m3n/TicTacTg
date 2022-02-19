package TicTacToe;

public class Field {
    private Turn[][] field = new Turn[3][];
    private int countOfFilledCells = 0;

    public Field() {
        for (int i = 0; i < 3; i++) {
            field[i] = new Turn[3];
            for (int j = 0; j < 3; j++) {
                field[i][j] = Turn.EMPTY;
            }
        }
    }

    public Turn getCell(int i, int j) {
        return field[i][j];
    }

    public Turn doTurn(int i, int j) {
        if (field[i][j] != Turn.EMPTY) {
            return null;
        }
        Turn turn;
        if (countOfFilledCells % 2 == 0) {
            turn = Turn.O;
        } else {
            turn = Turn.X;
        }
        field[i][j] = turn;
        countOfFilledCells++;
        return turn;
    }

    public void setCell(int i, int j, Turn turn) {
        field[i][j] = turn;
        if (turn != Turn.EMPTY) countOfFilledCells++;
    }

}
