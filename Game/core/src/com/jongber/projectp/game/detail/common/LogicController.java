package com.jongber.projectp.game.detail.common;

import com.jongber.projectp.object.GameObject;

public interface LogicController {
    void update(float elapsed);
    void collide(GameObject target1, GameObject target2);
}
