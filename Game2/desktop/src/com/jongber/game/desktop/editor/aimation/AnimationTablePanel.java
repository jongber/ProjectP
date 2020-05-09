package com.jongber.game.desktop.editor.aimation;

import com.jongber.game.core.GameObject;
import com.jongber.game.desktop.editor.EditorCmd;

import javax.swing.JPanel;

public class AnimationTablePanel implements EditorCmd.AreaImpl {

    JPanel panel;
    GameObject animTarget;

    @Override
    public JPanel createPanel() {

        panel = new JPanel();

        return panel;
    }
}
