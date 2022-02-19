package Telegram.Handlers.Callback;

import Telegram.Builders.KeyboardBuilder;
import Telegram.Handlers.Handler;
import Utils.di.annotations.KeyboardBuilders;
import com.google.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class MenuHandler implements Handler {
    private static final String MESSAGE_TEXT = "#MENU_TEXT";
    private final KeyboardBuilder menuKeyboardBuilder;

    @Inject
    public MenuHandler(@KeyboardBuilders.Menu KeyboardBuilder menuKeyboardBuilder) {
        this.menuKeyboardBuilder = menuKeyboardBuilder;
    }

    @Override
    public List<SendMessage> handle(Update update) {
        List<SendMessage> res = new ArrayList<>();
        res.add(new SendMessage()
                .setText(MESSAGE_TEXT)
                .setReplyMarkup(
                        menuKeyboardBuilder.build(update)
                ));
        return res;
    }
}
