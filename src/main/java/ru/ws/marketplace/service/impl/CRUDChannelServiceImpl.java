package ru.ws.marketplace.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.repository.ChannelRepository;
import ru.ws.marketplace.service.crud.CRUDChannelService;

import java.sql.SQLException;

@Service
public class CRUDChannelServiceImpl implements CRUDChannelService {

    final ChannelRepository channelRepository;

    @Autowired
    public CRUDChannelServiceImpl(ChannelRepository channelService) {
        this.channelRepository = channelService;
    }

    @Override
    @SneakyThrows
    public void delete(Long id) {
        if (searchChannelInDatabase(id)) {
            channelRepository.deleteById(id);
        } else {
            throw new SQLException();
        }
    }

    @Override
    public TChannel add(TChannel channel) {
        return channelRepository.save(channel);
    }

    @Override
    @SneakyThrows
    public TChannel get(Long id) {
        TChannel receivedChannel;
        if (searchChannelInDatabase(id)) {
            receivedChannel = channelRepository.getById(id);
        } else {
            throw new SQLException();
        }
        return receivedChannel;
    }

    @Override
    @SneakyThrows
    public void update(TChannel channel, Long id) {
        if (searchChannelInDatabase(id)) {
            channelRepository.deleteById(id);
            channel.setId(id);
            channelRepository.save(channel);
        } else {
            throw new SQLException();
        }
    }

    @Override
    public TChannel findByName(String name) {
        return channelRepository.getTChannelByName(name);
    }

    @Override
    public TChannel getByChatId(Long id) {
        return channelRepository.getByChatId(id);
    }

    @Override
    public Boolean searchChannelInDatabase(Long id) {
        return channelRepository.existsById(id);
    }
}
