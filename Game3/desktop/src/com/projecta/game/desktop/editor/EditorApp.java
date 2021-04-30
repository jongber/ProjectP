package com.projecta.game.desktop.editor;

import com.projecta.game.core.GameLayerAppAdapter;
import com.projecta.game.core.base.layer.GameLayer;

public class EditorApp extends GameLayerAppAdapter {

    @Override
    public void create() {
        super.create();

        GameLayer layer = new GameLayer();
        layer.addPipeline(new BlockGridRender());

        this.addLayer(layer);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
