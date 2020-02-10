package com.jongber.game.desktop.editor.object;

import com.jongber.game.core.GameLayer;
import com.jongber.game.desktop.editor.common.ViewControlArea;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ObjectEditorCmd extends JFrame {

    public static void pop(ObjectEditViewer view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ObjectEditorCmd cmd = new ObjectEditorCmd(view);
                cmd.init();
                cmd.pack();
                cmd.setVisible(true);
            }
        }).start();
    }

    String basePath;
    ObjectEditViewer view;
    ViewControlArea viewControlArea;

    ObjectEditorCmd(ObjectEditViewer view) {
        this.view = view;
        this.viewControlArea = new ViewControlArea(this, view, null);
        this.basePath = System.getProperty("user.dir") +
                File.separator + "android" + File.separator + "assets";
    }

    void init() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JPanel panel = this.viewControlArea.createPanel();
        add(panel, gbc);
    }
}

class SaveLoadArea {

}