package com.jongber.game.desktop.editor.character;

import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;
import com.jongber.game.desktop.common.panels.SaveLoadArea;

public class CharacterCmd extends EditorCmd {

    CharacterView layer;

    @Override
    public void create(EditorView layer) {
        this.layer = (CharacterView)layer;

        this.initLayout();
    }

    private void initLayout() {
        this.add(new SaveLoadArea(this, null, null).createPanel());
    }
}
