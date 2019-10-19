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
import com.jongber.projectp.graphics.OrthoCameraWrapper;
import com.jongber.projectp.graphics.VFAnimation;
import com.jongber.projectp.object.GameObject;
import com.jongber.projectp.object.component.SceneryComponent;
import com.jongber.projectp.object.component.SpriteComponent;
import com.jongber.projectp.object.method.RenderMethod;

public class ObjectTest extends ApplicationAdapter {
    SpriteBatch batch;
    OrthoCameraWrapper camera;
    GameObject hero;
    GameObject sky;

    @Override
    public void create () {
        batch = new SpriteBatch();

        GameSettingJson json = GameSettingJson.load();
        this.camera = new OrthoCameraWrapper(json.viewport.w, json.viewport.h);

        hero = GameAsset.inflate("hero_define.json");
        SpriteComponent comp = hero.getComponent(SpriteComponent.class);
        try {
            comp.setAnimation("Walk", VFAnimation.PlayMode.LOOP);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sky = GameAsset.inflate("sky_define.json");
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update(this.batch);

        batch.begin();

        RenderMethod.renderScenery(batch, sky, camera);
        RenderMethod.renderSprite(batch, hero, Gdx.graphics.getDeltaTime());

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