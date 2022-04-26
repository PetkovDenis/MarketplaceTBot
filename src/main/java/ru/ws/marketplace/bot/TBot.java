package ru.ws.marketplace.bot;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;
import ru.ws.marketplace.handler.update.UpdateHandler;
import ru.ws.marketplace.handler.user.UserHandler;

@Component
public class TBot extends TelegramLongPollingBot {

    private final UpdateHandler handleUpdate;
    private final UserHandler userHandler;

    public TBot(UpdateHandler handleIncomingMessageService, UserHandler userHandler) {
        this.handleUpdate = handleIncomingMessageService;
        this.userHandler = userHandler;
    }

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
            if (message.hasSuccessfulPayment()) {
                SuccessfulPayment successfulPayment = message.getSuccessfulPayment();
                SendMessage replyMessage = new SendMessage(message.getChatId().toString(), "Платеж успешно завершен! Информация о плетеже\n Ссылка на канал: ");
                execute(replyMessage);
            }
            if (message.hasText()) {
                if (message.getText().equals("/start")) {
                    execute(userHandler.handleMessage(message));
                } else {
                    execute(handleUpdate.handleUpdate(update));
                }
            }
        } else {
            execute(handleUpdate.handleUpdate(update));
        }
    }
}
