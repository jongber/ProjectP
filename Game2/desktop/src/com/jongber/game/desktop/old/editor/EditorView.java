package com.jongber.game.desktop.old.editor;

import com.jongber.game.core.GameLayer;

public class EditorView extends GameLayer {

    EditorApp editorApp;

    public void setEditorApp(EditorApp mainViewer) {
        this.editorApp = mainViewer;
    }

    public void resetApp() {
        this.editorApp.resetView();
    }
}
