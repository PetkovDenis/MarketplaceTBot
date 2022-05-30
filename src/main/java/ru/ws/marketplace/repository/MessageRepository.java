package ru.ws.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TMessage;

@Service
public interface MessageRepository extends JpaRepository<TMessage, Long> {

}
