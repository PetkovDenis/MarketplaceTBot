package ru.ws.marketplace.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.service.CRUDChannelService;
import ru.ws.marketplace.repository.ChannelRepository;

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
    public void delete(Long id)  {
        if (channelRepository.existsById(id)) {
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
    public TChannel get(Long id) throws SQLException {
        TChannel receivedChannel;
        if (channelRepository.existsById(id)) {
            receivedChannel = channelRepository.getById(id);
        } else {
            throw new SQLException();
        }
        return receivedChannel;
    }

    @Override
    public void update(TChannel channel, Long id) throws SQLException {
        if (channelRepository.existsById(id)) {
            channelRepository.deleteById(id);
            channel.setId(id);
            channelRepository.save(channel);
        } else {
            throw new SQLException();
        }
    }
}
