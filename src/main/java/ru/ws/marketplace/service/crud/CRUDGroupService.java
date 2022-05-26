package ru.ws.marketplace.service.crud;

import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TGroup;


@Service
public interface CRUDGroupService {

   TGroup add(TGroup tGroup);

    TGroup get(Long id);

    void searchGroupInDatabase(Long id);
}
