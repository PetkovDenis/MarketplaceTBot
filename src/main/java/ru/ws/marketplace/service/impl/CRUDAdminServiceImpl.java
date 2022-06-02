package ru.ws.marketplace.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ws.marketplace.model.TAdmin;
import ru.ws.marketplace.repository.AdminRepository;
import ru.ws.marketplace.service.crud.CRUDAdminService;

import java.sql.SQLException;

@Service
public class CRUDAdminServiceImpl implements CRUDAdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public CRUDAdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public TAdmin add(TAdmin tAdmin) {
        return adminRepository.save(tAdmin);
    }

    @Override
    @SneakyThrows
    @Transactional
    public void update(TAdmin tAdmin, Long id) {
        searchAdminInDatabase(id);
        adminRepository.deleteById(id);
        tAdmin.setId(id);
        adminRepository.save(tAdmin);
    }

    @Override
    @SneakyThrows
    @Transactional
    public TAdmin get(Long id) {
        searchAdminInDatabase(id);
        return adminRepository.getById(id);
    }

    @Override
    public TAdmin getByChatId(Long id) {
        return adminRepository.getByChatId(id);
    }

    @Override
    public TAdmin getTAdminByChannelName(String name) {
        return adminRepository.getTAdminByChannelName(name);
    }

    @Override
    @SneakyThrows
    public void searchAdminInDatabase(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new SQLException();
        }
    }
}
