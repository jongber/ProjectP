package com.jongber.projectp.game.detail.stage1;

import com.jongber.projectp.common.Traverser;
import com.jongber.projectp.game.World;
import com.jongber.projectp.graphics.VFAnimation;
import com.jongber.projectp.object.GameObject;
import com.jongber.projectp.object.component.SpriteComponent;

import java.util.ArrayDeque;
import java.util.Queue;

public class PlayerController {
    private final float Speed = 12.0f;

    private Queue<GameObject> collisions = new ArrayDeque<>();
    private World world;
    private GameObject hero;



    public PlayerController(World world, GameObject object) {
        this.hero = object;
        this.world = world;
    }

    public void touchDown() {
        SpriteComponent sprite = hero.getComponent(SpriteComponent.class);
        if (sprite.isAnimation("Walk"))
            return;

        this.attackStart();
    }

    public void update(float elapsed) {
        if (collisions.isEmpty()) {
            this.move(elapsed);
        }
        else {
            stop();
        }
    }

    public void collide(GameObject target) {
        collisions.add(target);
    }

    private void attackStart() {
        SpriteComponent sprite = hero.getComponent(SpriteComponent.class);
        if (sprite.isAnimation("Attack1") == false) {
            sprite.setAnimation("Attack1", VFAnimation.PlayMode.ONCE);
        }
    }

    private void stop() {
        SpriteComponent sprite = hero.getComponent(SpriteComponent.class);
        if (sprite.isAnimation("Attack1") && sprite.isFinished() == false)
            return;

        if (sprite.isAnimation("Idle") == false) {
            sprite.setAnimation("Idle", VFAnimation.PlayMode.LOOP);
        }
    }

    private void move(float elapsed) {
        SpriteComponent sprite = hero.getComponent(SpriteComponent.class);
        if (sprite.isAnimation("Walk") == false) {
            sprite.setAnimation("Walk", VFAnimation.PlayMode.LOOP);
        }

        this.hero.getTransform().x += Speed * elapsed;
        this.world.camera.getCamera().position.x += Speed * elapsed;
    }
}
