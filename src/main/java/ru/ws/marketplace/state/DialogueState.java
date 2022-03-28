package ru.ws.marketplace.state;

public interface DialogueState {

    void previousState(DialogueContext context);

    void nextState(DialogueContext context);

    String getStatus();
}
