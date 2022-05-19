package ru.ws.marketplace.model;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.logging.Logger;

@Component
public class TMessage {

    private static final Logger log = Logger.getLogger(Message.class.getName());

    public void messageWaiting(Message message) {
        log.info("username: " + message.getFrom().getFirstName() + " lastName: " + message.getFrom().getLastName() + " Sent a message - " + message.getText());
    }
}
