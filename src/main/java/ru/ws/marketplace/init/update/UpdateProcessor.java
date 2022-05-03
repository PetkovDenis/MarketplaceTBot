package ru.ws.marketplace.init.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

@Getter
@AllArgsConstructor(staticName = "of")
public class UpdateProcessor {
    private final Function<Update, Boolean> matcher;
    private final Function<Update, BotApiObject> extractor;
    private final Function<BotApiObject, BotApiMethod<?>> processor;
}
