package com.jongber.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.jongber.game.desktop.editor.map.MapEditorViewer;
import com.jongber.game.desktop.editor.room.RoomEditViewer;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class EditorCmd extends JFrame {

    EditorViewer viewer;

    static void popUI(EditorViewer viewer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EditorCmd window = new EditorCmd(viewer);
                window.setSize(200, 100);
                window.init();
                window.setVisible(true);
            }
        }).start();
    }

    public EditorCmd(EditorViewer viewer) {
        this.viewer = viewer;
    }

    void init() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                Gdx.app.exit();
            }
        });

        setLayout(new FlowLayout());

        JButton button = new JButton("Map");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewer.changeView(MapEditorViewer.class);
                EditorCmd.this.setVisible(false);
                EditorCmd.this.dispose();
            }
        });
        this.add(button);

        button = new JButton("Room");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewer.changeView(RoomEditViewer.class);
                EditorCmd.this.setVisible(false);
                EditorCmd.this.dispose();
            }
        });
        this.add(button);
    }
}
