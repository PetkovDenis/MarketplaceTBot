package ru.ws.marketplace.state;

public class Price implements DialogueState {

    @Override
    public void previousState(DialogueContext context) {
        context.setState(new Link());
    }

    @Override
    public void nextState(DialogueContext context) {
        System.out.println(new End());
    }

    @Override
    public String getStatus() {
        return DialogueStateEnum.PRICE.getStatus();
    }
}
