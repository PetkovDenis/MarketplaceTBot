package ru.ws.marketplace.handler.update;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class UpdateHandlerTestIT {

    @Autowired
    private UpdateHandler updateHandler;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void executeMessage() {
        //Arrange
        Update update = new Update();
        Message message = new Message();
        message.setMessageId(100);
        message.setText("TEXT");
        //Act
        update.setMessage(message);

        //Assert
        assertThat(updateHandler);
    }

    // выполнить все сценарии с ботом от платежа до смс

}