package ru.ws.marketplace.state;

import ru.ws.marketplace.enumstate.DialogueStateEnum;
import ru.ws.marketplace.interfacestate.DialogueState;

public class End implements DialogueState {

    @Override
    public void previousState(DialogueContext context) {
        context.setState(new Link());
    }

    @Override
    public void nextState(DialogueContext context) {
        System.out.println("Next state not found");
    }

    @Override
    public String getStatus() {
        return DialogueStateEnum.END.getStatus();
    }
}
