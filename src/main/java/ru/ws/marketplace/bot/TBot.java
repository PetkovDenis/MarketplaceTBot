package ru.ws.marketplace.bot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ws.marketplace.service.HandleIncomingMessageService;
import ru.ws.marketplace.service.MainMenuService;

@Component
public class TBot extends TelegramLongPollingBot {

    private String token = "5277995877:AAFxEZE5tVi1XTcxIGKIV6N7M-2hKuXfhjE";
    private String userName = "@WSMarketplace_bot";
    private final MainMenuService mainMenuService;
    private final HandleIncomingMessageService incomingMessageService;

    public TBot(MainMenuService service, HandleIncomingMessageService incomingMessageService) {
        this.mainMenuService = service;
        this.incomingMessageService = incomingMessageService;
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
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
                }
            }
        } else if (update.hasCallbackQuery()) {
            execute(incomingMessageService.updateProcessing(update));
        }
    }
}
