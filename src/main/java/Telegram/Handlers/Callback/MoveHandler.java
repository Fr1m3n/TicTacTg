package Telegram.Handlers.Callback;

import DataBase.Models.Game.GameRecord;
import DataBase.Models.Game.GameService;
import DataBase.Models.Game.GameServiceImpl;
import DataBase.Models.Turn.TurnService;
import DataBase.Models.Turn.TurnServiceImpl;
import DataBase.Models.User.UserRecord;
import DataBase.Models.User.UserService;
import DataBase.Models.User.UserServiceImpl;
import Telegram.Builders.FieldKeyboardBuilder;
import Telegram.Builders.KeyboardBuilder;
import Telegram.Handlers.Handler;
import TicTacToe.Field;
import Utils.ConfigurationLoader;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MoveHandler implements Handler {
    private static final String MESSAGE_TEXT_CURRENT_PLAYER = "Твой ход";
    private static final String MESSAGE_TEXT_ANOTHER_PLAYER = "Жди хода!!!!";

    private final UserService userService;
    private final TurnService turnService;
    private final GameService gameService;

    @Inject
    public MoveHandler(
            UserService userService,
            TurnService turnService,
            GameService gameService
    ) {
        this.userService = userService;
        this.turnService = turnService;
        this.gameService = gameService;
    }

    @Override
    public List<SendMessage> handle(Update update) {
        UserRecord userRecord = userService.getUserOrCreate(update.getCallbackQuery().getMessage().getChatId());
        GameRecord game = userRecord.getCurrentGame();

        String data = update.getCallbackQuery().getData();

        List<SendMessage> res = new ArrayList<>();

        if (data.equals(ConfigurationLoader.getInstance().getProperty("callback.null"))) {
            return res;
        }

        int coordinateX = Integer.parseInt(data.substring(1, 2));
        int coordinateY = Integer.parseInt(data.substring(0, 1));

        log.info("Player with id {} did turn x: {} y: {}", userRecord.getId(), coordinateX, coordinateY);

        Field turn = turnService.doTurn(userRecord.getId(), coordinateY, coordinateX);
        if (turn == null) {
            return res;
        }

        boolean isCurrent = gameService.isCurrentPlayer(game, userRecord);
        res.add(new SendMessage()
                .setText(isCurrent ? MESSAGE_TEXT_CURRENT_PLAYER : MESSAGE_TEXT_ANOTHER_PLAYER)
                .setReplyMarkup(
                        new FieldKeyboardBuilder(isCurrent).build(update)
                )
                .setChatId(userRecord.getId())
        );
        res.add(new SendMessage()
                .setChatId(game.getAnotherPlayer(userRecord).getId())
                .setText(!isCurrent ? MESSAGE_TEXT_CURRENT_PLAYER : MESSAGE_TEXT_ANOTHER_PLAYER)
                .setReplyMarkup(
                        new FieldKeyboardBuilder(!isCurrent).build(update)
                )
        );

        return res;
    }
}
