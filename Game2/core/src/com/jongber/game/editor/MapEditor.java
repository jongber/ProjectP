package com.jongber.game.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.controller.TextureRenderer;
import com.jongber.game.core.graphics.OrthoCameraWrapper;

public class MapEditor extends ApplicationAdapter {
    OrthoCameraWrapper cameraWrapper;
    SpriteBatch batch;
    GameLayer layer;

    @Override
    public void create () {
        this.cameraWrapper = new OrthoCameraWrapper(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.batch = new SpriteBatch();
        this.layer = new GameLayer();

        this.layer.registerController(new TextureRenderer());
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        this.cameraWrapper.update(batch);
        this.layer.update(Gdx.graphics.getDeltaTime());
        this.layer.update(Gdx.graphics.getDeltaTime());

        batch.end();
    }

    @Override
    public void resize (int width, int height) {
        this.cameraWrapper.resize(width, height);
    }

    @Override
    public void dispose() {
        this.batch.dispose();
    }
}