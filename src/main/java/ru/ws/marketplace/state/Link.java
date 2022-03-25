package ru.ws.marketplace.state;

import ru.ws.marketplace.enumstate.DialogueStateEnum;
import ru.ws.marketplace.interfacestate.DialogueState;

public class Link implements DialogueState {

    @Override
    public void previousState(DialogueContext context) {
        context.setState(new Description());
    }

    @Override
    public void nextState(DialogueContext context) {
        context.setState(new End());
    }

    @Override
    public String getStatus() {
        return DialogueStateEnum.LINK.getStatus();
    }
}
