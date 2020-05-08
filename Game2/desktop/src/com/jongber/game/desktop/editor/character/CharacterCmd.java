package com.jongber.game.desktop.editor.character;

import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;
import com.jongber.game.desktop.common.panels.SaveLoadArea;
import com.jongber.game.desktop.editor.character.panels.CharacterAttributeArea;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class CharacterCmd extends EditorCmd {

    CharacterView layer;

    @Override
    public void create(EditorView layer) {
        this.layer = (CharacterView)layer;

        this.initLayout();
    }

    private void initLayout() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        this.add(new SaveLoadArea(this, null, null).createPanel(), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        this.add(new CharacterAttributeArea().createPanel(), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        this.add(new CharacterAttributeArea().createPanel(), gbc);

        this.pack();
    }
}
