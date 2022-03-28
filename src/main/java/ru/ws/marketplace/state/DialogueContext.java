package ru.ws.marketplace.state;

import org.springframework.stereotype.Component;

@Component
public class DialogueContext {

    DialogueState state;

    public void setState(DialogueState state) {
        this.state = state;
    }

    public DialogueState getState() {
        return state;
    }

    public void previousState() {
        state.previousState(this);
    }

    public void nextState() {
        state.nextState(this);
    }

    public String getStatusName() {
        return state.getStatus();
    }

}
