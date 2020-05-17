package com.jongber.game.desktop.editor;

import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jongber.game.core.GameLayer;

public abstract class EditorView extends GameLayer {

    public static int width = 1600;
    public static int height = 900;

    public EditorView() {
        super(new FillViewport(width, height));
    }

    public abstract void create(EditorCmd cmd);
}
