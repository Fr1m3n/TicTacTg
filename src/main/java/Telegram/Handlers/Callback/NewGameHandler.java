package Telegram.Handlers.Callback;

import DataBase.Models.Game.GameRecord;
import DataBase.Models.Game.GameService;
import DataBase.Models.Game.GameServiceImpl;
import DataBase.Models.User.UserRecord;
import DataBase.Models.User.UserService;
import DataBase.Models.User.UserServiceImpl;
import Telegram.Builders.KeyboardBuilder;
import Telegram.Builders.NewGameKeyboardBuilder;
import Telegram.Handlers.Handler;
import Utils.di.annotations.KeyboardBuilders;
import com.google.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class NewGameHandler implements Handler {
    private static final String MESSAGE_TEXT = "#NEW_GAME_TEXT";

    private final GameService gameService;
    private final UserService userService;

    private final KeyboardBuilder newGameKeyboardBuilder;

    @Inject
    public NewGameHandler(
            GameService gameService,
            UserService userService,
            @KeyboardBuilders.NewGame KeyboardBuilder newGameKeyboardBuilder
    ) {
        this.gameService = gameService;
        this.userService = userService;
        this.newGameKeyboardBuilder = newGameKeyboardBuilder;
    }

    @Override
    public List<SendMessage> handle(Update update) {
        List<SendMessage> res = new ArrayList<>();
        res.add(new SendMessage()
                .setText(MESSAGE_TEXT + "\n" + generateMessage(update))
                .setReplyMarkup(
                        newGameKeyboardBuilder.build(update)
                )
                .setChatId(update.getCallbackQuery().getMessage().getChatId())
        );

        return res;
    }

    private String generateMessage(Update update) {
        UserRecord user = userService.getUserOrCreate(update.getCallbackQuery().getMessage().getChatId());

        GameRecord game = gameService.createNewGame(user);
        game.setInviteCode(gameService.generateInviteCode());
        gameService.update(game);

        return game.getInviteCode();
    }
}
