package ru.ws.marketplace.service.crud;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TUser;

import java.util.List;

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

//    @SneakyThrows
//    List<TUser> getAllUsers();

    @SneakyThrows
    List<TUser> getAllByEndDate();
}
