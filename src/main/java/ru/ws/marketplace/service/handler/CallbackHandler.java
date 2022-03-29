package ru.ws.marketplace.service.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ws.marketplace.service.ConvertDTOService;
import ru.ws.marketplace.service.MainMenuService;
import ru.ws.marketplace.state.DialogueContext;
import ru.ws.marketplace.state.Name;

@Service
public class CallbackHandler {

    final MainMenuService mainMenuService;
    final ConvertDTOService convertDTOService;

    public CallbackHandler(MainMenuService mainMenuService, ConvertDTOService convertDTOService) {
        this.mainMenuService = mainMenuService;
        this.convertDTOService = convertDTOService;
    }

    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, DialogueContext context) {
        Long chatId = buttonQuery.getMessage().getChatId();
        BotApiMethod<?> callBackAnswer = mainMenuService.getMainMessage(chatId, "Воспользуйтесь главным меню,\n для получения дополнительной информации");
        switch (buttonQuery.getData()) {
            case "buttonSubscribe":
                callBackAnswer = new SendMessage(chatId.toString(), "Выбрать определенный ресурс: \n" + convertDTOService.getAllNameDTO());
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
