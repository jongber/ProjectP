package com.jongber.projectp.game.detail.stage1;

import com.jongber.projectp.common.Traverser;
import com.jongber.projectp.game.World;
import com.jongber.projectp.object.GameObject;

public class PlayerController {
    private World world;
    private GameObject hero;

    public PlayerController(World world, GameObject object) {
        this.hero = object;
        this.world = world;
    }

    public void touchDown() {

    }

    public void update(float elapsed) {

    }

    private void move(float elapsed) {
        this.hero.getTransform().x += 10.0f * elapsed;
        this.world.camera.getCamera().position.x += 10.0f * elapsed;
    }

}
