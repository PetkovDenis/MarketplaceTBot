package ru.ws.marketplace.handler.update;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.ws.marketplace.init.update.UpdateProcessor;
import ru.ws.marketplace.init.update.UpdateStateInitialization;

@Component
@AllArgsConstructor
public class UpdateHandler {

    UpdateStateInitialization updateStateInitialization;

    public BotApiMethod<?> execute(Update update) {
        for (UpdateProcessor telegramUpdateProcessor : updateStateInitialization.executeUpdate(update)) {
            Boolean match = telegramUpdateProcessor.getMatcher().apply(update);
            if (match) {
                BotApiObject object = telegramUpdateProcessor.getExtractor().apply(update);
                return telegramUpdateProcessor.getProcessor().apply(object);
            }
        }
        return new SendMessage(update.getUpdateId().toString(), "Данная функция недоступна");
    }
}
