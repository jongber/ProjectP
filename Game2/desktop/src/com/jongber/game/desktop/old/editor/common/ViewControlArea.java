package com.jongber.game.desktop.old.editor.common;

import com.jongber.game.core.GameLayer;
import com.jongber.game.desktop.old.editor.EditorView;
import com.jongber.game.desktop.old.common.event.ShowGridEvent;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewControlArea {

    private EditorView view;
    private GameLayer layer;
    private JFrame frame;

    private JCheckBox gridBox;
    private JButton btReturn;

    public ViewControlArea(JFrame frame, EditorView view, GameLayer layer) {
        this.frame = frame;
        this.layer = layer;
        this.view = view;

        this.initGridBox();
        this.initReturnButton();
    }

    public JPanel createPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Viewer Cmd"));
        GridBagConstraints gbc = new GridBagConstraints();

        // 1. show grid
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Show grid "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(gridBox, gbc);

        // 2. return to main
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(btReturn, gbc);

        return panel;
    }

    private void initGridBox() {
        this.gridBox = new JCheckBox();
        this.gridBox.setSelected(true);
        this.gridBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean checked = e.getStateChange() == ItemEvent.SELECTED;
                if (layer == null)
                    return;
                layer.post(new ShowGridEvent(layer, checked));
            }
        });
    }

    private void initReturnButton() {
        btReturn = new JButton("Return Main");
        btReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                view.resetApp();
                frame.dispose();
            }
        });
    }

}