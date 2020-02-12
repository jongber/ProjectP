package com.jongber.game.desktop.editor.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.desktop.editor.EditorView;
import com.jongber.game.desktop.editor.sprite.controller.AsepriteController;
import com.jongber.game.desktop.viewer.controller.BlockGridRenderer;
import com.jongber.game.desktop.viewer.controller.CameraController;

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
        this.layer.registerController(new AsepriteController());
        this.layer.registerController(new CameraController());
        this.layer.registerController(new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Gdx.input.setInputProcessor(layer.getInput());
    }
}
