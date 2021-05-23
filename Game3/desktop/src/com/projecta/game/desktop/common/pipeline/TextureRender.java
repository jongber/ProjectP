package com.projecta.game.desktop.common.pipeline;

import com.projecta.game.core.base.object.GameObject;
import com.projecta.game.core.base.pipeline.GameObjectPipeline;
import com.projecta.game.core.base.pipeline.GamePipeline;

public class TextureRender extends GameObjectPipeline implements GamePipeline.Renderer {

    @Override
    public boolean canAddObject(GameObject obj) {
        return false;
    }

    @Override
    public void resize(int w, int h) {
    }

    @Override
    public void render(float elapsed) {
    }

    @Override
    public void dispose() {
    }
}
