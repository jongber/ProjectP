package com.jongber.game.core.event;

import java.util.ArrayDeque;
import java.util.Queue;

public class GameEventHandler {

    private Queue<GameEvent> queue = new ArrayDeque<>();

    public void post(GameEvent event) {
        synchronized (this) {
            this.queue.add(event);
        }
    }

    public void handle() {
        synchronized (this) {
            while (this.queue.peek() != null) {
                GameEvent event = this.queue.remove();
                event.handleWithCallback();
            }
        }
    }
}
