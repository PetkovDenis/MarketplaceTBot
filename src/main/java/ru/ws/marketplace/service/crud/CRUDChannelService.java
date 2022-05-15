package ru.ws.marketplace.service.crud;

import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TChannel;

@Service
public interface CRUDChannelService {

    void delete(Long id);

    TChannel add(TChannel channel);

    TChannel get(Long id);

    void update(TChannel channel, Long id);

    TChannel findByName(String name);

    TChannel getByChatId(Long id);

    Boolean searchChannelInDatabase(Long id);
}
