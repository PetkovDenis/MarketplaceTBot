package ru.ws.marketplace.service.crud;

import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TUser;

import java.util.List;

@Service
public interface CRUDUserService {

    TUser add(TUser tUser);

    void delete(Long id);

    void update(TUser tUser, Long id);

    TUser get(Long id);

    TUser getByChatId(Long id);

    List<TUser> getAllByInvoiceId(Integer id);

    List<TUser> getAllByChannelName(String name);

    void searchUserInDatabase(Long id);

    List<TUser> getAllUsers();


}
