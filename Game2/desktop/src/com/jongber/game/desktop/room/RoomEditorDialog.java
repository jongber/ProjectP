package com.jongber.game.desktop.room;

import com.jongber.game.core.GameLayer;
import com.jongber.game.desktop.room.event.CreateRoomEvent;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RoomEditorDialog {

    public static void popRoomUI(GameLayer layer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                _popRoomUI(layer);
            }
        }).start();
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

    public static void _popRoomUI(GameLayer layer) {
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
        widthSpinner.setValue(3);
        dialog.add(widthSpinner, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        dialog.add(new Label("block"), gbc);

        // 6. wallpaper
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel wallpaperLabel = new JLabel("Wallpaper: ");
        dialog.add(wallpaperLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        JButton wallpaperButton = new JButton("load");
        wallpaperButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String basePath = System.getProperty("user.dir") +
                        File.separator + "android" + File.separator + "assets";
                File baseFile = new File(basePath);

                JFileChooser fc = new JFileChooser(baseFile);
                int i = fc.showOpenDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    //String relative = baseFile.toURI().relativize(f.toURI()).getPath();
                    ImageIcon icon = new ImageIcon(f.getAbsolutePath());
                    Image image = icon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
                    wallpaperLabel.setIcon(new ImageIcon(image));
                    wallpaperButton.setText("reload");
                }
            }
        });
        dialog.add(wallpaperButton, gbc);

        //dialog.add(dialog);
        dialog.setVisible(true);
    }
}
