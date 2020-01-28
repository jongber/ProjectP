package com.jongber.game.desktop.room;

import com.jongber.game.core.GameLayer;
import com.jongber.game.desktop.room.event.CreateRoomEvent;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JSlider;
import javax.swing.JSpinner;
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
        dialog.setSize(400, 400);
        //dialog.setResizable(false);

        //JPanel dialog = new JPanel();
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        //gbc.insets = new Insets(5,5,0,0);

        // 1. room name
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new Label("Room name "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        TextField nameField = new TextField();
        dialog.add(nameField, gbc);

        // 2. sanity
        gbc.gridx = 0;
        gbc.gridy = 1;
        Label sanityLabel = new Label("Sanity: 50");
        dialog.add(sanityLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JSlider sanitySlider = new JSlider();
        sanitySlider.setMinimum(0);
        sanitySlider.setMaximum(100);
        sanitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                sanityLabel.setText("Sanity: " + sanitySlider.getValue());
            }
        });
        dialog.add(sanitySlider, gbc);

        // 3. noise
        gbc.gridx = 0;
        gbc.gridy = 2;
        Label noiseLabel = new Label("Noise: 50");
        dialog.add(noiseLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JSlider noiseSlider = new JSlider();
        noiseSlider.setMinimum(0);
        noiseSlider.setMaximum(100);
        noiseSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                noiseLabel.setText("Noise: " + noiseSlider.getValue());
            }
        });
        dialog.add(noiseSlider, gbc);

        // 4. height
        gbc.gridx = 0;
        gbc.gridy = 3;
        Label heightLabel = new Label("Height: ");
        dialog.add(heightLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        dialog.add(new Label("3 block(48px) fixed"), gbc);

        // 5. width
        gbc.gridx = 0;
        gbc.gridy = 4;
        Label widthLabel = new Label("Width: ");
        dialog.add(widthLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        JSpinner widthSpinner = new JSpinner();
        dialog.add(widthSpinner, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        dialog.add(new Label("block"), gbc);

        // 6. wallpaper
        gbc.gridx = 0;
        gbc.gridy = 5;
        JButton pathButton = new JButton("wallpaper");
        dialog.add(pathButton, gbc);

        //dialog.add(dialog);
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
