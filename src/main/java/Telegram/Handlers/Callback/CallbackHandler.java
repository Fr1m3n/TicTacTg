package Telegram.Handlers.Callback;

import Telegram.Handlers.Handler;
import Utils.ConfigurationLoader;
import Utils.di.annotations.Handlers;
import com.google.inject.Inject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CallbackHandler implements Handler {

    Map<String, Handler> handlers = new HashMap<>();
    private final Handler moveHandler;

    @Inject
    public CallbackHandler(
            @Handlers.Surrender Handler surrenderHandler,
            @Handlers.Help Handler helpHandler,
            @Handlers.Menu Handler menuHandler,
            @Handlers.NewGame Handler newGameHandler,
            @Handlers.Move Handler moveHandler
    ) {
        handlers.put(
                ConfigurationLoader.getInstance().getProperty("callback.surrender"),
                surrenderHandler
        );
        handlers.put(
                ConfigurationLoader.getInstance().getProperty("callback.help"),
                helpHandler
        );
        handlers.put(
                ConfigurationLoader.getInstance().getProperty("callback.menu"),
                menuHandler
        );
        handlers.put(
                ConfigurationLoader.getInstance().getProperty("callback.new_game"),
                newGameHandler
        );

        this.moveHandler = moveHandler;
    }

    @Override
    public List<SendMessage> handle(Update update) {
        List<SendMessage> sendMessage;
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callbackData = callbackQuery.getData();
        if (handlers.containsKey(callbackData)) {
            sendMessage = handlers.get(callbackData).handle(update);
        } else {
            sendMessage = moveHandler.handle(update);
        }

        return sendMessage;
    }


}
