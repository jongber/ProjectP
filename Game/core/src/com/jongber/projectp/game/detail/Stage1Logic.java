package com.jongber.projectp.game.detail;

import com.jongber.projectp.common.JoinTraverser;
import com.jongber.projectp.common.Traverser;
import com.jongber.projectp.game.World;
import com.jongber.projectp.game.detail.stage1.PlayerController;
import com.jongber.projectp.game.detail.stage1.NPCController;
import com.jongber.projectp.graphics.VFAnimation;
import com.jongber.projectp.object.GameObject;
import com.jongber.projectp.object.component.SpriteComponent;

public class Stage1Logic implements World.WorldLogic, JoinTraverser<GameObject> {

    private GameObject player;

    private PlayerController playerController;
    private NPCController NPCController;

    @Override
    public void init(World world) {

        world.forObjects(new Traverser<GameObject>() {
            @Override
            public void onTraverse(GameObject item) {
                Stage1Logic.this.player = item;
            }
        });

        this.playerController = new PlayerController(world, this.player);
        this.NPCController = new NPCController(world);
    }

    @Override
    public void update(World world, float elapsed) {
        world.forJoinObjects(this);

        this.playerController.update(elapsed);
        this.NPCController.update(elapsed);
    }

    @Override
    public void touchDown(int screenX, int screenY, int pointer, int button) {
        this.playerController.touchDown();
    }

    @Override
    public void onJoin(GameObject main, GameObject target) {
        if (this.player.getId() == main.getId()) {
            float dist = Math.abs(main.getTransform().x - target.getTransform().x);
            if (dist <= World.Setting.collisionDist) {
                this.playerController.collide(target);
            }
        }
    }
}
