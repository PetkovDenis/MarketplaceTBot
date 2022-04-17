package ru.ws.marketplace.init.command;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.ws.marketplace.state.dialog.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class MapCommandState {

    private Map<String, DialogueState> commandMap = new HashMap<>();

    {
        commandMap.put("NAME", new Name());
        commandMap.put("CATEGORY", new Category());
        commandMap.put("DESCRIPTION", new Description());
        commandMap.put("LINK", new Link());
        commandMap.put("PRICE", new Price());
        commandMap.put("END", new End());
    }
}
// Реализовать аннотацию Qualifier
