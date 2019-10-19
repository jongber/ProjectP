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
    private SpriteAsset asset;
    private StaticTextureAsset textureAsset;
    private StaticTextureAsset textureAsset2;
    OrthoCameraWrapper camera;
    GameObject object;
    GameObject scenery;
    GameObject sky;

    float elapsed = 0.0f;

    @Override
    public void create () {
        batch = new SpriteBatch();

//        GameSettingJson json = GameSettingJson.load();
//        this.camera = new OrthoCameraWrapper(json.viewport.w, json.viewport.h);
//
//        AsepriteJson aseJson = AsepriteJson.load("object/hero.json");
//        this.asset = GameAsset.loadSprite("hero", aseJson);
//
//        this.object = new GameObject("hero");
//        this.object.addComponent(SpriteComponent.class, new SpriteComponent(this.asset));
//
//        this.object.getTransform().y -= 24;
//
//        try {
//            SpriteComponent component = this.object.getComponent(SpriteComponent.class);
//            component.setAnimation("Walk", VFAnimation.PlayMode.LOOP);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ///
//        aseJson = AsepriteJson.load("stage1/Stage1_bottom.json");
//        this.textureAsset = GameAsset.loadTexture("bottom", aseJson);
//
//        this.scenery = new GameObject("stage1_bottom");
//        this.scenery.addComponent(SceneryComponent.class, new SceneryComponent());
//
//        try {
//            SceneryComponent component = this.scenery.getComponent(SceneryComponent.class);
//            component.setSceneryImage(this.textureAsset, 1.0f);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        aseJson = AsepriteJson.load("stage1/Stage1_sky.json");
//        this.textureAsset2 = GameAsset.loadTexture("sky", aseJson);
//
//        this.sky = new GameObject("stage1_sky");
//        this.sky.addComponent(SceneryComponent.class, new SceneryComponent());
//
//        try {
//            SceneryComponent component = this.sky.getComponent(SceneryComponent.class);
//            component.setSceneryImage(this.textureAsset2, 1.0f);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        GameObjectJson objectJson = GameObjectJson.load("sky_define.json");
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        this.elapsed += Gdx.graphics.getDeltaTime();
//
//        this.camera.update(this.batch);

        batch.begin();

//        RenderMethod.renderScenery(batch, this.sky, this.camera);
//        RenderMethod.renderScenery(batch, this.scenery, this.camera);
//        RenderMethod.renderSprite(batch, this.object, 0.016f);
//
//        int count = 0;
//        while (this.elapsed >= 0.016666f) {
//            this.scenery.getTransform().x -= 10f * 0.016666f;
//            this.elapsed-= 0.016666f;
//        }

        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
//        this.asset.dispose();
//        this.textureAsset2.dispose();
//        this.textureAsset.dispose();
    }

    @Override
    public void resize(int width, int height){
        //this.camera.resize(width, height);
    }
}