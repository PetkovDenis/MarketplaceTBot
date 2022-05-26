package ru.ws.marketplace.service.impl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ws.marketplace.model.TGroup;
import ru.ws.marketplace.repository.GroupRepository;
import ru.ws.marketplace.service.crud.CRUDGroupService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

@Service
public class CRUDGroupServiceImpl implements CRUDGroupService {

    private final GroupRepository groupRepository;
    private final EntityManager entityManager;

    public CRUDGroupServiceImpl(GroupRepository groupRepository, EntityManager entityManager) {
        this.groupRepository = groupRepository;
        this.entityManager = entityManager;
    }

    @Override
    public TGroup add(TGroup tGroup) {
        //TODO: проверять если уже есть эта группа то больше не добавлять её
        return groupRepository.save(tGroup);
    }

    @Override
    @Transactional
    public TGroup get(Long id) {
        searchGroupInDatabase(id);
        return groupRepository.getById(id);
    }

    @Override
    @SneakyThrows
    public void searchGroupInDatabase(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new SQLException();
        }
    }

    public List<TGroup> getAllGroups() {
        Query query = entityManager.createQuery("SELECT e FROM TGroup e");
        return (List<TGroup>) query.getResultList();
    }
}
