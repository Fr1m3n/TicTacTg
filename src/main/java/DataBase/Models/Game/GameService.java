package DataBase.Models.Game;

import DataBase.Models.User.UserRecord;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;

public interface GameService {
    GameRecord createNewGame(UserRecord user);

    GameRecord getCurrentGameForUserId(Long id);

    void addPlayerToGame(GameRecord game, UserRecord user);

    List<SendMessage> startGame(GameRecord game, Update update);

    GameRecord getByInviteCode(String inviteCode);

    String generateInviteCode();

    boolean isCurrentPlayer(GameRecord game, UserRecord user);

    void endGame(GameRecord game, UserRecord winner);

    void save(GameRecord record);

    void update(GameRecord record);
}
