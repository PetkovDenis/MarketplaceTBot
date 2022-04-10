package ru.ws.marketplace.bot;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ws.marketplace.handler.message.HandleIncomingMessageService;
import ru.ws.marketplace.init.button.ButtonInitialization;

@Component
public class TBot extends TelegramLongPollingBot {

    private final ButtonInitialization mainMenuService = new ButtonInitialization();

    private final HandleIncomingMessageService handleIncomingMessageService;

    public TBot(HandleIncomingMessageService handleIncomingMessageService) {
        this.handleIncomingMessageService = handleIncomingMessageService;
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
            if (message.hasText()) {
                if (message.getText().equals("/start")) {
                    execute(SendMessage.builder()
                            .chatId(message.getChatId().toString())
                            .text("Здравствуйте!")
                            .replyMarkup(mainMenuService.getKeyboard())
                            .build());
                } else {
                    execute(handleIncomingMessageService.handleUpdate(update));
                }
            }
        } else {
            execute(handleIncomingMessageService.handleUpdate(update));
        }
    }
}
