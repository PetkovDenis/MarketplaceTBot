package ru.ws.marketplace.payments;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import ru.ws.marketplace.model.TChannel;

import java.util.Collections;


@Component
public class SendPaymentCard {

    @Value("${paymentSystem.providerToken}")
    private String providerToken;

    public BotApiMethod<?> sendPayment(TChannel channel, SendMessage message) {
        SendInvoice sendInvoice = new SendInvoice();
        sendInvoice.setChatId(message.getChatId());
        sendInvoice.setCurrency("RUB");
        sendInvoice.setPrices(Collections.singletonList(new LabeledPrice("Стоимость подписки", channel.getPrice() * 100)));
        sendInvoice.setProviderToken(providerToken);
        sendInvoice.setPayload(channel.getId().toString());
        sendInvoice.setTitle(channel.getName());
        sendInvoice.setDescription(channel.getDescription());
        return sendInvoice;
    }
}
