package com.jongber.game.desktop.map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.controller.PerfRenderer;
import com.jongber.game.desktop.common.BlockGridRenderer;
import com.jongber.game.desktop.common.CameraController;


public class MapEditorViewer extends ApplicationAdapter implements InputProcessor {

    private SpriteBatch batch;
    private GameLayer mapLayer;
    private GameLayer fpsLayer;

    @Override
    public void create () {
        batch = new SpriteBatch();

        mapLayer = new GameLayer();
        mapLayer.registerController(new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        mapLayer.registerController(new CameraController());

        fpsLayer = new GameLayer();
        fpsLayer.registerController(new PerfRenderer());

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize (int width, int height) {
        mapLayer.resize(width, height);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0.45f, 0.45f, 0.45f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float elapsed = Gdx.graphics.getDeltaTime();

        batch.begin();
        this.mapLayer.update(elapsed);
        this.fpsLayer.update(elapsed);

        this.mapLayer.render(batch, elapsed);
        this.fpsLayer.render(batch, elapsed);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        mapLayer.dispose();
        fpsLayer.dispose();
        AssetManager.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        this.mapLayer.getInput().touchDown(screenX, screenY, pointer, button);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        this.mapLayer.getInput().touchUp(screenX, screenY, pointer, button);

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        this.mapLayer.getInput().touchDragged(screenX, screenY, pointer);

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {

        this.mapLayer.getInput().scrolled(amount);

        return false;
    }
}
