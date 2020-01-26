package com.jongber.game.desktop.editor.event;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.event.GameEvent;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class NewMapEvent extends GameEvent {

    GameLayer uiLayer;
    GameLayer mapLayer;

    public NewMapEvent(GameLayer ui, GameLayer map) {
        this.uiLayer = ui;
        this.mapLayer = map;
    }

    @Override
    public void handle() {
        this.showDialog();
    }

    private void showDialog() {
        JDialog dialog = new JDialog();
        dialog.setSize(300, 200);
        dialog.setTitle("Enter map information");
        dialog.setDefaultCloseOperation(WindowConstants. DO_NOTHING_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JTextField mapName = new JTextField(8);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 0, 0);

        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("map name:"), constraints);

        constraints.gridx = 1;
        panel.add(mapName, constraints);

//        constraints.gridy = 1;
//        panel.add(new JButton(), constraints);

        dialog.add(panel);
        dialog.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });
        dialog.setVisible(true);
    }
}
