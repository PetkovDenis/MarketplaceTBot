package ru.ws.marketplace.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TChannel;

@Service
public interface CRUDChannelService {

    @SneakyThrows
    void delete(Long id);

    @SneakyThrows
    TChannel add(TChannel channel);

    @SneakyThrows
    TChannel get(Long id);

    @SneakyThrows
    void update(TChannel channel, Long id);

    @SneakyThrows
    TChannel findByName(String name);
}
