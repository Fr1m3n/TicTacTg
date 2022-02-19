package Telegram;

import Telegram.Handlers.Callback.CallbackHandler;
import Telegram.Handlers.Handler;
import Telegram.Handlers.Message.MessageHandler;
import Utils.ConfigurationLoader;
import Utils.di.annotations.Handlers;
import Utils.di.modules.BasicModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Bot extends TelegramLongPollingBot {

    public static Bot bot;
    private final Handler messageHandler;
    private final Handler callbackHandler;

    @Inject
    public Bot(
            @Handlers.Message Handler messageHandler,
            @Handlers.Callback Handler callbackHandler
    ) {
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
    }

    public static Bot initBot() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        Injector injector = Guice.createInjector(new BasicModule());
        Bot bot = injector.getInstance(Bot.class);

        try {
            telegramBotsApi.registerBot(bot);
            log.info("Bot started successfully!");
        } catch (TelegramApiRequestException e) {
            log.error("Fail while connecting to telegram API", e);
        }
        Bot.bot = bot;
        return bot;
    }

    @Override
    public void onUpdateReceived(Update update) {
        List<SendMessage> messages = new ArrayList<>();
        if (update.hasMessage()) {
            messages = messageHandler.handle(update);
//            messages.set(0, messages.get(0).setChatId(update.getMessage().getChatId()));
        } else if (update.hasCallbackQuery()) {
            messages = callbackHandler.handle(update);
//            messages.set(0, messages.get(0).setChatId(update.getCallbackQuery().getMessage().getChatId()));

        }
        for (SendMessage message : messages) {
            if (message == null) {
                continue;
            }
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return ConfigurationLoader.getInstance().getProperty("bot.name");
    }

    @Override
    public String getBotToken() {
        return ConfigurationLoader.getInstance().getProperty("bot.token");
    }

}
