package com.jongber.game.desktop.editor.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.controller.PerfRenderer;

public class MainMenuView extends GameLayer {

    private GameLayer fpsLayer;

    public MainMenuView() {
        this.fpsLayer = new GameLayer();
        this.fpsLayer.registerController(new PerfRenderer());
    }

    @Override
    public void resize (int width, int height) {
        this.fpsLayer.resize(width, height);
    }

    @Override
    public void update(float elapsed) {
        this.fpsLayer.update(elapsed);
    }

    @Override
    public void render (float elapsed) {
        Gdx.gl.glClearColor(0.45f, 0.45f, 0.45f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.fpsLayer.render(elapsed);
    }

    @Override
    public void dispose () {
        AssetManager.dispose();
    }
}
