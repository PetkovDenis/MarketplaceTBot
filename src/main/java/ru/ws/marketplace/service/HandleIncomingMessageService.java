package ru.ws.marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.service.channel.CRUDChannelServiceImpl;
import ru.ws.marketplace.status.*;

@Service
public class HandleIncomingMessageService {

    final MainMenuService mainMenuService;
    final DialogueContext context;

    @Autowired
    ConvertDTOService dtoService;
    @Autowired
    CRUDChannelServiceImpl crudChannelService;

    public HandleIncomingMessageService(MainMenuService mainMenuService, DialogueContext context) {
        this.mainMenuService = mainMenuService;
        this.context = context;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
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

    public SendMessage handleInputMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        String text = message.getText();
        TChannel channel = new TChannel();
        switch (context.getStatusName()) {
            case "NAME":
                channel.setName(text);
                sendMessage.setText("Введите описание канала(цель канала, для кого предназначена информация публикуемая в канале и т.д.)");
                break;
            case "DESCRIPTION":
                channel.setDescription(text);
                sendMessage.setText("Введите ссылку на канал");
                break;
            case "LINK":
                channel.setLink(text);
                sendMessage.setText("Анкета заполнена!\n\n\r Перейдите по ссылке /start для дальнейшего управления ботом(для проверки добавился канал или нет, вам необходимо будет перейти на вкладку Список Ресурсов)");
                break;
        }
        // crudChannelService.add(channel);
        // private String channelLink = "https://t.me/+10khKecxs0BmMzUy";
        context.nextState();
        return sendMessage;
    }

    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery) {
        Long chatId = buttonQuery.getMessage().getChatId();
        BotApiMethod<?> callBackAnswer = mainMenuService.getMainMessage(chatId, "Воспользуйтесь главным меню,\n для получения дополнительной информации");

        switch (buttonQuery.getData()) {
            case "buttonListResources":
                callBackAnswer = new SendMessage(chatId.toString(), "Весь список ресурсов: \n/text\n/text\n/text\ntext\ntext");
                break;
            case "buttonSubscribe":
                callBackAnswer = new SendMessage(chatId.toString(), "Выбрать определенный ресурс:" + dtoService.getAllDTO());
                break;
            case "buttonAddResources":
                callBackAnswer = new SendMessage(chatId.toString(), "Для добавления канала необходимо заполнить анкету \uD83D\uDCCB \n\n" +
                        "Введите название канала");
                context.setState(new Name());
                break;
        }
        return callBackAnswer;
    }
}
