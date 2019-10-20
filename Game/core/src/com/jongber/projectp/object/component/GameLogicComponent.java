package com.jongber.projectp.object.component;

import com.jongber.projectp.game.World;
import com.jongber.projectp.object.GameObject;

public class GameLogicComponent {

    public interface LogicImpl {
        void update(World world, GameObject owner);
    }

    private LogicImpl logicImpl;

    public GameLogicComponent(LogicImpl logicImpl) {
        this.logicImpl = logicImpl;
    }

    public void update(World world, GameObject owner) {
        logicImpl.update(world, owner);
    }
}
