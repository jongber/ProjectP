package com.jongber.projectp.oldaction.game.detail.common;

import com.jongber.projectp.oldaction.object.GameObject;

public interface LogicController {
    void update(float elapsed);
    void collide(GameObject target1, GameObject target2);
}
