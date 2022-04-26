package ru.ws.marketplace.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TUser;

@Service
public interface CRUDUserService {

    @SneakyThrows
    TUser add(TUser tUser);

    @SneakyThrows
    void delete(Long id);

    @SneakyThrows
    void update(TUser tUser, Long id);

    @SneakyThrows
    TUser get(Long id);

    @SneakyThrows
    TUser findByFirstName(String firstName);

    @SneakyThrows
    TUser findByLastName(String lastName);
}
