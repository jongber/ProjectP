package com.jongber.game.desktop.editor.character.panels;

import com.jongber.game.desktop.editor.EditorCmd;

import javax.swing.JPanel;

public class CharacterAnimArea implements EditorCmd.AreaImpl {

    private JPanel panel;

    private String animName;



    public CharacterAnimArea(String animName) {
        this.animName = animName;
    }


    @Override
    public JPanel createPanel() {
        this.panel = new JPanel();

        return this.panel;
    }
}
