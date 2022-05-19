package ru.ws.marketplace.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ws.marketplace.model.TUser;
import ru.ws.marketplace.repository.UserRepository;
import ru.ws.marketplace.service.crud.CRUDUserService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

@Service
public class CRUDUserServiceImpl implements CRUDUserService {

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Autowired
    public CRUDUserServiceImpl(UserRepository userRepository, EntityManager em) {
        this.userRepository = userRepository;
        this.entityManager = em;
    }

    @Override
    public TUser add(TUser user) {
        return userRepository.save(user);
    }

    @Override
    @SneakyThrows
    @Transactional
    public void delete(Long id) {
        searchUserInDatabase(id);
        userRepository.deleteById(id);
    }

    @Override
    @SneakyThrows
    @Transactional
    public void update(TUser user, Long id) {//TODO: изменить функцию обновления данных
        searchUserInDatabase(id);
        userRepository.deleteById(id);
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    @SneakyThrows
    @Transactional
    public TUser get(Long id) {
        searchUserInDatabase(id);
        TUser tUser = userRepository.getById(id);
        return tUser;
    }

    @Override
    @Transactional
    public List<TUser> getAllUsers() {
        Query query = entityManager.createQuery("SELECT e FROM TUser e");
        return (List<TUser>) query.getResultList();
    }


    @Override
    public TUser getByChatId(Long id) {
        return userRepository.getByChatId(id);
    }

    @Override
    public List<TUser> getAllByInvoiceId(Integer id) {
        return userRepository.getAllByChannelId(id);
    }

    @Override
    public List<TUser> getAllByChannelName(String name) {
        return userRepository.getAllByChannelName(name);
    }

    @Override
    @SneakyThrows
    public void searchUserInDatabase(Long id) {
        if (!userRepository.existsById(id)) {
            throw new SQLException();
        }
    }
}
