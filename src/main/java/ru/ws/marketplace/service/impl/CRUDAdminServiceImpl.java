package ru.ws.marketplace.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public void delete(Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
        } else {
            throw new SQLException();
        }
    }

    @Override
    @SneakyThrows
    public void update(TAdmin tAdmin, Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            tAdmin.setId(id);
            adminRepository.save(tAdmin);
        } else {
            throw new SQLException();
        }
    }

    @Override
    @SneakyThrows
    public TAdmin get(Long id) {
        TAdmin tAdmin;
        if (adminRepository.existsById(id)) {
            tAdmin = adminRepository.getById(id);
        } else {
            throw new SQLException();
        }
        return tAdmin;
    }

    @Override
    public TAdmin getByChatId(Long id) {
        return adminRepository.getByChatId(id);
    }

}
