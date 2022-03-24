package ru.ws.marketplace.service.channel;

import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TChannel;

import java.sql.SQLException;

@Service
public class CRUDChannelServiceImpl implements CRUDChannelService {

    final ChannelRepository channelRepository;

    public CRUDChannelServiceImpl(ChannelRepository channelService) {
        this.channelRepository = channelService;
    }

    @Override
    public void delete(Long id) throws SQLException {
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
