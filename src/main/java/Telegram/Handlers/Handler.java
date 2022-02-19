package Telegram.Handlers;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;

public interface Handler {
    List<SendMessage> handle(Update update);
}
