package ru.ws.marketplace.handler.message;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CreateMessageWithPaymentTest {

    private CreateMessageWithPayment createMessageWithPayment = mock(CreateMessageWithPayment.class);

    private Integer messageId = 100;
    private String messageText = "text";

    @Test
    public Message getCreatedMessage() {
        Message message = new Message();
        message.setText(messageText);
        message.setMessageId(messageId);
        return message;
    }

    @Test
    public void getPayment() {
        //Arrange
        Message createdMessage = getCreatedMessage();
        createMessageWithPayment.getPayment(createdMessage, eq(any()));
        //Act
        ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);
        verify(createMessageWithPayment).getPayment(messageArgumentCaptor.capture(), any());

        Message value = messageArgumentCaptor.getValue();
        //Assert
        verify(createMessageWithPayment, times(1)).getPayment(createdMessage, eq(any()));
        assertThat(value.getText()).isEqualTo(messageText);
        assertThat(value.getMessageId()).isEqualTo(messageId);
    }
}
