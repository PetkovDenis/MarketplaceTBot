package ru.ws.marketplace.handler.dialog;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ws.marketplace.handler.dialog.DialogWithClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DialogWithClientTest {

    private Integer messageId = 100;
    private String messageText = "text";

    private Message message;
    private BotApiMethod<?> resultMethod;

    private DialogWithClient dialogWithClient = mock(DialogWithClient.class);

    @Test
    public Message getCreatedMessage() {
        Message message = new Message();
        message.setText(messageText);
        message.setMessageId(messageId);
        return message;
    }

    @Test
    public void dialogWithClient() {

        //Arrange
        message = getCreatedMessage();

        // Act
        resultMethod = dialogWithClient.dialog(message,eq(any()),any());

        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);

        verify(dialogWithClient, times(1)).dialog(messageCaptor.capture(), any(),any());

        Message resultMessage = messageCaptor.getValue();

        // Assert
        verify(dialogWithClient,times(1)).dialog(message,eq(any()),any());

        assertThat(resultMessage.getText()).isEqualTo(messageText);
        assertThat(resultMessage.getMessageId()).isEqualTo(messageId);
    }
}
