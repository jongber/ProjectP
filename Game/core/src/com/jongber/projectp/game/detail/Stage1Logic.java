package com.jongber.projectp.game.detail;

import com.jongber.projectp.common.Traverser;
import com.jongber.projectp.game.World;
import com.jongber.projectp.graphics.VFAnimation;
import com.jongber.projectp.object.GameObject;
import com.jongber.projectp.object.component.SpriteComponent;

public class Stage1Logic implements World.WorldLogic{

    private GameObject player;
    private String beforeAttack = "Walk";
    private boolean touched = true;

    @Override
    public void init(World world) {

        world.forObjects(new Traverser<GameObject>() {
            @Override
            public void onTraverse(GameObject item) {
                Stage1Logic.this.player = item;
            }
        });
    }

    @Override
    public void update(World world, float elapsed) {
        this.checkPlayerState();
    }

    @Override
    public void touchDown(int screenX, int screenY, int pointer, int button) {
        SpriteComponent component = this.player.getComponent(SpriteComponent.class);
        if (component.isAnimation("Attack1") == false) {
            this.beforeAttack = component.getAnimationName();
            component.setAnimation("Attack1", VFAnimation.PlayMode.ONCE);
        }
    }

    private void checkPlayerState() {
        SpriteComponent component = this.player.getComponent(SpriteComponent.class);
        if (component.isAnimation("Attack1") && component.isFinished()){
            component.setAnimation(this.beforeAttack, VFAnimation.PlayMode.LOOP);
        }
    }
}
