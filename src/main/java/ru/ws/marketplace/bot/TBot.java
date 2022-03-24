package ru.ws.marketplace.bot;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.ws.marketplace.service.HandleIncomingMessageService;
import ru.ws.marketplace.service.MainMenuService;

@Configuration
public class TBot extends TelegramLongPollingBot {

    private String token = "5277995877:AAFxEZE5tVi1XTcxIGKIV6N7M-2hKuXfhjE";
    private String userName = "@WSMarketplace_bot";
    private final MainMenuService mainMenuService;
    private final HandleIncomingMessageService handleIncomingMessageService;

    public TBot(MainMenuService service, HandleIncomingMessageService incomingMessageService) {
        this.mainMenuService = service;
        this.handleIncomingMessageService = incomingMessageService;
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
                }else{
                    execute(handleIncomingMessageService.handleUpdate(update));
                }
            }
        } else {
            BotApiMethod<?> botApiMethod = handleIncomingMessageService.handleUpdate(update);
            execute(botApiMethod);
        }
    }
}
