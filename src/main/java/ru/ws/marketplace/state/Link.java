package ru.ws.marketplace.state;

public class Link implements DialogueState {

    @Override
    public void previousState(DialogueContext context) {
        context.setState(new Description());
    }

    @Override
    public void nextState(DialogueContext context) {
        context.setState(new Price());
    }

    @Override
    public String getStatus() {
        return DialogueStateEnum.LINK.getStatus();
    }
}
