package ru.ws.marketplace.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;
import ru.ws.marketplace.state.*;

@Service
public class HandleIncomingMessageService {

    private final MainMenuService mainMenuService = new MainMenuService();
    private final DialogueContext context = new DialogueContext();
    TChannel channel = new TChannel();

    final ConvertDTOService dtoService;

    final CRUDChannelServiceImpl crudChannelService;

    public HandleIncomingMessageService(CRUDChannelServiceImpl crudChannelService, ConvertDTOService dtoService) {
        this.crudChannelService = crudChannelService;
        this.dtoService = dtoService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage;
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return handleCallback(callbackQuery);
        }
        Message message = update.getMessage();
        if (message != null && message.hasText() && !context.getStatusName().equals("END")) {
            replyMessage = handleInputMessage(message);
        } else {
            replyMessage = new SendMessage(update.getMessage().getChatId().toString(), " Сообщения принято на обработку");
        }
        return replyMessage;
    }

    public SendMessage handleInputMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        String text = message.getText();

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
                sendMessage.setText("Введите цену подписки на месяц");
                break;
            case "PRICE":
                channel.setPrice(Integer.valueOf(text));
                break;
            default:
                break;
        }
        if(context.getStatusName().equals("PRICE")){
           crudChannelService.add(channel);
            sendMessage.setText("Анкета заполнена! \n\n Перейдите по ссылке /start для дальнейшего управления ботом(для проверки добавился канал или нет, вам необходимо будет перейти на вкладку Список Ресурсов)");
        }
        context.nextState();
        return sendMessage;
    }

    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery) {
        Long chatId = buttonQuery.getMessage().getChatId();
        BotApiMethod<?> callBackAnswer = mainMenuService.getMainMessage(chatId, "Воспользуйтесь главным меню,\n для получения дополнительной информации");
        switch (buttonQuery.getData()) {
            case "buttonSubscribe":
                callBackAnswer = new SendMessage(chatId.toString(), "Выбрать определенный ресурс: \n"+dtoService.getAllNameDTO());
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
