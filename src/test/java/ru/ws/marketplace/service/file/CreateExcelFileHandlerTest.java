package ru.ws.marketplace.service.file;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateExcelFileHandlerTest {

    private Integer messageId = 100;
    private String messageText = "text";

    private CreateExcelFileHandler createExcelFileHandler = mock(CreateExcelFileHandler.class);

    public Message getCreatedMessage() {
        Message message = new Message();
        message.setText(messageText);
        message.setMessageId(messageId);
        return message;
    }

    @Test
    void getReadyExcelList() {
        //Arrange
        Message createdMessage = getCreatedMessage();
        //Act
        createExcelFileHandler.sendFileToUser(eq(any()),createdMessage);

        ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);

        verify(createExcelFileHandler).sendFileToUser(any(),messageArgumentCaptor.capture());

        Message value = messageArgumentCaptor.getValue();
        //Assert
        verify(createExcelFileHandler,times(1)).sendFileToUser(any(),any());

        assertThat(value.getMessageId()).isEqualTo(messageId);
        assertThat(value.getText()).isEqualTo(messageText);
    }
}