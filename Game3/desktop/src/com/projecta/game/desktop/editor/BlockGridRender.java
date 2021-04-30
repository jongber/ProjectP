package com.projecta.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.projecta.game.core.base.layer.GameLayerController;
import com.projecta.game.core.base.pipeline.GamePipeline;

public class BlockGridRender extends GamePipeline {

    private OrthographicCamera camera;

    public BlockGridRender(GameLayerController c) {
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void update(float elapsed) {
    }

    @Override
    public void dispose() {
    }
}
