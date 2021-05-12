package com.projecta.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.projecta.game.core.GameLayerAppAdapter;
import com.projecta.game.core.base.layer.GameLayer;
import com.projecta.game.core.base.pipeline.EventHandlePipeline;
import com.projecta.game.desktop.editor.pipeline.BlockGridRender;
import com.projecta.game.desktop.editor.pipeline.PerfRender;

public class EditorApp extends GameLayerAppAdapter {

    private GameLayer layer = new GameLayer();

    private EventHandlePipeline eventHandler = new EventHandlePipeline();

    @Override
    public void create() {
        super.create();

        layer.addPipeline(eventHandler);
        layer.addPipeline(new BlockGridRender());
        layer.addPipeline(new PerfRender());

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
