package com.jongber.game.desktop.editor.object;

import com.jongber.game.core.GameLayer;
import com.jongber.game.desktop.editor.EditorView;

public class ObjectEditViewer extends EditorView {

    private GameLayer layer;

    public ObjectEditViewer() {
        ObjectEditorCmd.pop(this);
    }
}
