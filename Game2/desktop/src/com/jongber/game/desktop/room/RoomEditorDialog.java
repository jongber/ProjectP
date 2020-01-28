package com.jongber.game.desktop.room;

import com.jongber.game.core.GameLayer;
import com.jongber.game.desktop.room.event.CreateRoomEvent;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RoomEditorDialog {

    public static void popInitUI(GameLayer layer) {
        _popInitUI(layer);
    }

    /*
     *
     * room name : []
     * sanity : []
     * noise : []
     * height : 48(current)
     * width : 16px block
     * wallpaper (texture)
     * display grid[]
     * [apply] [clear]
     * [save] [load]
     * */

    public static void popRoomUI(GameLayer layer) {
        JDialog dialog = new JDialog();
        dialog.setSize(200, 400);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        // 1. room name
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new Label("Room name "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        TextField nameField = new TextField(10);
        dialog.add(nameField, gbc);

        // 2. sanity
        gbc.gridx = 0;
        gbc.gridy = 1;
        Label sanityLabel = new Label("Sanity 50");
        dialog.add(sanityLabel, gbc);

        JSlider sanitySlider = new JSlider();
        sanitySlider.setMinorTickSpacing(5);
        sanitySlider.setMajorTickSpacing(25);
        sanitySlider.setPaintTicks(true);
        sanitySlider.setPaintLabels(true);
        sanitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        dialog.add(sanitySlider, gbc);

        dialog.setVisible(true);
    }

    private static void _popInitUI(GameLayer layer) {
        JDialog dialog = new JDialog();
        dialog.setLayout(new FlowLayout());
        dialog.setSize(100, 100);

        JButton newButton = new JButton();
        JButton loadButton = new JButton();

        newButton.setText("create room");
        loadButton.setText("load room");

        dialog.add(newButton);
        dialog.add(loadButton);

        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                layer.post(new CreateRoomEvent(layer));
                dialog.dispose();
            }
        });

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent windowEvent) {
                super.windowClosed(windowEvent);
            }
        });

        dialog.setVisible(true);
    }
}
