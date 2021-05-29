package com.projecta.game.desktop.editor.pipeline;

import com.projecta.game.core.base.object.GameObject;
import com.projecta.game.core.base.pipeline.GameObjectPipeline;
import com.projecta.game.core.base.pipeline.GamePipeline;
import com.projecta.game.desktop.editor.object.SpriteObject;

public class SpritePlayer extends GameObjectPipeline implements GamePipeline.Renderer {

    @Override
    public boolean canAddObject(GameObject obj) {
        return obj instanceof SpriteObject;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int w, int h) {

    }

    @Override
    public void render(float elapsed) {

    }
}
