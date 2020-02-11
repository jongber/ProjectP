package com.jongber.game.desktop.editor.anim;

import com.badlogic.gdx.Gdx;
import com.jongber.game.core.GameLayer;
import com.jongber.game.desktop.editor.EditorView;
import com.jongber.game.desktop.editor.anim.controller.AsepriteController;
import com.jongber.game.desktop.viewer.controller.BlockGridRenderer;
import com.jongber.game.desktop.viewer.controller.CameraController;

public class AnimEditViewer extends EditorView {

    GameLayer layer;

    public AnimEditViewer() {
        initLayer();

        AnimEditorCmd.pop(this);
    }

    public void render(float elapsed) {
        //super.update(elapsed);
        this.layer.render(elapsed);
    }

    public void update(float elapsed) {
        super.update(elapsed);
        this.layer.update(elapsed);
    }

    public GameLayer getLayer() {
        return this.layer;
    }

    void initLayer() {
        this.layer = new GameLayer();
        this.layer.registerController(new AsepriteController());
        this.layer.registerController(new CameraController());
        this.layer.registerController(new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }
}
