package com.projecta.game.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.projecta.game.core.base.GameLayer;

import java.util.ArrayList;
import java.util.List;

public abstract class GameLayerAppAdapter extends ApplicationAdapter {

    private List<GameLayer> layers = new ArrayList<>();

    public void addLayer(GameLayer layer) {
        this.layers.add(layer);
    }

    @Override
    public void render () {
        ScreenUtils.clear(1, 0, 0, 1);
        this.updateLayers(Gdx.graphics.getDeltaTime());
    }

    private void updateLayers(float elapsed) {
        for (GameLayer layer : this.layers) {
            layer.update(elapsed);
        }
    }
}
