package com.jongber.game;

import com.badlogic.gdx.utils.viewport.FillViewport;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.controller.Controller;
import com.jongber.game.core.controller.PerfRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainLayer extends GameLayer {

    public static final int width = 256;
    public static final int height = 144;

    public enum LayerType {
        Map,
        Actor,
        Fps,
    }

    private HashMap<LayerType, GameLayer> layerMap = new HashMap<>();
    private List<GameLayer> layers = new ArrayList<>();

    public MainLayer() {
        super(new FillViewport(width, height));

        this.addLayer(LayerType.Map, new GameLayer(this.getCameraWrapper(), this.getSequencePlayer()));
        this.addLayer(LayerType.Actor, new GameLayer(this.getCameraWrapper(), this.getSequencePlayer()));
        this.addLayer(LayerType.Fps, new GameLayer());

        this.registerController(LayerType.Fps, new PerfRenderer());
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
}
