package ru.ws.marketplace.status;

public class Description implements DialogueState {
    @Override
    public void previousState(DialogueContext context) {
        context.setState(new Name());
    }

    @Override
    public void nextState(DialogueContext context) {
        context.setState(new Link());
    }

    @Override
    public String getStatus() {
        return DialogueStateEnum.DESCRIPTION.getStatus();
    }
}
