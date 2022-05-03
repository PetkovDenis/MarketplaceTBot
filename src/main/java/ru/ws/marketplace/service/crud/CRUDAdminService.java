package ru.ws.marketplace.service.crud;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TAdmin;

@Service
public interface CRUDAdminService {
    @SneakyThrows
    void delete(Long id);

    @SneakyThrows
    TAdmin add(TAdmin admin);

    @SneakyThrows
    TAdmin get(Long id);

    @SneakyThrows
    void update(TAdmin admin, Long id);

    @SneakyThrows
    TAdmin getByChatId(Long id);
}
