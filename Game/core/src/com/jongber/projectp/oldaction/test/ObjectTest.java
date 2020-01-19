package com.jongber.projectp.oldaction.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.projectp.oldaction.asset.GameAsset;
import com.jongber.projectp.oldaction.asset.json.GameSettingJson;
import com.jongber.projectp.oldaction.common.Traverser;
import com.jongber.projectp.oldaction.game.World;
import com.jongber.projectp.oldaction.object.GameObject;
import com.jongber.projectp.oldaction.object.method.RenderMethod;

public class ObjectTest extends ApplicationAdapter {
    SpriteBatch batch;
    World world;

    @Override
    public void create () {
        batch = new SpriteBatch();

        GameSettingJson json = GameSettingJson.load();
        world = GameAsset.inflate(json, "stage1/stage1_define.json");
        world.init();

        GameAsset.inflate("stage1/zombie_define.json");

        Gdx.input.setInputProcessor(world);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //long elapsed = System.currentTimeMillis();

        this.world.update(this.batch, Gdx.graphics.getDeltaTime());
        //Gdx.app.log("DEBUG", "world update elapsed:" + (System.currentTimeMillis() - elapsed));

        ///elapsed = System.currentTimeMillis();
        batch.begin();

        world.forSceneries(new Traverser<GameObject>() {
            @Override
            public void onTraverse(GameObject item) {
                RenderMethod.renderScenery(batch, item, world.camera);
            }
        });

        world.forObjects(new Traverser<GameObject>() {
            @Override
            public void onTraverse(GameObject item) {
                RenderMethod.renderSprite(0, batch, item, Gdx.graphics.getDeltaTime());
            }
        });

        world.forObjects(new Traverser<GameObject>() {
            @Override
            public void onTraverse(GameObject item) {
                RenderMethod.renderSprite(1, batch, item, Gdx.graphics.getDeltaTime());
            }
        });

        //Gdx.app.log("DEBUG", "render elapsed:" + (System.currentTimeMillis() - elapsed));
        ////Gdx.app.log("DEBUG", "render elapsed:" + (Gdx.graphics.getDeltaTime()));

        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        this.world.dispose();
    }

    @Override
    public void resize(int width, int height){
        this.world.camera.resize(width, height);
    }
}