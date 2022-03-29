package ru.ws.marketplace.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ws.marketplace.service.handler.CallbackHandler;
import ru.ws.marketplace.service.handler.MessageHandler;
import ru.ws.marketplace.state.*;

@Service
public class HandleIncomingMessageService {

    private final DialogueContext context = new DialogueContext();

    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;

    public HandleIncomingMessageService(MessageHandler messageHandler, CallbackHandler callbackHandler) {
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage;
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackHandler.handleCallback(callbackQuery, context);
        }
        Message message = update.getMessage();
        if (message != null && message.hasText() && !context.getStatusName().equals("END")) {
            replyMessage = messageHandler.handleInputMessage(message, context);
        } else {
            replyMessage = new SendMessage(update.getMessage().getChatId().toString(), " Сообщения принято на обработку");
        }
        return replyMessage;
    }
}
