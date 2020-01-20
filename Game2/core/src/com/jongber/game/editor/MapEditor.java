package com.jongber.game.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MapEditor extends ApplicationAdapter {
    Stage stage;
    OrthographicCamera camera;
    SpriteBatch batch;

    @Override
    public void create () {
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
//        koreanFont.draw(batch, "한국어/ 3 조선�?" + "한국어", 0, 28);
        batch.end();
    }

    @Override
    public void resize (int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
    }
}