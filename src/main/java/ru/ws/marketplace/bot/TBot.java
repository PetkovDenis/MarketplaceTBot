package ru.ws.marketplace.bot;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;
import ru.ws.marketplace.handler.message.MessageHandler;
import ru.ws.marketplace.handler.update.UpdateHandler;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.model.TMessage;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;

@Component
@AllArgsConstructor
public class TBot extends TelegramLongPollingBot {

    private final CRUDChannelServiceImpl crudChannelService;
    private final MessageHandler messageHandler;
    private final UpdateHandler updateHandler;
    private final TMessage telegramMessage;

    @Override
    public String getBotUsername() {
        return "@WSMarketplace_bot";
    }

    @Override
    public String getBotToken() {
        return "5277995877:AAFxEZE5tVi1XTcxIGKIV6N7M-2hKuXfhjE";
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            telegramMessage.messageWaiting(message);
            if (message.hasSuccessfulPayment()) {
                SuccessfulPayment successfulPayment = message.getSuccessfulPayment();
                TChannel tChannel = crudChannelService.get(Long.valueOf(successfulPayment.getInvoicePayload()));
                SendMessage replyMessage = new SendMessage(message.getChatId().toString(), "Платеж успешно завершен!\n Ссылка на канал: " + tChannel.getLink());
                execute(replyMessage);
            }
            if (message.hasText()) {
                if (message.getText().equals("Получить отчет")) {
                    execute(messageHandler.createFile(message));
                } else {
                    execute(updateHandler.execute(update));
                }
            }
        } else {
            execute(updateHandler.execute(update));
        }
    }
}
