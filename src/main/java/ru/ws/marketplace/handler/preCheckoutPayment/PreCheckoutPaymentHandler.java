package ru.ws.marketplace.handler.preCheckoutPayment;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import ru.ws.marketplace.service.impl.CRUDChannelServiceImpl;

@Component
public class PreCheckoutPaymentHandler {

    private final CRUDChannelServiceImpl crudChannelService;

    public PreCheckoutPaymentHandler(CRUDChannelServiceImpl crudChannelService) {
        this.crudChannelService = crudChannelService;
    }

    public boolean resultPreCheckout(PreCheckoutQuery preCheckoutQuery) {

        String invoicePayload = preCheckoutQuery.getInvoicePayload();

        return crudChannelService.get(Long.valueOf(invoicePayload)) != null;
    }
}
