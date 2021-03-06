package ru.ws.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TChannel;

@Service
public interface ChannelRepository extends JpaRepository<TChannel, Long> {

    TChannel getTChannelByName(String firstName);

    TChannel getByChatId(Long id);

}
