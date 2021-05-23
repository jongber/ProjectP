package com.projecta.game.desktop.common.pipeline;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.projecta.game.core.base.object.GameObject;
import com.projecta.game.core.base.pipeline.GameObjectPipeline;
import com.projecta.game.core.base.pipeline.GamePipeline;

public class TextureRender extends GameObjectPipeline implements GamePipeline.Renderer {

    private SpriteBatch batch;
    private OrthographicCamera camera;

    public TextureRender(OrthographicCamera camera) {
        this.batch = new SpriteBatch();
        this.camera = camera;
    }

    @Override
    public boolean canAddObject(GameObject obj) {
        return false;
    }

    @Override
    public void resize(int w, int h) {
    }

    @Override
    public void render(float elapsed) {
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();

        for (GameObject o : this.objects) {
        }

        this.batch.end();
    }

    @Override
    public void dispose() {
    }
}
