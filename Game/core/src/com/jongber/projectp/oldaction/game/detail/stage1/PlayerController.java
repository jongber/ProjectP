package com.jongber.projectp.oldaction.game.detail.stage1;

import com.jongber.projectp.oldaction.game.World;
import com.jongber.projectp.oldaction.game.detail.common.LogicController;
import com.jongber.projectp.oldaction.graphics.VFAnimation;
import com.jongber.projectp.oldaction.object.GameObject;
import com.jongber.projectp.oldaction.object.component.SpriteComponent;

public class PlayerController implements LogicController {
    private final float Speed = 15.0f;

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
        if (this.hero.collisionQ.isEmpty()) {
            this.move(elapsed);
            //Gdx.app.log("DEBUG", "player move[" + (int)(elapsed * 1000.0f) + "]ms");
        }
        else {
            stop();
            //Gdx.app.log("DEBUG", "player stop[" + (int)(elapsed * 1000.0f) + "]ms");
        }

        this.hero.collisionQ.clear();
    }

    @Override
    public void collide(GameObject target1, GameObject target2) {
        if (target1.getId() != this.hero.getId() && target2.getId() != this.hero.getId()) {
            return;
        }

        GameObject enemy = target1;
        if (target1.getId() == this.hero.getId()) {
            enemy = target2;
        }

        this.hero.collisionQ.add(enemy);
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
