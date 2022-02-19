package Telegram.Handlers.Callback;

import DataBase.Models.Game.GameRecord;
import DataBase.Models.Game.GameService;
import DataBase.Models.User.UserRecord;
import DataBase.Models.User.UserService;
import Telegram.Handlers.Handler;
import com.google.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class SurrenderHandler implements Handler {
    private static final String MESSAGE_TEXT = "#SURRENDER_TEXT";

    private final UserService userService;
    private final GameService gameService;

    @Inject
    public SurrenderHandler(
            UserService userService,
            GameService gameService
    ) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public List<SendMessage> handle(Update update) {
        UserRecord user = userService.getUserOrCreate(update.getCallbackQuery().getMessage().getChatId());
        GameRecord game = user.getCurrentGame();
        UserRecord anotherUser = game.getAnotherPlayer(user);

        gameService.endGame(game, anotherUser);

        List<SendMessage> res = new ArrayList<>();
        res.add(new SendMessage()
                .setText("YOU LOSE!")
                .setChatId(user.getId())
        );
        res.add(new SendMessage()
                .setText("YOU WIN! ENEMY SURRENDER")
                .setChatId(anotherUser.getId())
        );

        return res;
    }
}
