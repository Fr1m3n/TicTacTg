package DataBase.Models.Turn;

import TicTacToe.Field;

public interface TurnService {
    Field getFieldForUserId(Long userId);

    Field doTurn(Long userId, int i, int j);
}
