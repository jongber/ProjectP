package com.projecta.game.desktop.editor.pipeline;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.projecta.game.core.base.object.GameObject;
import com.projecta.game.core.base.pipeline.GameObjectPipeline;
import com.projecta.game.core.base.pipeline.GamePipeline;
import com.projecta.game.desktop.common.component.TextureComponent;
import com.projecta.game.desktop.editor.object.TextureObject;

public class TextureRender extends GameObjectPipeline implements GamePipeline.Renderer {

    private SpriteBatch batch;
    private OrthographicCamera camera;

    public TextureRender(OrthographicCamera camera) {
        this.batch = new SpriteBatch();
        this.camera = camera;
    }

    @Override
    public boolean canAddObject(GameObject obj) {
        if (obj instanceof TextureObject) {
            return true;
        }

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
            TextureObject t = (TextureObject)o;
            this.batch.draw(t.TextureComponent.region, t.getPosition().x, t.getPosition().y);
        }

        this.batch.end();
    }

    @Override
    public void dispose() {
    }
}
