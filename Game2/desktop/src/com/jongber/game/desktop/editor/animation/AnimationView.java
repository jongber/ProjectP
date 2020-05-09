package com.jongber.game.desktop.editor.animation;

import com.badlogic.gdx.Gdx;
import com.jongber.game.core.GameObject;
import com.jongber.game.desktop.common.component.SpriteComponent;
import com.jongber.game.desktop.common.controller.CameraController;
import com.jongber.game.desktop.common.controller.SpriteRenderer;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;
import com.jongber.game.desktop.common.controller.BlockGridRenderer;

public class AnimationView extends EditorView {
    GameObject object;

    @Override
    public void create(EditorCmd cmd) {
        this.object = new GameObject();
        this.object.addComponent(new SpriteComponent());

        this.addObject(this.object);

        this.registerController(new SpriteRenderer());
        this.registerController(new CameraController());
        this.registerController(new BlockGridRenderer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Gdx.input.setInputProcessor(this.getInput());

        getCameraWrapper().setZoom(0.5f);
    }

    public GameObject getAnimationTarget() {
        return this.object;
    }
}
