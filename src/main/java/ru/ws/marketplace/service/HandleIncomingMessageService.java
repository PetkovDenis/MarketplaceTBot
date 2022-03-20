package ru.ws.marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class HandleIncomingMessageService {

    final MainMenuService mainMenuService;

    public HandleIncomingMessageService(MainMenuService mainMenuService) {
        this.mainMenuService = mainMenuService;
    }

    public BotApiMethod<?> updateProcessing(Update update) {
        SendMessage replyMessage = null;
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return handleCallback(callbackQuery);
        }
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            replyMessage = handleInputMessage(message);
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(message.getText());
        return sendMessage;
    }

    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery) {
        Long chatId = buttonQuery.getMessage().getChatId();
        BotApiMethod<?> callBackAnswer = mainMenuService.getMainMessage(chatId, "Воспользуйтесь главным меню,\n для получения дополнительной информации");
        if (buttonQuery.getData().equals("buttonListResources")) {
            callBackAnswer = new SendMessage(chatId.toString(), "Весь список ресурсов: \n/text\n/text\n/text\ntext\ntext");
        } else if (buttonQuery.getData().equals("buttonSubscribe")) {
            callBackAnswer = new SendMessage(chatId.toString(), "Выбрать определенный ресурс: \n/text\n/text\n/text\ntext\ntext");
        }
        return callBackAnswer;
    }
}
