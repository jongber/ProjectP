package com.jongber.game.desktop.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class EditorApp extends ApplicationAdapter {

    public static void returnToMain() {

    }

    public static void changeEditor() {
        
    }

    @Override
    public void create () {
    }

    @Override
    public void resize (int width, int height) {
    }

    @Override
    public void render () {

        Gdx.gl.glClearColor(0.45f, 0.45f, 0.45f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float elapsed = Gdx.graphics.getDeltaTime();
    }

    @Override
    public void dispose () {
    }
}
