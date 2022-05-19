package ru.ws.marketplace.service.crud;

import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TChannel;

import java.util.List;

@Service
public interface CRUDChannelService {

    void delete(Long id);

    TChannel add(TChannel channel);

    TChannel get(Long id);

    void update(TChannel channel, Long id);

    TChannel findByName(String name);

    TChannel getByChatId(Long id);

    void searchChannelInDatabaseById(Long id);

    List<TChannel> getAllChannels();
}
