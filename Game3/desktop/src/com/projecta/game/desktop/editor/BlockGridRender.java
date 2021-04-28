package com.projecta.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.projecta.game.core.base.GameLayerController;
import com.projecta.game.core.base.GameObject;
import com.projecta.game.core.base.GameObjectController;

public class BlockGridRender extends GameObjectController {

    private OrthographicCamera camera;

    public BlockGridRender(GameLayerController c) {
        super(c);
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public boolean canAdd(GameObject obj) {
        return false;
    }

    @Override
    public void updateInternal(GameObject obj, float elapsed) {
    }

    @Override
    public void onAdd(GameObject obj) {
    }

    @Override
    public void onRemove(GameObject obj) {
    }

    @Override
    public void dispose() {
    }
}
