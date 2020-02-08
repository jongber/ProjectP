package com.jongber.game.desktop.editor;

import com.jongber.game.core.GameLayer;

public class EditorViewLayer extends GameLayer {

    EditorViewApp editorApp;

    public void setEditorApp(EditorViewApp mainViewer) {
        this.editorApp = mainViewer;
    }

    public void resetApp() {
        this.editorApp.resetView();
    }
}
