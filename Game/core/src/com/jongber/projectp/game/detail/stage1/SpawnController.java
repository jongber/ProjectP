package com.jongber.projectp.game.detail.stage1;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jongber.projectp.asset.GameAsset;
import com.jongber.projectp.game.World;
import com.jongber.projectp.object.GameObject;

public class SpawnController {

    private final float SpawnTime = 5.0f;

    private World world;
    private float elapsed;

    public SpawnController(World world) {
        this.world = world;
    }

    public void update(float elapsed) {
        this.elapsed += elapsed;

        if (this.elapsed >= SpawnTime && this.world.gameObjectCount() < 2) {
            GameObject zombie = GameAsset.inflate("stage1/zombie_define.json");

            Vector2 spawnPos = new Vector2(world.camera.getPosition().x, world.camera.getPosition().y);
            spawnPos.y = -24;
            spawnPos.x += World.Setting.viewport.w / 2;

            zombie.setTransform(spawnPos);

            this.world.addObject(zombie);
        }
    }
}
