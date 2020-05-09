package com.jongber.game.desktop.editor.aimation;

import com.jongber.game.desktop.common.panels.SaveLoadArea;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimationCmd extends EditorCmd {

    EditorView view;
    AnimationTableArea tablePanel;

    GridBagConstraints gbc;

    public AnimationCmd() {
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5,5,5,5);
        this.setLayout(new GridBagLayout());
    }

    @Override
    public void create(EditorView layer) {
        this.view = layer;

        createLayout();
    }

    private void createLayout() {
        gbc.gridx = 0; gbc.gridy = 0;
        this.add(new SaveLoadArea(this, saveListener, loadListener).createPanel(), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        this.tablePanel = new AnimationTableArea(this.view);
        this.add(this.tablePanel.createPanel(), gbc);

        this.pack();
    }

    ActionListener saveListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    };

    ActionListener loadListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    };
}
