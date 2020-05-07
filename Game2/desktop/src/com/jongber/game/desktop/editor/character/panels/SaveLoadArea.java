package com.jongber.game.desktop.editor.character.panels;

import com.jongber.game.desktop.editor.EditorCmd;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SaveLoadArea implements EditorCmd.AreaImpl {

    private EditorCmd cmd;

    JButton saveButton;
    JButton loadButton;

    public SaveLoadArea(EditorCmd cmd, ActionListener save, ActionListener load) {
        this.cmd = cmd;
        this.initSave(save);
        this.initLoad(load);
    }

    @Override
    public JPanel createPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Save Load Area"));

        panel.add(this.saveButton);
        panel.add(this.loadButton);

        return panel;
    }

    private void initSave(ActionListener save) {
        this.saveButton = new JButton("Save");
        if (save != null)
            this.saveButton.addActionListener(save);
    }

    private void initLoad(ActionListener load) {
        this.loadButton = new JButton("Load");
        if (load != null)
            this.loadButton.addActionListener(load);
    }
}
