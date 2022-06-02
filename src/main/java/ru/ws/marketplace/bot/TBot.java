package ru.ws.marketplace.bot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;
import ru.ws.marketplace.handler.message.MessageHandler;
import ru.ws.marketplace.handler.update.UpdateHandler;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;
import ru.ws.marketplace.service.impl.CRUDMessageServiceImpl;

@Component
public class TBot extends TelegramLongPollingBot {

    private final CRUDChannelServiceImpl crudChannelService;
    private final MessageHandler messageHandler;
    private final UpdateHandler updateHandler;
    private final CRUDMessageServiceImpl crudMessageService;

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.name}")
    private String botName;

    public TBot(CRUDChannelServiceImpl crudChannelService, MessageHandler messageHandler, UpdateHandler updateHandler, CRUDMessageServiceImpl crudMessageService) {
        this.crudChannelService = crudChannelService;
        this.messageHandler = messageHandler;
        this.updateHandler = updateHandler;
        this.crudMessageService = crudMessageService;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasSuccessfulPayment()) {
                SuccessfulPayment successfulPayment = message.getSuccessfulPayment();
                TChannel tChannel = crudChannelService.get(Long.valueOf(successfulPayment.getInvoicePayload()));
                SendMessage replyMessage = new SendMessage(message.getChatId().toString(), "Платеж успешно завершен!\n Ссылка на канал: " + tChannel.getLink());
                crudMessageService.createSavedMessage(message.getChatId(), message.getMessageId(), message.getText(), replyMessage.getText());
                execute(replyMessage);
            }
            if (message.hasText()) {
                if (message.getText().equals("Получить отчет")) {
                    SendDocument file = messageHandler.createFile(message);
                    crudMessageService.createSavedMessage(message.getChatId(), message.getMessageId(), message.getText(), "send file");
                    execute(file);
                } else {
                    execute(updateHandler.execute(update));
                }
            }
        } else {
            execute(updateHandler.execute(update));
        }
    }
}
