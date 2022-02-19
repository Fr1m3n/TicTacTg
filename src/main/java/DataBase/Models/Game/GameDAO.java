package DataBase.Models.Game;

import DataBase.Models.User.UserRecord;
import TicTacToe.GameState;

import java.util.List;

public interface GameDAO {
    void save(GameRecord game);
    List<GameRecord> getByPlayer(UserRecord user);
    GameRecord getByPlayerAndState(UserRecord user, GameState state);
    GameRecord  getByInviteCode(String inviteCode);
    Long getTurnsCount(GameRecord game);
    void update(GameRecord game);
}
