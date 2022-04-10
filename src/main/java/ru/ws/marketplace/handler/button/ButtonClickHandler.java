package ru.ws.marketplace.handler.button;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ws.marketplace.init.button.ButtonInitialization;
import ru.ws.marketplace.service.ConvertDTOService;
import ru.ws.marketplace.state.dialog.DialogueContext;
import ru.ws.marketplace.state.dialog.Name;

@Component
public class ButtonClickHandler {

    private final ButtonInitialization buttonInitialization;
    private final ConvertDTOService convertDTOService;

    public ButtonClickHandler(ButtonInitialization mainMenuService, ConvertDTOService convertDTOService) {
        this.buttonInitialization = mainMenuService;
        this.convertDTOService = convertDTOService;
    }

    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, DialogueContext context) {
        Long chatId = buttonQuery.getMessage().getChatId();

        BotApiMethod<?> callBackAnswer = buttonInitialization.getMainMessage(chatId, "Воспользуйтесь главным меню,\n для получения дополнительной информации");
        switch (buttonQuery.getData()) {
            case "buttonSubscribe":
                callBackAnswer = buttonInitialization.convertDTOInButton(chatId, convertDTOService.getAllDTO());
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
