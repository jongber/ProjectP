package com.projecta.game.core.base.layer;

import java.util.ArrayDeque;
import java.util.Queue;

public class GameLayerMessageHandler {
    private Queue<GameLayerMessage> queue = new ArrayDeque<>();

    public void post(GameLayerMessage event) {
        synchronized (this) {
            this.queue.add(event);
        }
    }

    public void handle(GameLayer layer) {
        synchronized (this) {
            while (this.queue.peek() != null) {
                GameLayerMessage event = this.queue.remove();
                event.handle(layer);
            }
        }
    }
}
