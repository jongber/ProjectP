package com.jongber.projectp.object.component;

import com.jongber.projectp.object.GameObject;

public class GameLogicComponent {

    public interface LogicImpl {
        void create(GameObject owner);
        void update();
    }

    private GameObject owner;
    private LogicImpl logicImpl;

    public GameLogicComponent(GameObject owner, LogicImpl logicImpl) {
        this.logicImpl = logicImpl;
        this.owner = owner;
    }

    public void create(GameObject owner) {
        this.owner = owner;
    }

    public void update() {
        logicImpl.update();
    }
}
