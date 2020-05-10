package com.jongber.game.desktop.editor.animation;

import com.jongber.game.desktop.common.panels.SaveLoadArea;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AnimationCmd extends EditorCmd {

    AnimationView view;
    AnimationTableArea tableArea;

    GridBagConstraints gbc;

    public AnimationCmd() {
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5,5,5,5);
        this.setLayout(new GridBagLayout());
    }

    @Override
    public void create(EditorView layer) {
        this.view = (AnimationView)layer;

        createLayout();
    }

    private void createLayout() {
        gbc.gridx = 0; gbc.gridy = 0;
        this.add(new SaveLoadArea(this, saveListener, loadListener).createPanel(), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        this.tableArea = new AnimationTableArea(this.view);
        this.add(this.tableArea.createPanel(), gbc);

        this.pack();
    }

    ActionListener saveListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fc = new JFileChooser(EditorCmd.BasePath);
            fc.setFileFilter(new FileNameExtensionFilter("json", "json"));
            if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                AnimationCmd.this.tableArea.onSave(file);
            }
        }
    };

    ActionListener loadListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fc = new JFileChooser(EditorCmd.BasePath);
            fc.setFileFilter(new FileNameExtensionFilter("json", "json"));
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                AnimationCmd.this.tableArea.onLoad(file);
            }
        }
    };
}
