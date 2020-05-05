package com.jongber.game.desktop.old.editor.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.controller.PerfRenderer;
import com.jongber.game.core.controller.TextureRenderer;
import com.jongber.game.desktop.old.editor.EditorView;
import com.jongber.game.desktop.old.common.controller.BlockGridRenderer;
import com.jongber.game.desktop.common.controller.CameraController;
import com.jongber.game.desktop.old.editor.room.controller.RoomPropsController;
import com.jongber.game.desktop.old.common.controller.RoomPropertyRenderer;
import com.jongber.game.desktop.old.common.controller.RoomPropsRenderer;

public class RoomEditView extends EditorView implements InputProcessor {

    private GameLayer roomViewLayer;
    private GameLayer fpsViewLayer;

    public RoomEditView() {
        this.createRoomView();

        this.fpsViewLayer = new GameLayer();
        this.fpsViewLayer.registerController(new PerfRenderer());

        RoomEditorCommander.popRoomUI(this);

        Gdx.input.setInputProcessor(this);

        this.loadAssets();
    }

    @Override
    public void resize (int width, int height) {
        this.roomViewLayer.resize(width, height);
    }

    @Override
    public void update(float elapsed) {
        this.roomViewLayer.update(elapsed);
        this.fpsViewLayer.update(elapsed);
    }

    @Override
    public void render (float elapsed) {
        Gdx.gl.glClearColor(0.45f, 0.45f, 0.45f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.roomViewLayer.render(elapsed);
        this.fpsViewLayer.render(elapsed);
    }

    @Override
    public void dispose () {
        this.roomViewLayer.dispose();
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
        this.roomViewLayer.getInput().touchDown(screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.roomViewLayer.getInput().touchUp(screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        this.roomViewLayer.getInput().touchDragged(screenX, screenY, pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        this.roomViewLayer.getInput().mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {

        this.roomViewLayer.getInput().scrolled(amount);

        return false;
    }

    public GameLayer getRoomLayer() {
        return this.roomViewLayer;
    }

    private void loadAssets() {
        AssetManager.getTexture("projectz/house/wall/ceil_wall.png");
        AssetManager.getTexture("projectz/house/wall/left_wall.png");
        AssetManager.getTexture("projectz/house/wall/right_wall.png");
    }

    private void createRoomView() {
        this.roomViewLayer = new GameLayer();
        this.roomViewLayer.registerController(new TextureRenderer());
        this.roomViewLayer.registerController(new CameraController());
        this.roomViewLayer.registerController(new RoomPropertyRenderer());
        this.roomViewLayer.registerController(new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.roomViewLayer.registerController(new RoomPropsController());
        this.roomViewLayer.registerController(new RoomPropsRenderer());
        this.roomViewLayer.getCameraWrapper().getCamera().zoom = 0.4f;
    }
}