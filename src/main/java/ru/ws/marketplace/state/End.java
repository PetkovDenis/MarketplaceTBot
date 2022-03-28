package ru.ws.marketplace.state;

public class End implements DialogueState {

    @Override
    public void previousState(DialogueContext context) {
        context.setState(new Price());
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
