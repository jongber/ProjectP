package com.jongber.game.desktop.editor.battle;

import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;

public class BattleSceneView extends EditorView {

    BattleRule rule;

    @Override
    public void create(EditorCmd cmd) {
        this.rule = new BattleRule(this);
    }
}
