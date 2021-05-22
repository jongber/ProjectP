package com.projecta.game.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.projecta.game.core.base.layer.GameLayer;

import java.util.ArrayList;
import java.util.List;

public abstract class GameLayerAppAdapter extends ApplicationAdapter implements InputProcessor {

    private List<GameLayer> layers = new ArrayList<>();

    public void addLayer(GameLayer layer) {
        this.layers.add(layer);
    }

    @Override
    public void render () {
        ScreenUtils.clear(Color.GRAY);
        this.updateLayers(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        super.dispose();

        for (GameLayer layer : this.layers) {
            layer.dispose();
        }
    }

    private void updateLayers(float elapsed) {
        for (GameLayer layer : this.layers) {
            layer.update(elapsed);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        for (GameLayer layer : this.layers) {
            if (layer.keyDown(keycode)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        for (GameLayer layer : this.layers) {
            if (layer.keyUp(keycode)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        for (GameLayer layer : this.layers) {
            if (layer.keyTyped(character)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (GameLayer layer : this.layers) {
            if (layer.touchDown(screenX, screenY, pointer, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (GameLayer layer : this.layers) {
            if (layer.touchUp(screenX, screenY, pointer, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (GameLayer layer : this.layers) {
            if (layer.touchDragged(screenX, screenY, pointer)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        for (GameLayer layer : this.layers) {
            if (layer.mouseMoved(screenX, screenY)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        for (GameLayer layer : this.layers) {
            if (layer.scrolled(amountX, amountY)) {
                return true;
            }
        }
        return false;
    }
}
