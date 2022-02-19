package Utils.di.factory;

import Telegram.Builders.FieldKeyboardBuilder;

public class FieldKeyboardBuilderFactory {

    public FieldKeyboardBuilder create(boolean isCurrentPlayer) {
        return new FieldKeyboardBuilder(isCurrentPlayer);
    }

}
