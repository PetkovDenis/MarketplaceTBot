package ru.ws.marketplace.handler.role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.ws.marketplace.model.TAdmin;
import ru.ws.marketplace.service.impl.CRUDAdminServiceImpl;

@Component
@AllArgsConstructor
public class AdminHandler {

    private final CRUDAdminServiceImpl crudAdminService;

    public void handleCallback(CallbackQuery buttonQuery) {
        User user = buttonQuery.getFrom();
        TAdmin admin = createAdmin(user);
        crudAdminService.add(admin);
    }

    private TAdmin createAdmin(User user) {
        TAdmin admin = new TAdmin();
        admin.setFirstName(user.getFirstName());
        admin.setChatId(user.getId());
        if (user.getLastName() != null) {
            admin.setLastName(user.getLastName());
        } else {
            admin.setLastName("DEFAULT last_name");
        }
        return admin;
    }
}
