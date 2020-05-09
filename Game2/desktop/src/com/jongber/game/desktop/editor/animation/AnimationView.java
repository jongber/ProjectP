package com.jongber.game.desktop.editor.animation;

import com.jongber.game.core.GameObject;
import com.jongber.game.desktop.common.component.SpriteComponent;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;

public class AnimationView extends EditorView {
    GameObject object;

    @Override
    public void create(EditorCmd cmd) {
        this.object = new GameObject();
        this.object.addComponent(new SpriteComponent());

        this.addObject(this.object);
    }

    public GameObject getAnimationTarget() {
        return this.object;
    }
}
