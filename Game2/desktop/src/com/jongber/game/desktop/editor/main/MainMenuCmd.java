package com.jongber.game.desktop.editor.main;

import com.jongber.game.core.GameLayer;
import com.jongber.game.desktop.editor.EditorCmd;

public class MainMenuCmd extends EditorCmd {

    MainMenuView layer;

    @Override
    public void create(GameLayer layer) {
        this.layer = (MainMenuView)layer;
    }
}
