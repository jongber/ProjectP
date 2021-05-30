package com.projecta.game.desktop.editor.pipeline;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.projecta.game.core.base.object.GameObject;
import com.projecta.game.core.base.pipeline.GameObjectPipeline;
import com.projecta.game.core.base.pipeline.GamePipeline;
import com.projecta.game.desktop.common.component.SpriteComponent;
import com.projecta.game.desktop.editor.object.SpriteObject;

public class SpritePlayer extends GameObjectPipeline implements GamePipeline.Renderer {

    private OrthographicCamera camera;
    private SpriteBatch batch;

    public SpritePlayer(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();
    }

    @Override
    public boolean canAddObject(GameObject obj) {
        if (obj instanceof SpriteObject) {
            SpriteObject so = (SpriteObject)obj;
            if (so.SpriteComponent != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void dispose() {
        this.batch.dispose();
    }

    @Override
    public void resize(int w, int h) {
    }

    @Override
    public void render(float elapsed) {
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();

        for (GameObject o : this.objects) {
            SpriteObject so = (SpriteObject)o;
            SpriteComponent c = so.SpriteComponent;
            TextureRegion tr = so.SpriteComponent.getNext(elapsed, true);
            if (tr != null) {
                this.batch.draw(tr, so.getPosition().x - c.pivot.x, so.getPosition().y - c.pivot.y);
            }
        }

        this.batch.end();
    }
}
