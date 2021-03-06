package ru.ws.marketplace.handler.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.payments.SendPaymentCard;

@Component
public class CreateMessageWithPayment {

    final SendPaymentCard sendPaymentCard;

    public CreateMessageWithPayment(SendPaymentCard sendPaymentCard) {
        this.sendPaymentCard = sendPaymentCard;
    }

    public BotApiMethod<?> getPayment(Message message, TChannel channel) {
        Long chatId = message.getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        return sendPaymentCard.sendPayment(channel, sendMessage);
    }
}
