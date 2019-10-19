package com.jongber.projectp.object.component;

import com.jongber.projectp.object.GameObject;

public class GameLoopComponent {

    public interface LoopRunner {
        void update(GameObject owner);
    }

    private GameObject owner;
    private LoopRunner loopRunner;

    public GameLoopComponent(GameObject owner, LoopRunner loopRunner) {
        this.loopRunner = loopRunner;
        this.owner = owner;
    }

    public void update() {
        loopRunner.update(this.owner);
    }
}
