package com.jongber.game.desktop.editor.sequence;

import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;

public class SequenceCmd extends EditorCmd {

    SequenceView view;

    @Override
    public void create(EditorView layer) {
        this.view = (SequenceView)layer;
    }
}
