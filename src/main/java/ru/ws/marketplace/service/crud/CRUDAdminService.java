package ru.ws.marketplace.service.crud;

import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TAdmin;

@Service
public interface CRUDAdminService {

    void delete(Long id);

    TAdmin add(TAdmin admin);

    TAdmin get(Long id);

    void update(TAdmin admin, Long id);

    TAdmin getByChatId(Long id);

    Boolean searchAdminInDatabase(Long id);
}
