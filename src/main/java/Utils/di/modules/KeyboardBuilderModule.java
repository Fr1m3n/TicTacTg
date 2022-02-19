package Utils.di.modules;

import Telegram.Builders.*;
import Utils.di.annotations.KeyboardBuilders;
import Utils.di.factory.FieldKeyboardBuilderFactory;
import com.google.inject.AbstractModule;

public class KeyboardBuilderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(KeyboardBuilder.class)
                .annotatedWith(KeyboardBuilders.Help.class)
                .to(HelpKeyboardBuilder.class);

        bind(KeyboardBuilder.class)
                .annotatedWith(KeyboardBuilders.Menu.class)
                .to(MenuKeyboardBuilder.class);

        bind(KeyboardBuilder.class)
                .annotatedWith(KeyboardBuilders.NewGame.class)
                .to(NewGameKeyboardBuilder.class);
    }
}
