package ru.ws.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TGroup;

@Service
public interface GroupRepository extends JpaRepository<TGroup, Long> {

}
