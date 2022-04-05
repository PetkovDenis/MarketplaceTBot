package ru.ws.marketplace.handler.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ws.marketplace.handler.button.ButtonClickHandler;
import ru.ws.marketplace.state.dialog.DialogueContext;

@Component
public class HandleIncomingMessageService {

    private final DialogueContext context = new DialogueContext();
    private final MessageHandler messageHandler;
    private final ButtonClickHandler callbackHandler;

    private BotApiMethod<?> replyMessage;

    public HandleIncomingMessageService(MessageHandler messageHandler, ButtonClickHandler callbackHandler) {
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
    }

    public BotApiMethod<?> handleUpdate(Update update) {

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackHandler.handleCallback(callbackQuery, context);
        }

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            replyMessage = messageHandler.sortedMessage(message, context);
        } else {
            replyMessage = new SendMessage(update.getMessage().getChatId().toString(), " Сообщения принято на обработку");
        }
        return replyMessage;
    }
}
