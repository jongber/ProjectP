package com.jongber.game.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.controller.TextureRenderer;

public class MapEditor extends ApplicationAdapter {
    SpriteBatch batch;
    GameLayer mapLayer;

    @Override
    public void create () {
        this.batch = new SpriteBatch();
        this.mapLayer = new GameLayer();

        this.mapLayer.registerController(new TextureRenderer());
        this.mapLayer.addObject(ObjectFactory.createLabel("hello world"));
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        this.mapLayer.update(Gdx.graphics.getDeltaTime());
        this.mapLayer.render(this.batch, Gdx.graphics.getDeltaTime());
        batch.end();
    }

    @Override
    public void resize (int width, int height) {
        this.mapLayer.resize(width, height);
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.mapLayer.dispose();
    }
}