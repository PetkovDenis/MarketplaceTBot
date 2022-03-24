package ru.ws.marketplace.service.channel;

import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TChannel;

import java.sql.SQLException;

@Service
public interface CRUDChannelService {

   void delete(Long id) throws SQLException;
   TChannel add(TChannel channel);
   TChannel get(Long id) throws SQLException;
   void update(TChannel channel, Long id) throws SQLException;

}
