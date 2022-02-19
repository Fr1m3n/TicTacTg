package DataBase.Models.Game;

import DataBase.Models.User.UserDAO;
import DataBase.Models.User.UserDAOImpl;
import DataBase.Models.User.UserRecord;
import Telegram.Builders.FieldKeyboardBuilder;
import TicTacToe.GameState;
import Utils.ConfigurationLoader;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.games.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class GameServiceImpl implements GameService {
    private final GameDAO gameDAO;
    private final UserDAO userDAO;

    public GameServiceImpl() {
        this.gameDAO = new GameDAOImpl();
        this.userDAO = new UserDAOImpl();
    }

    @Override
    public GameRecord createNewGame(UserRecord user) {
        GameRecord gameRecord = new GameRecord(user, GameState.STARTING);
        gameDAO.save(gameRecord);
        gameRecord = gameDAO.getByPlayerAndState(user, GameState.STARTING);
        user.setCurrentGame(gameRecord);
        userDAO.update(user);
        return gameRecord;
    }

    @Override
    public GameRecord getCurrentGameForUserId(Long id) {
        return userDAO.getById(id).getCurrentGame();
    }

    @Override
    public void addPlayerToGame(GameRecord game, UserRecord user) {
        game.setSecondPlayer(user);
        Random random = new Random();
        user.setCurrentGame(game);
        userDAO.update(user);
        // Рандом свап игроков
        if (random.nextBoolean()) {
            UserRecord user1 = game.getFirstPlayer();
            game.setFirstPlayer(game.getSecondPlayer());
            game.setSecondPlayer(user1);
        }
        gameDAO.update(game);
    }

    @Override
    public List<SendMessage> startGame(GameRecord game, Update update) {
        game.setState(GameState.RUNNING);
        gameDAO.update(game);

        List<SendMessage> res = new ArrayList<>();
        res.add(
                new SendMessage()
                        .setChatId(game.getFirstPlayer().getId())
                        .setText("@") // TODO: 04.04.2021 TEXT
                        .setReplyMarkup(
                                new FieldKeyboardBuilder(
                                        isCurrentPlayer(game, game.getFirstPlayer())
                                ).build(update)
                        )
        );
        res.add(
                new SendMessage()
                        .setChatId(game.getSecondPlayer().getId())
                        .setText("@") // TODO: 04.04.2021 TEXT
                        .setReplyMarkup(
                                new FieldKeyboardBuilder(
                                        isCurrentPlayer(game, game.getSecondPlayer())
                                ).build(update)
                        )
        );
        return res;
    }

    @Override
    public GameRecord getByInviteCode(String inviteCode) {
        return gameDAO.getByInviteCode(inviteCode);
    }

    @Override
    public String generateInviteCode() {
        Random rng = new Random();
        int length = Integer.parseInt(ConfigurationLoader.getInstance().getProperty("game.invite_code_length"));
        String characters = ConfigurationLoader.getInstance().getProperty("game.characters_for_invite_code");
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    @Override
    public boolean isCurrentPlayer(GameRecord game, UserRecord user) {
        return (gameDAO.getTurnsCount(game) % 2 == 0) == game.getFirstPlayer().getId().equals(user.getId());
    }

    @Override
    public void endGame(GameRecord game, UserRecord winner) {
        game.setWinner(winner);
        game.setState(GameState.FINISHED);
        gameDAO.update(game);

        game.getFirstPlayer().setCurrentGame(null);
        game.getSecondPlayer().setCurrentGame(null);
        userDAO.update(game.getSecondPlayer());
        userDAO.update(game.getFirstPlayer());
    }

    @Override
    public void save(GameRecord record) {
        gameDAO.save(record);
    }

    @Override
    public void update(GameRecord record) {
        gameDAO.update(record);
    }
}
