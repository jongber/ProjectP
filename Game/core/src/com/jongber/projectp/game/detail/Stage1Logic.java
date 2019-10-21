package com.jongber.projectp.game.detail;

import com.jongber.projectp.game.World;
import com.jongber.projectp.graphics.VFAnimation;
import com.jongber.projectp.object.GameObject;
import com.jongber.projectp.object.component.GameLogicComponent;
import com.jongber.projectp.object.component.SpriteComponent;

public class Stage1Logic implements GameLogicComponent.LogicImpl {
    @Override
    public void init() {
    }

    @Override
    public void update(World world, GameObject owner, float elapsed) {

        SpriteComponent sprite = owner.getComponent(SpriteComponent.class);
        if (sprite != null && sprite.getAnimationName().compareTo("Walk") != 0) {
            try {
                sprite.setAnimation("Walk", VFAnimation.PlayMode.LOOP);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        world.camera.getPosition().x += 10.0 * 0.016666;
        owner.getTransform().x += 10.0 * 0.016666;
    }
}
