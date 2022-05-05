package ru.ws.marketplace.handler.preCheckout;


import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PreCheckoutQueryHandlerTest {

    private PreCheckoutQuery preCheckoutQuery = new PreCheckoutQuery();

    PreCheckoutQueryHandler preCheckoutQueryHandler = mock(PreCheckoutQueryHandler.class);

    private String preCheckoutQueryId = "100";
    private String invoicePayload = "text";

    @Test
    void handlePreCheckoutQuery() {
        //Arrange
        preCheckoutQuery.setId(preCheckoutQueryId);
        preCheckoutQuery.setInvoicePayload(invoicePayload);

        //Act
        preCheckoutQueryHandler.handlePreCheckoutQuery(preCheckoutQuery);

        ArgumentCaptor<PreCheckoutQuery> checkoutQueryArgumentCaptor = ArgumentCaptor.forClass(PreCheckoutQuery.class);

        verify(preCheckoutQueryHandler).handlePreCheckoutQuery(checkoutQueryArgumentCaptor.capture());

        PreCheckoutQuery value = checkoutQueryArgumentCaptor.getValue();
        //Assert
        verify(preCheckoutQueryHandler, times(1)).handlePreCheckoutQuery(any());

        assertThat(value.getInvoicePayload()).isEqualTo(invoicePayload);
        assertThat(value.getId()).isEqualTo(preCheckoutQueryId);
    }
}