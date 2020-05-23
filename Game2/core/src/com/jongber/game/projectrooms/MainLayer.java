package com.jongber.game.projectrooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.controller.PerfRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainLayer extends GameLayer implements InputProcessor {

    public static final int defaltScale = 5;
    public static final int width = 256 * defaltScale;
    public static final int height = 144 * defaltScale;

    public enum LayerType {
        Map,
        Actor,
        Dialog,
        UI,
        Fps,
    }

    private HashMap<LayerType, GameLayer> layerMap = new HashMap<>();
    private List<GameLayer> layers = new ArrayList<>();

    public MainLayer() {
        super(new FillViewport(width, height));

        this.addLayer(LayerType.Map, new GameLayer(this.getCameraWrapper(), this.getSequencePlayer()));
        this.addLayer(LayerType.Actor, new GameLayer(this.getCameraWrapper(), this.getSequencePlayer()));
        this.addLayer(LayerType.Dialog, new GameLayer(this.getCameraWrapper(), this.getSequencePlayer()));
        this.addLayer(LayerType.UI, new GameLayer(this.getCameraWrapper(), this.getSequencePlayer()));
        this.addLayer(LayerType.Fps, new GameLayer());

        this.registerController(LayerType.Fps, new PerfRenderer());

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float elapsed) {
        super.render(elapsed);

        for (GameLayer layer : layers) {
            layer.render(elapsed);
        }
    }

    public void update(float elapsed) {
        super.update(elapsed);

        for (GameLayer layer : layers) {
            layer.update(elapsed);
        }
    }

    @Override
    public void resize (int width, int height) {
        super.resize(width, height);

        for (GameLayer layer : layers) {
            layer.resize(width, height);
        }
    }

    @Override
    public void dispose() {
        super.dispose();

        for (GameLayer layer : layers) {
            layer.dispose();
        }
    }

    public GameLayer getLayer(LayerType type) {
        return this.layerMap.get(type);
    }

    public void registerController(LayerType type, Controller c) {
        this.getLayer(type).registerController(c);
    }

    private void addLayer(LayerType type, GameLayer layer) {
        this.layers.add(layer);
        this.layerMap.put(type, layer);
    }

    @Override
    public boolean keyDown(int keycode) {

        this.getInput().keyDown(keycode);

        for (int i = this.layers.size() - 1; i >= 0; --i) {
            GameLayer layer = this.layers.get(i);
            if (layer.getInput().keyDown(keycode))
                return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        this.getInput().keyUp(keycode);

        for (int i = this.layers.size() - 1; i >= 0; --i) {
            GameLayer layer = this.layers.get(i);
            if (layer.getInput().keyUp(keycode))
                return true;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        this.getInput().keyTyped(character);

        for (int i = this.layers.size() - 1; i >= 0; --i) {
            GameLayer layer = this.layers.get(i);
            if (layer.getInput().keyTyped(character))
                return true;
        }

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        this.getInput().touchDown(screenX, screenY, pointer, button);

        for (int i = this.layers.size() - 1; i >= 0; --i) {
            GameLayer layer = this.layers.get(i);
            if (layer.getInput().touchDown(screenX, screenY, pointer, button))
                return true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        this.getInput().touchUp(screenX, screenY, pointer, button);

        for (int i = this.layers.size() - 1; i >= 0; --i) {
            GameLayer layer = this.layers.get(i);
            if (layer.getInput().touchUp(screenX, screenY, pointer, button))
                return true;
        }

        return false;

    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        this.getInput().touchDragged(screenX, screenY, pointer);

        for (int i = this.layers.size() - 1; i >= 0; --i) {
            GameLayer layer = this.layers.get(i);
            if (layer.getInput().touchDragged(screenX, screenY, pointer))
                return true;
        }

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        this.getInput().mouseMoved(screenX, screenY);

        for (int i = this.layers.size() - 1; i >= 0; --i) {
            GameLayer layer = this.layers.get(i);
            if (layer.getInput().mouseMoved(screenX, screenY))
                return true;
        }

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        this.getInput().scrolled(amount);

        for (int i = this.layers.size() - 1; i >= 0; --i) {
            GameLayer layer = this.layers.get(i);
            if (layer.getInput().scrolled(amount))
                return true;
        }

        return false;
    }
}
