package com.jongber.projectp.game.detail.stage1;

import com.badlogic.gdx.math.Vector2;
import com.jongber.projectp.asset.GameAsset;
import com.jongber.projectp.common.PackedArray;
import com.jongber.projectp.game.World;
import com.jongber.projectp.game.detail.common.LogicController;
import com.jongber.projectp.graphics.VFAnimation;
import com.jongber.projectp.object.GameObject;
import com.jongber.projectp.object.component.SpriteComponent;

import java.util.Iterator;

public class NPCController implements LogicController {

    private final float SpawnTime = 5.0f;

    private World world;
    private float elapsed;
    private PackedArray spawnedList = new PackedArray();

    public NPCController(World world) {
        this.world = world;
    }

    public void update(float elapsed) {
        this.elapsed += elapsed;

        this.trySpawn();

        Object[] arr = this.spawnedList.getArray();
        int size = this.spawnedList.size();
        for (int i = 0; i < size; ++i) {
            GameObject npc = (GameObject)arr[i];

            if (npc.collisionQ.isEmpty()) {
                this.move(npc, elapsed);
            }
            else {
                this.stop(npc);
            }

            npc.collisionQ.clear();
        }
    }

    @Override
    public void collide(GameObject target1, GameObject target2) {
        if (this.spawnedList.isContained(target1)) {
            target1.collisionQ.add(target2);
        }

        if (this.spawnedList.isContained(target2)) {
            target2.collisionQ.add(target1);
        }
    }

    private void trySpawn() {
        if (this.elapsed >= SpawnTime && this.spawnedList.size() < 1) {
            GameObject zombie = GameAsset.inflate("stage1/zombie_define.json");

            Vector2 spawnPos = new Vector2(world.camera.getPosition().x, world.camera.getPosition().y);
            spawnPos.y = -24;
            spawnPos.x += World.Setting.viewport.w / 2;

            zombie.setTransform(spawnPos);

            this.world.addObject(zombie);

            this.spawnedList.add(zombie);
        }
    }

    private void move(GameObject npc, float elapsed) {
        SpriteComponent.changeAnimation(npc, "Walk", VFAnimation.PlayMode.LOOP);
        npc.getTransform().x -= 10.0f * elapsed;
    }

    private void stop(GameObject npc) {
        SpriteComponent.changeAnimation(npc, "Idle", VFAnimation.PlayMode.LOOP);
    }
}
