package Telegram.Handlers.Message;

import DataBase.Models.Game.GameRecord;
import DataBase.Models.Game.GameService;
import DataBase.Models.Game.GameServiceImpl;
import DataBase.Models.User.UserRecord;
import DataBase.Models.User.UserService;
import DataBase.Models.User.UserServiceImpl;
import Telegram.Builders.MenuKeyboardBuilder;
import Telegram.Handlers.Callback.HelpHandler;
import Telegram.Handlers.Handler;
import Telegram.Builders.FieldKeyboardBuilder;
import com.google.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class MessageHandler implements Handler {
    private final UserService userService;
    private final GameService gameService;

    @Inject
    public MessageHandler(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    public List<SendMessage> handle(Update update) {
        UserRecord user = userService.getUserOrCreate(update.getMessage().getChatId());
        String message = update.getMessage().getText();
        List<SendMessage> res = new ArrayList<>();
        if (message.equals("/start")) {
            res.add(
                    new SendMessage()
                            .setText(HelpHandler.MESSAGE_TEXT)
                            .setReplyMarkup(new MenuKeyboardBuilder().build(update))
                            .setChatId(user.getId())
            );

        } else if (user.getCurrentGame() != null) {
            res.add(
                    new SendMessage()
                            .setText("Чей-то ход...")
                            .setReplyMarkup(new FieldKeyboardBuilder(true).build(update))
                            .setChatId(user.getId())
            );
        } else {
            GameRecord game = handleInviteCode(update, message);
            res.addAll(gameService.startGame(game, update));
        }
        return res;
    }

    private GameRecord handleInviteCode(Update update, String code) {
        UserRecord user = userService.getUserOrCreate(update.getMessage().getChatId());
        GameRecord game = gameService.getByInviteCode(code);
        gameService.addPlayerToGame(game, user);
        return game;
    }

}
