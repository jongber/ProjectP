package com.jongber.game.desktop.editor.mainmenu;

import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;

public class MainMenuCmd extends EditorCmd {

    MainMenuView layer;

    @Override
    public void create(EditorView layer) {
        this.layer = (MainMenuView)layer;
    }
}
