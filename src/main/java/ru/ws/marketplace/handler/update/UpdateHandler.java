package ru.ws.marketplace.handler.update;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;
import ru.ws.marketplace.handler.button.ButtonClickHandler;
import ru.ws.marketplace.handler.message.MessageHandler;
import ru.ws.marketplace.handler.preCheckoutPayment.PreCheckoutQueryHandler;
import ru.ws.marketplace.handler.role.UserHandler;
import ru.ws.marketplace.state.dialog.DialogueContext;
import ru.ws.marketplace.state.file.FileContext;

@Component
@RequiredArgsConstructor
public class UpdateHandler {

    private final DialogueContext context = new DialogueContext();
    private final FileContext fileContext = new FileContext();
    private final MessageHandler messageHandler;
    private final ButtonClickHandler callbackHandler;
    private final UserHandler userHandler;

    private BotApiMethod<?> replyMessage;
    private final PreCheckoutQueryHandler preCheckoutQueryHandler;

    public BotApiMethod<?> handleUpdate(Update update) {

        Message message = update.getMessage();

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackHandler.handleCallback(callbackQuery, context, fileContext);

        } else if (update.hasPreCheckoutQuery()) {
            PreCheckoutQuery preCheckoutQuery = update.getPreCheckoutQuery();
            boolean result = preCheckoutQueryHandler.handlePreCheckoutQuery(preCheckoutQuery);
            return replyMessage = new AnswerPreCheckoutQuery(preCheckoutQuery.getId(), result);
        }

        if (!update.hasMessage()) {
            return replyMessage;
        }

        if (message.hasSuccessfulPayment()) {
            SuccessfulPayment successfulPayment = message.getSuccessfulPayment();
            String invoicePayload = successfulPayment.getInvoicePayload();
            replyMessage = new SendMessage(message.getChatId().toString(), "Платеж успешно завершен! " + invoicePayload + "\n Ссылка на канал: ");
        }

        if (message.hasText()) {
            replyMessage = messageHandler.sortedMessage(message, context, fileContext);
        } else {
            replyMessage = new SendMessage(update.getMessage().getChatId().toString(), " Сообщения принято на обработку");
        }
        return replyMessage;
    }
}
