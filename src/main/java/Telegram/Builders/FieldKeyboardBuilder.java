package Telegram.Builders;

import DataBase.Models.Turn.TurnServiceImpl;
import TicTacToe.Field;
import TicTacToe.Turn;
import Utils.ConfigurationLoader;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class FieldKeyboardBuilder implements KeyboardBuilder {
    private static final int ROWS_COUNT = 4;
    private boolean isCurrentPlayer;

    public FieldKeyboardBuilder(boolean isCurrentPlayer) {
        this.isCurrentPlayer = isCurrentPlayer;
    }

    @Override
    public InlineKeyboardMarkup build(Update update) {
        Long chatId = update.hasCallbackQuery() ?
                update.getCallbackQuery().getMessage().getChatId() :
                update.getMessage().getChatId();
        Field field = new TurnServiceImpl().getFieldForUserId(chatId);

        List<List<InlineKeyboardButton>> keyboard = generateKeyboard();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonText = getButtonText(field, i, j);
                String buttonCallback = getButtonCallback(field, i, j);
                keyboard.get(i).add(
                        new InlineKeyboardButton()
                                .setText(buttonText)
                                .setCallbackData(buttonCallback)
                );
            }
        }

        keyboard.get(3).add(
                new InlineKeyboardButton()
                        .setText("Сдаться.")
                        .setCallbackData(ConfigurationLoader.getInstance().getProperty("callback.surrender"))
        );

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        System.out.println(keyboard.toString());
        return markup;
    }

    private List<List<InlineKeyboardButton>> generateKeyboard() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (int i = 0; i < FieldKeyboardBuilder.ROWS_COUNT; i++) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            keyboard.add(row);
        }

        return keyboard;
    }

    private String getButtonText(Field field, int i, int j) {
        return field.getCell(i, j).getSymbol();
    }

    private String getButtonCallback(Field field, int i, int j) {
        String nullCallback = ConfigurationLoader.getInstance().getProperty("callback.null");

        if (!isCurrentPlayer) {
            return nullCallback;
        }
        StringBuilder sb = new StringBuilder();

        if (field.getCell(i, j) == Turn.EMPTY) {
            sb.append(i);
            sb.append(j);
        } else {
            sb.append(nullCallback);
        }

        return sb.toString();
    }
}
