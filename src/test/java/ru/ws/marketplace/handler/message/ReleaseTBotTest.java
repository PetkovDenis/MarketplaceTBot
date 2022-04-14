package ru.ws.marketplace.handler.message;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import ru.ws.marketplace.model.TChannel;
import ru.ws.marketplace.payments.SendPaymentCard;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class ReleaseTBotTest {

    private static final Long chatId = 3L;
    private static final Integer messageId = 100;
    private static final String nameChannel = "sPort";
    private static final Integer replyMessageId = 43;

    private final String token = "token";


    CRUDChannelServiceImpl crudChannelService = Mockito.mock(CRUDChannelServiceImpl.class);
    SendPaymentCard sendPaymentCard = Mockito.mock(SendPaymentCard.class);


    @Test
    public void testCommandStart() {
        //Arrange
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());

        TChannel channel = TChannel.builder()
                .id(26L)
                .name("sPort")
                .category("Спорт")
                .description("Desc")
                .link("link")
                .price(9000)
                .build();

        //Act

        when(crudChannelService.findByName(nameChannel)).thenReturn(channel);

        TChannel byName = crudChannelService.findByName(nameChannel);

        BotApiMethod<?> botApiMethod = sendPaymentCard.sendPayment(byName,sendMessage);

        //Assert
        assertThat(botApiMethod).isNull();
        assertThat(botApiMethod).isEqualTo(sendPaymentCard.getSendInvoice());

        //1 мокам нужно задать поведение
    }
    public SendInvoice getInvoice(TChannel channel,SendMessage sendMessage){
        SendInvoice sendInvoice = new SendInvoice();
        sendInvoice.setChatId(sendMessage.getChatId());
        sendInvoice.setCurrency("RUB");
        sendInvoice.setPrices(Collections.singletonList(new LabeledPrice("Стоимость подписки", channel.getPrice() * 100)));
        sendInvoice.setProviderToken(token);
        sendInvoice.setPayload(channel.getId().toString());
        sendInvoice.setTitle(channel.getName());
        sendInvoice.setDescription(channel.getDescription());
        return sendInvoice;
    }
}