package ru.ws.marketplace.service.impl;

import org.springframework.stereotype.Service;
import ru.ws.marketplace.model.TMessage;
import ru.ws.marketplace.repository.MessageRepository;
import ru.ws.marketplace.service.crud.CRUDMessageService;

import java.util.Date;

@Service
public class CRUDMessageServiceImpl implements CRUDMessageService {

    private final MessageRepository messageRepository;

    public CRUDMessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void createSavedMessage(Long chatId, Integer messageId, String messageText, String replyMessageText) {
        TMessage message = new TMessage();
        message.setMessageId(messageId);
        message.setMessageText(messageText);
        message.setReplyMessageText(replyMessageText);
        message.setChatId(chatId);
        message.setCreateDate(new Date());
        messageRepository.save(message);
    }
}
