package ru.ws.marketplace.handler.dialog;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.ws.marketplace.handler.dialog.GreetingPerson;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class GreetingPersonHandlerTest {

    GreetingPerson greetingPerson = mock(GreetingPerson.class);

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
    public void handleCallbackTest() {
        //Arrange
        Message createdMessage = getCreatedMessage();
        greetingPerson.greeting(createdMessage);
        //Act
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
        verify(greetingPerson).greeting(messageCaptor.capture());

        Message value = messageCaptor.getValue();
        //Assert
        verify(greetingPerson,times(1)).greeting(any());

        assertThat(value.getMessageId()).isEqualTo(messageId);
        assertThat(value.getText()).isEqualTo(messageText);
    }
}
