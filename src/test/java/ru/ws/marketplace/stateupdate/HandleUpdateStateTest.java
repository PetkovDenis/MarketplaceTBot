package ru.ws.marketplace.stateupdate;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import ru.ws.marketplace.handler.button.ButtonClickHandler;
import ru.ws.marketplace.handler.message.MessageHandler;
import ru.ws.marketplace.handler.preCheckout.PreCheckoutQueryHandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HandleUpdateStateTest {

    private CallbackQuery callbackQuery = new CallbackQuery();
    private Message message = new Message();
    private PreCheckoutQuery preCheckoutQuery = new PreCheckoutQuery();

    private BotApiMethod<?> botApiMethod;

    private ButtonClickHandler buttonClickHandler = mock(ButtonClickHandler.class);
    private MessageHandler messageHandler = mock(MessageHandler.class);
    private PreCheckoutQueryHandler preCheckoutQueryHandler = mock(PreCheckoutQueryHandler.class);

    @Test
    void handleCallbackQuery() {

        // Arrange
        String callbackId = "10500";
        String callbackData = "testData";

        callbackQuery.setId(callbackId);
        callbackQuery.setData(callbackData);

        // Act
        buttonClickHandler.handleCallback(callbackQuery, eq(any()));

        ArgumentCaptor<CallbackQuery> callbackArgumentCaptor = ArgumentCaptor.forClass(CallbackQuery.class);

        verify(buttonClickHandler).handleCallback(callbackArgumentCaptor.capture(), any());

        CallbackQuery value = callbackArgumentCaptor.getValue();

        // Assert
        verify(buttonClickHandler, times(1)).handleCallback(any(), any());

        assertThat(value.getId()).isEqualTo(callbackId);
        assertThat(value.getData()).isEqualTo(callbackData);
    }

    @Test
    public void handleMessage() {

        //Arrange
        String messageText = "testText";
        Integer messageId = 100;

        message.setMessageId(messageId);
        message.setText(messageText);

        //Act
        messageHandler.sortedMessage(message, eq(any()));

        ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);

        verify(messageHandler).sortedMessage(messageArgumentCaptor.capture(), any());

        Message value = messageArgumentCaptor.getValue();

        //Assert
        verify(messageHandler, times(1)).sortedMessage(any(), any());

        assertThat(value.getMessageId()).isEqualTo(messageId);
        assertThat(value.getText()).isEqualTo(messageText);
    }

    @Test
    public void handlePreCheckout() {
        //Arrange
        String id = "150";
        String currency = "200";
        String invoicePayload = "250";

        preCheckoutQuery.setId(id);
        preCheckoutQuery.setCurrency(currency);
        preCheckoutQuery.setFrom(new User());
        preCheckoutQuery.setInvoicePayload(invoicePayload);

        //Act
        preCheckoutQueryHandler.handlePreCheckoutQuery(preCheckoutQuery);

        ArgumentCaptor<PreCheckoutQuery> preCheckoutQueryArgumentCaptor = ArgumentCaptor.forClass(PreCheckoutQuery.class);

        verify(preCheckoutQueryHandler).handlePreCheckoutQuery(preCheckoutQueryArgumentCaptor.capture());

        PreCheckoutQuery value = preCheckoutQueryArgumentCaptor.getValue();

        //Assert
        verify(preCheckoutQueryHandler,times(1)).handlePreCheckoutQuery(any());

        assertThat(value.getId()).isEqualTo(id);
        assertThat(value.getInvoicePayload()).isEqualTo(invoicePayload);
    }

}