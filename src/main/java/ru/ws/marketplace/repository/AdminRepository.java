package ru.ws.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TAdmin;

@Service
public interface AdminRepository extends JpaRepository<TAdmin, Long> {
    TAdmin getByChatId(Long id);

    TAdmin getTAdminByChannelName(String name);
}
