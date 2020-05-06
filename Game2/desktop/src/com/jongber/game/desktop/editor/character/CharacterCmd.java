package com.jongber.game.desktop.editor.character;

import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;

public class CharacterCmd extends EditorCmd {

    CharacterView layer;

    @Override
    public void create(EditorView layer) {
        this.layer = (CharacterView)layer;
    }


}
