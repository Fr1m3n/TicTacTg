package Telegram.Builders;

import Utils.ConfigurationLoader;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MenuKeyboardBuilder implements KeyboardBuilder {

    @Override
    public InlineKeyboardMarkup build(Update update) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        keyboard.add(row);
        row.add(
                new InlineKeyboardButton()
                        .setText("Создать игру")
                        .setCallbackData(ConfigurationLoader.getInstance().getProperty("callback.new_game"))
        );
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        return markup;
    }
}
