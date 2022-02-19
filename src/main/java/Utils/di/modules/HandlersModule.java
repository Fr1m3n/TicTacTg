package Utils.di.modules;

import Telegram.Handlers.Callback.*;
import Telegram.Handlers.Handler;
import Telegram.Handlers.Message.MessageHandler;
import Utils.di.annotations.Handlers;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class HandlersModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Handler.class)
                .annotatedWith(Handlers.Help.class)
                .to(HelpHandler.class)
                .in(Scopes.SINGLETON);

        bind(Handler.class)
                .annotatedWith(Handlers.Move.class)
                .to(MoveHandler.class)
                .in(Scopes.SINGLETON);

        bind(Handler.class)
                .annotatedWith(Handlers.Surrender.class)
                .to(SurrenderHandler.class)
                .in(Scopes.SINGLETON);

        bind(Handler.class)
                .annotatedWith(Handlers.NewGame.class)
                .to(NewGameHandler.class)
                .in(Scopes.SINGLETON);

        bind(Handler.class)
                .annotatedWith(Handlers.Menu.class)
                .to(MenuHandler.class)
                .in(Scopes.SINGLETON);

        bind(Handler.class)
                .annotatedWith(Handlers.Message.class)
                .to(MessageHandler.class)
                .in(Scopes.SINGLETON);

        bind(Handler.class)
                .annotatedWith(Handlers.Callback.class)
                .to(CallbackHandler.class)
                .in(Scopes.SINGLETON);
    }
}
