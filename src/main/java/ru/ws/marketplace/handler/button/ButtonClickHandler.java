package ru.ws.marketplace.handler.button;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.ws.marketplace.handler.role.AdminHandler;
import ru.ws.marketplace.handler.role.UserHandler;
import ru.ws.marketplace.init.button.ButtonInitialization;
import ru.ws.marketplace.service.ConvertDTOService;
import ru.ws.marketplace.service.file.CreateExcelFileHandler;
import ru.ws.marketplace.state.dialog.Category;
import ru.ws.marketplace.state.dialog.DialogueContext;

@Component
@AllArgsConstructor
public class ButtonClickHandler {

    private final ButtonInitialization buttonInitialization;
    private final ConvertDTOService convertDTOService;
    private final CreateExcelFileHandler createExcelFileHandler;
    private final AdminHandler adminHandler;
    private final UserHandler userHandler;

    public BotApiMethod<?> handleCallback(CallbackQuery buttonQuery, DialogueContext context) {
        Long chatId = buttonQuery.getMessage().getChatId();
        BotApiMethod<?> callBackAnswer = buttonInitialization.getMainMessage(chatId, "Воспользуйтесь главным меню");
        switch (buttonQuery.getData()) {
            case "buttonSubscribe":
                // логика для клиента
                userHandler.handleCallback(buttonQuery);
                callBackAnswer = buttonInitialization.convertDTOInButton(chatId, convertDTOService.getAllDTO());
                break;
            case "buttonAddResources":
                // логика для админа
                SendMessage message = new SendMessage(chatId.toString(), "Для добавления канала необходимо заполнить анкету \uD83D\uDCCB\n\n Выберите категорию");
                message.setReplyMarkup(buttonInitialization.getAllCategories());
                adminHandler.handleCallback(buttonQuery);
                callBackAnswer = message;
                context.setState(new Category());
                break;
            case "buttonReport":
                callBackAnswer = buttonInitialization.getMainMessage(chatId, "Выберите отчет");
                break;
        }
        return callBackAnswer;
    }
}
