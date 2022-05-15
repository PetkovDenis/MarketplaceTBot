package ru.ws.marketplace.init.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

@Getter
@AllArgsConstructor(staticName = "of")
public class UpdateProcessor {
    private  Function<Update, Boolean> matcher;
    private  Function<Update, BotApiObject> extractor;
    private  Function<BotApiObject, BotApiMethod<?>> processor;
}
