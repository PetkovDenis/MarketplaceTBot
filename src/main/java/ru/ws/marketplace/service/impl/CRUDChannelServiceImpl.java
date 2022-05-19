package ru.ws.marketplace.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.repository.ChannelRepository;
import ru.ws.marketplace.service.crud.CRUDChannelService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

@Service
public class CRUDChannelServiceImpl implements CRUDChannelService {

    private final ChannelRepository channelRepository;
    private final EntityManager entityManager;

    @Autowired
    public CRUDChannelServiceImpl(ChannelRepository channelService, EntityManager entityManager) {
        this.channelRepository = channelService;
        this.entityManager = entityManager;
    }

    @Override
    @SneakyThrows
    @Transactional
    public void delete(Long id) {
        searchChannelInDatabaseById(id);
        channelRepository.deleteById(id);
    }

    @Override
    public TChannel add(TChannel channel) {
        return channelRepository.save(channel);
    }

    @Override
    @SneakyThrows
    @Transactional
    public TChannel get(Long id) {
        searchChannelInDatabaseById(id);
        return channelRepository.getById(id);
    }

    @Override
    @SneakyThrows
    @Transactional
    public void update(TChannel channel, Long id) {
        searchChannelInDatabaseById(id);
        channelRepository.deleteById(id); //TODO: изменить функцию обновления данных
        channel.setId(id);
        channelRepository.save(channel);
    }

    @Override
    @Transactional
    public TChannel findByName(String name) {
        searchChannelInDatabaseByName(name);
        return channelRepository.getTChannelByName(name);
    }


    @Override
    @Transactional
    public List<TChannel> getAllChannels() {
        Query query = entityManager.createQuery("SELECT e FROM TChannel e");
        return (List<TChannel>) query.getResultList();
    }


    @Override
    public TChannel getByChatId(Long id) {
        return channelRepository.getByChatId(id);
    }

    @Override
    @SneakyThrows
    public void searchChannelInDatabaseById(Long id) {
        if (!channelRepository.existsById(id)) {
            throw new SQLException();
        }
    }

    @SneakyThrows
    public void searchChannelInDatabaseByName(String name) {
        if (channelRepository.getTChannelByName(name) == null) {
            throw new SQLException();
        }
    }
}
