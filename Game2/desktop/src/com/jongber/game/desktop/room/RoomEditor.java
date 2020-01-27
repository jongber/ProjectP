package com.jongber.game.desktop.room;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.controller.TextureRenderer;
import com.jongber.game.desktop.room.controller.BlockGridRenderer;
import com.jongber.game.desktop.room.controller.CameraController;

public class RoomEditor extends ApplicationAdapter implements InputProcessor {

    private SpriteBatch batch;
    private GameLayer layer;

    @Override
    public void create () {
        this.batch = new SpriteBatch();
        this.layer = new GameLayer();
        this.layer.registerController(new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.layer.registerController(new TextureRenderer());
        this.layer.registerController(new CameraController());

        RoomEditorDialog.popInitUI(this.layer);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize (int width, int height) {
        this.layer.resize(width, height);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float elapsed = Gdx.graphics.getDeltaTime();

        batch.begin();
        this.layer.update(elapsed);
        this.layer.render(batch, elapsed);
        batch.end();
    }

    @Override
    public void dispose () {
        this.layer.dispose();
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
        this.layer.getInput().touchDown(screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.layer.getInput().touchUp(screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        this.layer.getInput().touchDragged(screenX, screenY, pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        this.layer.getInput().mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {

        this.layer.getInput().scrolled(amount);

        return false;
    }
}
