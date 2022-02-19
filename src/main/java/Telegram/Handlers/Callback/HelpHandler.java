package Telegram.Handlers.Callback;

import Telegram.Builders.HelpKeyboardBuilder;
import Telegram.Builders.KeyboardBuilder;
import Telegram.Handlers.Handler;
import Utils.di.annotations.KeyboardBuilders;
import com.google.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class HelpHandler implements Handler {
    public static final String MESSAGE_TEXT = "#HELP_TEXT";
    private final KeyboardBuilder helpKeyboardBuilder;

    @Inject
    public HelpHandler(@KeyboardBuilders.Help KeyboardBuilder helpKeyboardBuilder) {
        this.helpKeyboardBuilder = helpKeyboardBuilder;
    }

    @Override
    public List<SendMessage> handle(Update update) {
        List<SendMessage> res = new ArrayList<>();
        res.add(new SendMessage()
                .setText(MESSAGE_TEXT)
                .setReplyMarkup(
                        helpKeyboardBuilder.build(update)
                )
                .setChatId(update.getCallbackQuery().getId())
        );
        return res;
    }
}
