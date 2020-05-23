package com.jongber.game.desktop.editor;

import com.jongber.game.projectrooms.MainLayer;

public abstract class EditorView extends MainLayer {
    public EditorView() {
    }

    public abstract void create(EditorCmd cmd);
}
