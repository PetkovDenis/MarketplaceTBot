package ru.ws.marketplace.handler.user;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.ws.marketplace.init.button.ButtonInitialization;
import ru.ws.marketplace.model.TUser;
import ru.ws.marketplace.service.impl.CRUDUserServiceImpl;

@Component
public class UserHandler {

    private final ButtonInitialization mainMenuService = new ButtonInitialization();

    private final CRUDUserServiceImpl crudUserService;

    public UserHandler(CRUDUserServiceImpl crudUserService) {
        this.crudUserService = crudUserService;
    }

    public BotApiMethod<?> handleMessage(Message message) {

        TUser client = createClient(message);
        crudUserService.add(client);

        return createSendMessage(message);
    }

    public TUser createClient(Message message) {
        User user = message.getFrom();
        TUser client = new TUser();
        client.setFirstName(user.getFirstName());
        if(user.getLastName() != null) {
            client.setLastName(user.getLastName());
        }else {
            client.setLastName("DEFAULT last_name");
        }

        client.setChatId(message.getChatId());
        return client;
    }

    public SendMessage createSendMessage(Message message) {
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), "Здравствуйте!");
        sendMessage.setReplyMarkup(mainMenuService.getKeyboard());
        return sendMessage;
    }
}
