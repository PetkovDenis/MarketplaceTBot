package ru.ws.marketplace.service.crud;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TUser;

import java.util.List;

@Service
public interface CRUDUserService {

    TUser add(TUser tUser);

    void delete(Long id);

    void update(TUser tUser, Long id);

    TUser get(Long id);

    TUser findByFirstName(String firstName);

    TUser findByLastName(String lastName);

    List<TUser> getAllByEndDate();

    TUser getByChatId(Long id);

    List<TUser> getAllByInvoiceId(Integer id);

    Boolean searchUserInDatabase(Long id);
}
