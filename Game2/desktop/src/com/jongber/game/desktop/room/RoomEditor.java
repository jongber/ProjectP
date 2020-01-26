package com.jongber.game.desktop.room;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.controller.TextureRenderer;

public class RoomEditor extends ApplicationAdapter implements InputProcessor {

    private SpriteBatch batch;
    private GameLayer layer;

    @Override
    public void create () {
        this.batch = new SpriteBatch();
        this.layer = new GameLayer();
        this.layer.registerController(new TextureRenderer());


        RoomEditorDialog.popInitUI(this.layer);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize (int width, int height) {
        this.layer.resize(width, height);
    }

    @Override
    public void render () {
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {

        this.layer.getInput().scrolled(amount);

        return false;
    }
}
