package com.jongber.game.desktop.editor;

import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jongber.game.MainLayer;
import com.jongber.game.core.GameLayer;

public abstract class EditorView extends MainLayer {
    public EditorView() {
        //super(new FillViewport(width, height));
    }

    public abstract void create(EditorCmd cmd);
}
