package ru.ws.marketplace.state;

public class Name implements DialogueState {
    @Override
    public void previousState(DialogueContext context) {
        System.out.println("Previous state not found");
    }

    @Override
    public void nextState(DialogueContext context) {
        context.setState(new Description());
    }

    @Override
    public String getStatus() {
        return DialogueStateEnum.NAME.getStatus();
    }
}
