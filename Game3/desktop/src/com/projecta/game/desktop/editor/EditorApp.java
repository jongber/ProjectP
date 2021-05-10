package com.projecta.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.projecta.game.core.GameLayerAppAdapter;
import com.projecta.game.core.base.layer.GameLayer;

public class EditorApp extends GameLayerAppAdapter {

    @Override
    public void create() {
        super.create();

        GameLayer layer = new GameLayer();
        layer.addPipeline(new BlockGridRender());

        this.addLayer(layer);

        Gdx.input.setInputProcessor(layer);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
