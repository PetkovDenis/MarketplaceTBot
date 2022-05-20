package ru.ws.marketplace.service.crud;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface CRUDMessageService {

     void createSavedMessage(Long chatId, Integer messageId, String messageText, String replyMessageText);

}
