package com.jongber.projectp.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.projectp.asset.GameAsset;
import com.jongber.projectp.asset.json.GameObjectJson;
import com.jongber.projectp.asset.json.GameSettingJson;
import com.jongber.projectp.asset.SpriteAsset;
import com.jongber.projectp.asset.StaticTextureAsset;
import com.jongber.projectp.asset.json.AsepriteJson;
import com.jongber.projectp.common.Traverser;
import com.jongber.projectp.game.World;
import com.jongber.projectp.graphics.OrthoCameraWrapper;
import com.jongber.projectp.graphics.VFAnimation;
import com.jongber.projectp.object.GameObject;
import com.jongber.projectp.object.component.SceneryComponent;
import com.jongber.projectp.object.component.SpriteComponent;
import com.jongber.projectp.object.method.RenderMethod;

import java.util.ArrayList;
import java.util.List;

public class ObjectTest extends ApplicationAdapter {
    SpriteBatch batch;
    World world;

    @Override
    public void create () {
        batch = new SpriteBatch();

        GameSettingJson json = GameSettingJson.load();
        world = GameAsset.inflate(json, "Stage1/stage1_define.json");

    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.world.camera.update(batch);
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
                RenderMethod.renderSprite(batch, item, Gdx.graphics.getDeltaTime());
            }
        });

        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        GameAsset.dispose();
//        this.asset.dispose();
//        this.textureAsset2.dispose();
//        this.textureAsset.dispose();
    }

    @Override
    public void resize(int width, int height){
        //this.camera.resize(width, height);
    }
}