package com.jongber.game.desktop.old.editor.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.desktop.old.editor.EditorView;
import com.jongber.game.desktop.old.editor.sprite.controller.SpriteController;
import com.jongber.game.desktop.old.common.controller.BlockGridRenderer;
import com.jongber.game.desktop.old.common.controller.CameraController;

public class SpriteEditViewer extends EditorView {

    GameLayer layer;

    public SpriteEditViewer() {
        initLayer();

        SpriteEditorCmd.pop(this);
    }

    public void render(float elapsed) {
        //super.update(elapsed);
        Gdx.gl.glClearColor(0.78f, 0.78f, 0.78f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.layer.render(elapsed);
    }

    public void update(float elapsed) {
        super.update(elapsed);
        this.layer.update(elapsed);
    }

    @Override
    public void resize (int width, int height) {
        this.layer.resize(width, height);
    }

    public void dispose() {
        AssetManager.dispose();
        this.layer.dispose();
    }

    public GameLayer getLayer() {
        return this.layer;
    }

    void initLayer() {
        this.layer = new GameLayer();
        this.layer.registerController(new SpriteController());
        this.layer.registerController(new CameraController());
        this.layer.registerController(new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Gdx.input.setInputProcessor(layer.getInput());
    }
}
