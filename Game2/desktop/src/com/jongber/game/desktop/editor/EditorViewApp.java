package com.jongber.game.desktop.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.core.event.GameEventHandler;

public class EditorViewApp extends ApplicationAdapter implements InputProcessor {

    class ChangeViewEvent extends GameEvent {

        Class type;

        public ChangeViewEvent(Class type) {
            this.type = type;
        }

        @Override
        public void handle() {
            if (EditorViewApp.this.layer != null)
                EditorViewApp.this.layer.dispose();
            try {
                EditorViewApp.this.layer = (EditorViewLayer)type.newInstance();
                layer.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                layer.setEditorApp(EditorViewApp.this);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    class ResetViewEvent extends GameEvent {

        @Override
        public void handle() {
            if (layer != null) {
                layer.dispose();
                layer = null;
            }
        }
    }

    EditorViewLayer layer;
    GameEventHandler handler = new GameEventHandler();

    @Override
    public void create () {
        EditorCmd.popUI(this);
    }

    @Override
    public void resize (int width, int height) {
        if (layer != null) {
            layer.resize(width, height);
        }
    }

    @Override
    public void render () {

        Gdx.gl.glClearColor(0.45f, 0.45f, 0.45f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handler.handle();

        float elapsed = Gdx.graphics.getDeltaTime();
        if (layer != null) {
            layer.update(elapsed);
            layer.render(elapsed);
        }
    }

    @Override
    public void dispose () {
        if (layer != null) {
            layer.dispose();
        }
    }

    public void changeView(Class type) {
        this.handler.post(new ChangeViewEvent(type));
    }

    public void resetView() {
        this.handler.post(new ResetViewEvent());
        EditorCmd.popUI(this);
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