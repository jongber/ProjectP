package com.projecta.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.projecta.game.core.GameLayerAppAdapter;
import com.projecta.game.core.util.Tuple2;
import com.projecta.game.desktop.common.panel.GamePanel;
import com.projecta.game.desktop.common.pipeline.BlockGridRender;
import com.projecta.game.desktop.common.pipeline.PerfRender;

public class EditorApp extends GameLayerAppAdapter {

    private GamePanel panel;

    @Override
    public void create() {
        super.create();

        this.panel = new GamePanel(new Tuple2<>(0.50f, 0.5f), new Tuple2<>(0.3f, 0.3f));
        this.panel.addPipeline(new BlockGridRender(this.panel.getCamera()));
        this.panel.addPipeline(new PerfRender());

        this.addLayer(this.panel);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        this.panel.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
