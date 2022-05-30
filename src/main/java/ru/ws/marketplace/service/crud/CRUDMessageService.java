package ru.ws.marketplace.service.crud;

import org.springframework.stereotype.Service;

@Service
public interface CRUDMessageService {

    void createSavedMessage(Long chatId, Integer messageId, String messageText, String replyMessageText);

}
