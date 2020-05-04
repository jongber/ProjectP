package com.jongber.game.desktop.editor;

import com.jongber.game.core.GameLayer;

public abstract class EditorView extends GameLayer {

    public abstract void create(EditorCmd cmd);
}
