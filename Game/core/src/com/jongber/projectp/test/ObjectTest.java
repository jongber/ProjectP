package com.jongber.projectp.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.projectp.asset.AssetLoader;
import com.jongber.projectp.asset.GameSettingJson;
import com.jongber.projectp.asset.SpriteAsset;
import com.jongber.projectp.asset.aseprite.AsepriteJson;
import com.jongber.projectp.graphics.OrthoCameraWrapper;
import com.jongber.projectp.graphics.VFAnimation;
import com.jongber.projectp.object.GameObject;
import com.jongber.projectp.object.data.SpriteComponent;

public class ObjectTest extends ApplicationAdapter {
    SpriteBatch batch;
    private SpriteAsset asset;
    OrthoCameraWrapper camera;
    GameObject object;

    @Override
    public void create () {
        batch = new SpriteBatch();

        GameSettingJson json = GameSettingJson.load();
        this.camera = new OrthoCameraWrapper(json.viewport.w, json.viewport.h);

        AsepriteJson aseJson = AsepriteJson.load("object/hero.json");
        this.asset = AssetLoader.loadSprite("hero", aseJson);

        this.object = new GameObject("hero");
        this.object.addComponent(SpriteComponent.class, new SpriteComponent(this.asset));

        try {
            SpriteComponent component = this.object.getComponent(SpriteComponent.class);
            component.setAnimation("Idle", VFAnimation.PlayMode.LOOP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update(this.batch);

        float elapsed = Gdx.graphics.getDeltaTime();

        SpriteComponent component = this.object.getComponent(SpriteComponent.class);
        TextureRegion region = component.getNext(elapsed);

        batch.begin();

        this.batch.draw(region, 0, 0);

        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        this.asset.dispose();
    }
}