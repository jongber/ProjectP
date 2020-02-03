package com.jongber.game.desktop.map;

import com.badlogic.gdx.Gdx;
import com.jongber.game.core.GameLayer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

class MapEditorCmd extends JFrame {

    private GameLayer layer;

    public MapEditorCmd(GameLayer layer) {
        super();
        this.layer = layer;
    }

    static void popMapEditorCmd(GameLayer layer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MapEditorCmd cmd = new MapEditorCmd(layer);
                cmd.setSize(400, 400);
                cmd.setVisible(true);
                cmd.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent windowEvent) {
                        super.windowClosing(windowEvent);
                        Gdx.app.exit();
                    }
                });
            }
        }).start();
    }
}


