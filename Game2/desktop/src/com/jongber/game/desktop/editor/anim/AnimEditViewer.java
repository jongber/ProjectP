package com.jongber.game.desktop.editor.anim;

import com.jongber.game.core.GameLayer;
import com.jongber.game.desktop.editor.EditorView;

public class AnimEditViewer extends EditorView {

    GameLayer layer;

    public AnimEditViewer() {
        this.layer = new GameLayer();

        AnimEditorCmd.pop(this);
    }

    public GameLayer getLayer() {
        return this.layer;
    }
}
