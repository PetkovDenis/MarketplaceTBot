package ru.ws.marketplace.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TUser;
import ru.ws.marketplace.repository.UserRepository;
import ru.ws.marketplace.service.crud.CRUDUserService;

import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class CRUDUserServiceImpl implements CRUDUserService {

    private final UserRepository userRepository;

    @Autowired
    public CRUDUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public TUser add(TUser user) {
        return userRepository.save(user);
    }

    @Override
    @SneakyThrows
    public void delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new SQLException();
        }
    }

    @Override
    @SneakyThrows
    public void update(TUser user, Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            user.setId(id);
            userRepository.save(user);
        } else {
            throw new SQLException();
        }
    }

    @Override
    @SneakyThrows
    public TUser get(Long id) {
        TUser tUser;
        if (userRepository.existsById(id)) {
            tUser = userRepository.getById(id);
        } else {
            throw new SQLException();
        }
        return tUser;
    }

    @Override
    public TUser findByFirstName(String firstName) {
        return userRepository.getByFirstName(firstName);
    }

    @Override
    public TUser findByLastName(String lastName) {
        return userRepository.getByLastName(lastName);
    }

    @Override
    public List<TUser> getAllByEndDate() {
        return userRepository.getAllByEndDate(new GregorianCalendar());
    }

    @Override
    public TUser getByChatId(Long id) {
        return userRepository.getByChatId(id);
    }
}
