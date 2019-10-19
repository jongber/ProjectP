package com.jongber.projectp.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.projectp.asset.AssetLoader;
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
    private SpriteAsset asset;
    private StaticTextureAsset textureAsset;
    OrthoCameraWrapper camera;
    GameObject object;
    GameObject scenery;

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
            component.setAnimation("Walk", VFAnimation.PlayMode.LOOP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ///
        aseJson = AsepriteJson.load("stage1/Stage1_bottom.json");
        this.textureAsset = AssetLoader.loadTexture("bottom", aseJson);

        this.scenery = new GameObject("stage1_sky");
        this.scenery.addComponent(SceneryComponent.class, new SceneryComponent());

        try {
            SceneryComponent component = this.scenery.getComponent(SceneryComponent.class);
            component.setSceneryImage(this.textureAsset, 1.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GameObjectJson objectJson = GameObjectJson.load("hero_define.json");
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.camera.update(this.batch);

        float elapsed = Gdx.graphics.getDeltaTime();

        batch.begin();

        RenderMethod.renderScenery(batch, this.scenery, this.camera);
        RenderMethod.renderSprite(batch, this.object, elapsed);

        this.object.getTransform().x += 0.6f;
        this.camera.getCamera().position.x += 0.6f;

        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        this.asset.dispose();
        this.textureAsset.dispose();
    }
}