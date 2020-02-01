package com.jongber.game.core.event;

public abstract class GameEvent {

    public interface Callback {
        void callback(GameEvent event);
    }

    protected Callback callback = null;

    public GameEvent() {}

    public GameEvent(Callback callback) {
        this.callback = callback;
    }

    public void handleWithCallback() {

        this.handle();

        if (this.callback != null) {
            this.callback.callback(this);
        }
    }

    public abstract void handle();
}
