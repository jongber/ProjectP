package com.jongber.projectp.object.component;

public class GameLoopComponent {
    public interface LoopRunner {
        void update();
    }

    private LoopRunner loopRunner;

    public GameLoopComponent(LoopRunner loopRunner) {
        this.loopRunner = loopRunner;
    }

    public void update() {
        loopRunner.update();
    }
}
