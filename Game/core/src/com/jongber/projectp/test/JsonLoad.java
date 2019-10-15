package com.jongber.projectp.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.projectp.asset.AssetLoader;
import com.jongber.projectp.asset.SpriteAsset;
import com.jongber.projectp.asset.aseprite.AsepriteJson;
import com.jongber.projectp.graphics.VFAnimation;

public class JsonLoad extends ApplicationAdapter {
    SpriteBatch batch;
    SpriteAsset asset;
    VFAnimation animation = new VFAnimation();

    @Override
    public void create () {
        batch = new SpriteBatch();

        AsepriteJson json = AsepriteJson.load("object/hero.json");
        asset = AssetLoader.loadSprite("hero", json);
        this.animation.init(this.asset.getAnimation("Idle"), VFAnimation.PlayMode.LOOP);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        TextureRegion region = this.animation.getNext(Gdx.graphics.getDeltaTime());
        batch.draw(region, 10, 10);

        batch.end();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.asset.dispose();
    }

}