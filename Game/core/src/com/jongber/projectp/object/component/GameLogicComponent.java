package com.jongber.projectp.object.component;

import com.jongber.projectp.game.World;
import com.jongber.projectp.object.GameObject;

public class GameLogicComponent {

    public interface LogicImpl {
        void init();
        void update(World world, GameObject owner, float elapsed);
    }

    private LogicImpl logicImpl;

    public GameLogicComponent(LogicImpl logicImpl) {
        this.logicImpl = logicImpl;
    }

    public void init() {
        this.logicImpl.init();
    }

    public void update(World world, GameObject owner, float elapsed) {
        logicImpl.update(world, owner, elapsed);
    }
}
