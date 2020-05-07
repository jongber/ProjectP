package com.jongber.game.desktop.editor.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.desktop.old.common.controller.SpriteController;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;

public class CharacterView extends EditorView {

    private GameLayer fpsLayer = new GameLayer();
    private GameLayer layer = new GameLayer();

    @Override
    public void create(EditorCmd cmd) {
        this.layer.registerController(new SpriteController());
    }

    @Override
    public void resize (int width, int height) {
        this.fpsLayer.resize(width, height);
        this.layer.resize(width, height);
    }

    @Override
    public void update(float elapsed) {
        super.update(elapsed);
        this.fpsLayer.update(elapsed);
        this.layer.update(elapsed);
    }

    @Override
    public void render (float elapsed) {
        Gdx.gl.glClearColor(0.45f, 0.45f, 0.45f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.fpsLayer.render(elapsed);
        this.layer.render(elapsed);
    }

    @Override
    public void dispose () {
        AssetManager.dispose();
    }
}
