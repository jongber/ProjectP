package com.jongber.game.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class MapEditor extends ApplicationAdapter {
    OrthographicCamera cam;
    SpriteBatch batch;
    BitmapFont koreanFont;

    @Override
    public void create () {


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Jua-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 28;
        parameter.characters = "한국어/조선�?";

        koreanFont = generator.generateFont(parameter);
        generator.dispose();

        batch = new SpriteBatch();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.update();
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        koreanFont.draw(batch, "한국어/ 3 조선�?" + "한국어", 0, 28);
        batch.end();
    }

    @Override
    public void resize (int width, int height) {
        cam.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {
    }
}