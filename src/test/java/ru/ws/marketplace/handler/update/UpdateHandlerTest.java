package ru.ws.marketplace.handler.update;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UpdateHandlerTest {

    private UpdateHandler updateHandler = mock(UpdateHandler.class);

    @Test
    void execute() {
        //Arrange
        Update update = new Update();
        update.setUpdateId(100);
        //Act
        updateHandler.execute(update);

        ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);

        verify(updateHandler).execute(updateArgumentCaptor.capture());

        Update value = updateArgumentCaptor.getValue();
        //Assert
        verify(updateHandler, times(1)).execute(any());
        assertThat(value.getUpdateId()).isEqualTo(100);
    }
}