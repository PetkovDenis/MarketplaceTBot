package ru.ws.marketplace.interfacestate;

import ru.ws.marketplace.state.DialogueContext;

public interface DialogueState {

    void previousState(DialogueContext context);

    void nextState(DialogueContext context);

    String getStatus();
}
