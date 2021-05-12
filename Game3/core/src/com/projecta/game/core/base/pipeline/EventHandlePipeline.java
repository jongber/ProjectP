package com.projecta.game.core.base.pipeline;

import java.util.ArrayDeque;
import java.util.Queue;

public class EventHandlePipeline extends GamePipeline implements GamePipeline.Updater {

    public interface EventMessage {
        void onHandle();
    }

    private Queue<EventMessage> list = new ArrayDeque<>();

    public void request(EventMessage msg) {
        synchronized (this.list) {
            this.list.add(msg);
        }
    }

    @Override
    public void update(float elapsed) {
        synchronized (this.list) {
            for (EventMessage msg : this.list) {
                msg.onHandle();
            }

            this.list.clear();
        }
    }

    @Override
    public void dispose() {
    }
}
