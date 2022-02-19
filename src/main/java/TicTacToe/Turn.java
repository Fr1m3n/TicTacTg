package TicTacToe;

public enum Turn {
    O("O"),
    X("X"),
    EMPTY(".");

    private String symbol;
    Turn(String s) {
        this.symbol = s;
    }
    public String getSymbol() {
        return symbol;
    }

}
