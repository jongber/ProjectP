package com.jongber.game.desktop.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.core.event.GameEventHandler;

public class EditorViewer extends ApplicationAdapter implements InputProcessor {

    class ChangeViewEvent extends GameEvent {

        Class type;

        public ChangeViewEvent(Class type) {
            this.type = type;
        }

        @Override
        public void handle() {
            if (EditorViewer.this.viewer != null)
                EditorViewer.this.viewer.dispose();
            try {
                EditorViewer.this.viewer = (GameLayer)type.newInstance();
                viewer.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    GameLayer viewer;
    GameEventHandler handler = new GameEventHandler();

    @Override
    public void create () {
        EditorCmd.popUI(this);
    }

    @Override
    public void resize (int width, int height) {
        if (viewer != null) {
            viewer.resize(width, height);
        }
    }

    @Override
    public void render () {
        handler.handle();

        float elapsed = Gdx.graphics.getDeltaTime();
        if (viewer != null) {
            viewer.update(elapsed);
            viewer.render(elapsed);
        }
    }

    @Override
    public void dispose () {
        if (viewer != null) {
            viewer.dispose();
        }
    }

    public void changeView(Class type) {
        this.handler.post(new ChangeViewEvent(type));
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