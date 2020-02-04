package com.jongber.game.desktop.map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.controller.PerfRenderer;
import com.jongber.game.desktop.map.controller.RoomController;
import com.jongber.game.desktop.viewer.controller.BlockGridRenderer;
import com.jongber.game.desktop.viewer.controller.CameraController;
import com.jongber.game.desktop.viewer.controller.RoomPropertyRenderer;
import com.jongber.game.desktop.viewer.controller.RoomPropsRenderer;


public class MapEditorViewer extends ApplicationAdapter implements InputProcessor {

    private SpriteBatch batch;
    private GameLayer roomLayer;
    private GameLayer fpsLayer;

    @Override
    public void create () {
        batch = new SpriteBatch();

        this.initRoomLayer();

        fpsLayer = new GameLayer();
        fpsLayer.registerController(new PerfRenderer());

        Gdx.input.setInputProcessor(this);
        MapEditorCmd.popMapEditorCmd(roomLayer);
    }

    @Override
    public void resize (int width, int height) {
        roomLayer.resize(width, height);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0.45f, 0.45f, 0.45f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float elapsed = Gdx.graphics.getDeltaTime();

        batch.begin();
        this.roomLayer.update(elapsed);
        this.fpsLayer.update(elapsed);

        this.roomLayer.render(batch, elapsed);
        this.fpsLayer.render(batch, elapsed);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        roomLayer.dispose();
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

        this.roomLayer.getInput().touchDown(screenX, screenY, pointer, button);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        this.roomLayer.getInput().touchUp(screenX, screenY, pointer, button);

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        this.roomLayer.getInput().touchDragged(screenX, screenY, pointer);

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {

        this.roomLayer.getInput().scrolled(amount);

        return false;
    }

    private void initRoomLayer() {
        roomLayer = new GameLayer();
        roomLayer.registerController(new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        roomLayer.registerController(new CameraController());
        roomLayer.registerController(new RoomPropertyRenderer());
        roomLayer.registerController(new RoomPropsRenderer());
        roomLayer.registerController(new RoomController());

        roomLayer.getCameraWrapper().setZoom(0.4f);
    }
}
