package ru.ws.marketplace.state.button;

import org.springframework.stereotype.Component;


@Component
public class ButtonContext {

    ButtonState state;

    public void setState(ButtonState state) {
        this.state = state;
    }

    public ButtonState getState() {
        return state;
    }

}
