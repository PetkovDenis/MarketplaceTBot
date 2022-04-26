package ru.ws.marketplace.handler.message;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MessageHandlerTest {

    private BotApiMethod<?> resultMethod;
    private Message message;

    private Integer messageId = 100;
    private String messageText = "text";

    MessageHandler messageHandler = mock(MessageHandler.class);

    @Test
    public Message getCreatedMessage() {
        Message message = new Message();
        message.setText(messageText);
        message.setMessageId(messageId);
        return message;
    }

    @Test
    public void getTChannel() {

        //Arrange
        message = getCreatedMessage();

        //Act
        resultMethod = messageHandler.getTChannel(message);

        ArgumentCaptor<Message> channelCaptor = ArgumentCaptor.forClass(Message.class);

        verify(messageHandler, times(1)).getTChannel(channelCaptor.capture());

        Message resultCaptor = channelCaptor.getValue();

        //Assert
        assertThat(resultCaptor.getText()).isEqualTo(messageText);
        assertThat(resultCaptor.getMessageId()).isEqualTo(messageId);
    }

    @Test
    public void dialogWithClient() {

        //Arrange
        message = getCreatedMessage();

        // Act
        resultMethod = messageHandler.dialogWithClient(message,eq(any()));

        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);

        verify(messageHandler,times(1)).dialogWithClient(messageCaptor.capture(),any());

        Message resultMessage = messageCaptor.getValue();

        // Assert
        assertThat(resultMessage.getText()).isEqualTo(messageText);
        assertThat(resultMessage.getMessageId()).isEqualTo(messageId);
    }
}
