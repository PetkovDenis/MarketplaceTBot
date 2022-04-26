package ru.ws.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TUser;

@Service
public interface UserRepository extends JpaRepository<TUser, Long> {
    TUser getByFirstName(String firstName);
    TUser getByLastName(String lastName);
}
