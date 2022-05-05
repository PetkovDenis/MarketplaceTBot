package ru.ws.marketplace.payments;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.ws.marketplace.model.TChannel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SendPaymentCardTest {

    private SendPaymentCard sendPaymentCard = mock(SendPaymentCard.class);

    Long channelId = 100L;
    String channelName = "Test name";
    String channelDescription = "Test description";

    @Test
    void sendPayment() {
        //Arrange
        TChannel channel = createdTChannel();

        //Act
        sendPaymentCard.sendPayment(channel, eq(any()));

        ArgumentCaptor<TChannel> channelArgumentCaptor = ArgumentCaptor.forClass(TChannel.class);

        verify(sendPaymentCard).sendPayment(channelArgumentCaptor.capture(), any());

        TChannel value = channelArgumentCaptor.getValue();
        //Assert
        verify(sendPaymentCard, times(1)).sendPayment(channel, eq(any()));

        assertThat(value.getId()).isEqualTo(channelId);
        assertThat(value.getName()).isEqualTo(channelName);
        assertThat(value.getDescription()).isEqualTo(channelDescription);
    }

    TChannel createdTChannel() {
        TChannel channel = new TChannel();
        channel.setId(channelId);
        channel.setName(channelName);
        channel.setDescription(channelDescription);
        return channel;
    }
}