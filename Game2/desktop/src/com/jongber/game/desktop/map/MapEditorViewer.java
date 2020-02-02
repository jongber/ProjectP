package com.jongber.game.desktop.map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.InputProcessor;

public class MapEditorViewer extends ApplicationAdapter implements InputProcessor {

    @Override
    public void create () {
    }

    @Override
    public void resize (int width, int height) {
    }

    @Override
    public void render () {
    }

    @Override
    public void dispose () {
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
        return false;
    }
}
