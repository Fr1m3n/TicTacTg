package DataBase.Models.Turn;

import DataBase.Models.Game.GameRecord;

import java.util.List;

public interface TurnDAO {
    void save(TurnRecord turn);
    List<TurnRecord> getByGame(GameRecord game);
}
