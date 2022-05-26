package ru.ws.marketplace.handler.dialog;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ws.marketplace.init.button.ButtonInitialization;

@Component
@AllArgsConstructor
public class GreetingPerson {

    private final ButtonInitialization buttonInitialization = new ButtonInitialization();

    public BotApiMethod<?> greeting(Message message) {
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), "Здравствуйте!");
        sendMessage.setReplyMarkup(buttonInitialization.getDefaultKeyboard());
        return sendMessage;
    }
}

