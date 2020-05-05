package com.jongber.game.desktop.common;

import com.jongber.game.core.event.GameEvent;

public class CallbackEvent extends GameEvent {

    public interface Callback {
        void invoke();
    }

    protected Callback callback;

    public CallbackEvent(Callback callback) {
        this.callback = callback;
    }


    @Override
    public void handle() {
        this.callback.invoke();
    }
}
