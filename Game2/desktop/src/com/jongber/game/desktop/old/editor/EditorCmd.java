package com.jongber.game.desktop.old.editor;

import com.badlogic.gdx.Gdx;
import com.jongber.game.desktop.old.editor.map.MapEditorView;
import com.jongber.game.desktop.old.editor.sprite.SpriteEditViewer;
import com.jongber.game.desktop.old.editor.room.RoomEditView;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

class EditorCmd extends JFrame {

    private EditorApp viewApp;

    static void popUI(EditorApp viewer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EditorCmd window = new EditorCmd(viewer);
                window.setSize(200, 200);
                window.setTitle("Editor Commander");
                window.init();
                window.setVisible(true);
            }
        }).start();
    }

    private EditorCmd(EditorApp viewApp) {
        this.viewApp = viewApp;
    }

    private void init() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                Gdx.app.exit();
            }
        });

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton button = new JButton("Map Editor");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewApp.changeView(MapEditorView.class);
                EditorCmd.this.setVisible(false);
                EditorCmd.this.dispose();
            }
        });

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1;
        this.add(button, gbc);

        button = new JButton("Room Editor");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewApp.changeView(RoomEditView.class);
                EditorCmd.this.setVisible(false);
                EditorCmd.this.dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        this.add(button, gbc);

        button = new JButton("Sprite Editor");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewApp.changeView(SpriteEditViewer.class);
                EditorCmd.this.dispose();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        this.add(button, gbc);
    }
}