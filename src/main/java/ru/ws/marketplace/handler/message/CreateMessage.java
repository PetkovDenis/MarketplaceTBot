package ru.ws.marketplace.handler.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.payments.SendPaymentCard;

@Component
public class CreateMessage {

    final SendPaymentCard sendPaymentCard;

    public CreateMessage(SendPaymentCard sendPaymentCard) {
        this.sendPaymentCard = sendPaymentCard;
    }


    public BotApiMethod<?> getPayment(Long chatId, TChannel byName) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        BotApiMethod<?> botApiMethod = sendPaymentCard.sendPayment(byName, sendMessage);
        return botApiMethod;
    }
}
