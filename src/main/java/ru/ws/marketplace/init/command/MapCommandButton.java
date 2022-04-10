package ru.ws.marketplace.init.command;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.ws.marketplace.state.button.AddResource;
import ru.ws.marketplace.state.button.ButtonState;
import ru.ws.marketplace.state.button.Subscribe;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class MapCommandButton {
    Map<String, ButtonState> commandMap = new HashMap<>();

    {
        commandMap.put("buttonSubscribe", new Subscribe());
        commandMap.put("buttonAddResources", new AddResource());
    }
}
