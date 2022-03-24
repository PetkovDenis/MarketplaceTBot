package ru.ws.marketplace.status;

public class Link implements DialogueState {
    @Override
    public void previousState(DialogueContext context) {
        context.setState(new Description());
    }

    @Override
    public void nextState(DialogueContext context) {
        System.out.println("Next state not found");
    }

    @Override
    public String getStatus() {
        return DialogueStateEnum.LINK.getStatus();
    }
}
