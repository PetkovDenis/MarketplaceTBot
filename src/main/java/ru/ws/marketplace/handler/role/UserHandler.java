package ru.ws.marketplace.handler.role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.ws.marketplace.model.TUser;
import ru.ws.marketplace.service.impl.CRUDUserServiceImpl;

@Component
@AllArgsConstructor
public class UserHandler {

    private final CRUDUserServiceImpl crudUserService;

    public void handleCallback(CallbackQuery buttonQuery) {
        TUser client = createClient(buttonQuery);
        crudUserService.add(client);
    }

    public TUser createClient(CallbackQuery buttonQuery) {
        User user = buttonQuery.getFrom();
        TUser client = new TUser();
        client.setFirstName(user.getFirstName());
        client.setChatId(user.getId());
        if (user.getLastName() != null) {
            client.setLastName(user.getLastName());
        } else {
            client.setLastName("DEFAULT last_name");
        }
        return client;
    }
}
