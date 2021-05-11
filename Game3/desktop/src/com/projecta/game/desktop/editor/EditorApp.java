package com.projecta.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.projecta.game.core.GameLayerAppAdapter;
import com.projecta.game.core.base.layer.GameLayer;

public class EditorApp extends GameLayerAppAdapter {

    private GameLayer layer = new GameLayer();

    @Override
    public void create() {
        super.create();

        layer.addPipeline(new BlockGridRender());

        this.addLayer(layer);

        Gdx.input.setInputProcessor(layer);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        this.layer.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
