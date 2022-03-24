package ru.ws.marketplace.status;

public interface DialogueState {

    void previousState(DialogueContext context);

    void nextState(DialogueContext context);

    String getStatus();
}
