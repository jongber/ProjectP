package com.jongber.game.desktop.room;

import com.badlogic.gdx.Gdx;
import com.jongber.game.core.GameLayer;
import com.jongber.game.desktop.room.event.ClearRoomViewEvent;
import com.jongber.game.desktop.room.event.ShowGridEvent;
import com.jongber.game.projectz.Const;
import com.jongber.game.desktop.room.event.ApplyRoomViewEvent;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

class RoomEditorCommander {

    static void popRoomUI(GameLayer layer) {
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

    private static void _popRoomUI(GameLayer layer) {

        JFrame window = new JFrame();
        window.setTitle("Room Editor Commander");
        window.setSize(450, 350);
        //propertyPanel.setResizable(false);
        window.setLayout(new GridBagLayout());

        ///// active panel area
            JPanel activePanel = new JPanel();
            activePanel.setLayout(new GridBagLayout());
        activePanel.setBorder(BorderFactory.createTitledBorder("Editor Cmd"));
            GridBagConstraints activeGbc = new GridBagConstraints();

            // 1. show grid
            activeGbc.fill = GridBagConstraints.HORIZONTAL;
            activeGbc.gridx = 0;
            activeGbc.gridy = 0;
            activePanel.add(new JLabel("Show grid "), activeGbc);

            activeGbc.gridx = 1;
            activeGbc.gridy = 0;
            JCheckBox gridCheck = new JCheckBox();
            gridCheck.setSelected(true);
            gridCheck.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    boolean checked = e.getStateChange() == ItemEvent.SELECTED;
                    layer.post(new ShowGridEvent(layer, checked));
                }
            });
            activePanel.add(gridCheck, activeGbc);
        ///// active panel area end

        GridBagConstraints dialogGbc = new GridBagConstraints();
        dialogGbc.fill = GridBagConstraints.HORIZONTAL;
        dialogGbc.gridx = 0;
        dialogGbc.gridy = 0;
        dialogGbc.ipady = 10;

        window.add(activePanel, dialogGbc);


        ////// panel area
            JPanel propertyPanel = new JPanel();
            propertyPanel.setBorder(BorderFactory.createTitledBorder("Room Property"));
            propertyPanel.setLayout(new GridBagLayout());
            GridBagConstraints panelGbc = new GridBagConstraints();
            //gbc.insets = new Insets(5,5,0,0);

            // 1. room name
            panelGbc.fill = GridBagConstraints.VERTICAL;
            panelGbc.gridx = 0;
            panelGbc.gridy = 0;
            propertyPanel.add(new Label("Room name "), panelGbc);

            panelGbc.gridx = 1;
            panelGbc.gridy = 0;
            TextField nameField = new TextField(8);
            propertyPanel.add(nameField, panelGbc);

            // 2. sanity
            panelGbc.gridx = 0;
            panelGbc.gridy = 1;
            Label sanityLabel = new Label("Sanity: 50");
            propertyPanel.add(sanityLabel, panelGbc);

            panelGbc.gridx = 1;
            panelGbc.gridy = 1;
            JSlider sanitySlider = new JSlider();
            sanitySlider.setMinimum(0);
            sanitySlider.setMaximum(100);
            sanitySlider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent changeEvent) {
                    sanityLabel.setText("Sanity: " + sanitySlider.getValue());
                }
            });
            propertyPanel.add(sanitySlider, panelGbc);

            // 3. noise
            panelGbc.gridx = 0;
            panelGbc.gridy = 2;
            Label noiseLabel = new Label("Noise: 50");
            propertyPanel.add(noiseLabel, panelGbc);

            panelGbc.gridx = 1;
            panelGbc.gridy = 2;
            JSlider noiseSlider = new JSlider();
            noiseSlider.setMinimum(0);
            noiseSlider.setMaximum(100);
            noiseSlider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent changeEvent) {
                    noiseLabel.setText("Noise: " + noiseSlider.getValue());
                }
            });
            propertyPanel.add(noiseSlider, panelGbc);

            // 4. height
            panelGbc.gridx = 0;
            panelGbc.gridy = 3;
            Label heightLabel = new Label("Height: ");
            propertyPanel.add(heightLabel, panelGbc);

            panelGbc.gridx = 1;
            panelGbc.gridy = 3;
            propertyPanel.add(new Label("3 block(48px) fixed"), panelGbc);

            // 5. width
            panelGbc.gridx = 0;
            panelGbc.gridy = 4;
            Label widthLabel = new Label("Width: ");
            propertyPanel.add(widthLabel, panelGbc);

            panelGbc.gridx = 1;
            panelGbc.gridy = 4;
            JSpinner widthSpinner = new JSpinner();
            widthSpinner.setValue(3);
            propertyPanel.add(widthSpinner, panelGbc);

            panelGbc.gridx = 2;
            panelGbc.gridy = 4;
            propertyPanel.add(new Label("block"), panelGbc);

            // 6. wallpaper
            panelGbc.gridx = 0;
            panelGbc.gridy = 5;
            JLabel wallpaperLabel = new JLabel("Wallpaper: ");
            propertyPanel.add(wallpaperLabel, panelGbc);

            panelGbc.gridx = 1;
            panelGbc.gridy = 5;
            JLabel wallpaperPathLabel = new JLabel("");
            JButton wallpaperButton = new JButton("Load");
            wallpaperButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String basePath = System.getProperty("user.dir") +
                            File.separator + "android" + File.separator + "assets";
                    File baseFile = new File(basePath);

                    JFileChooser fc = new JFileChooser(baseFile);
                    fc.setAcceptAllFileFilterUsed(false);
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("image files", "png", "PNG", "JPG", "jpg");
                    fc.setFileFilter(filter);

                    int i = fc.showOpenDialog(null);
                    if (i == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fc.getSelectedFile();
                        if (selectedFile.getPath().contains(baseFile.getPath()) == false) {
                            JOptionPane.showMessageDialog(null, "Invalid path, use only under android/asset");
                            return;
                        }

                        String relative = baseFile.toURI().relativize(selectedFile.toURI()).getPath();
                        wallpaperPathLabel.setText(relative);

                        ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                        Image image = icon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
                        wallpaperLabel.setIcon(new ImageIcon(image));
                        wallpaperButton.setText("reload");
                    }
                }
            });
            propertyPanel.add(wallpaperButton, panelGbc);

            // 7. Apply & Clear
            panelGbc.gridx = 1;
            panelGbc.gridy = 6;
            panelGbc.insets = new Insets(10,10,0,0);
            JButton apply = new JButton("\tApply property\t");
            apply.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String name = nameField.getText();
                    int sanity = sanitySlider.getValue();
                    int noise = noiseSlider.getValue();
                    int height = 48;
                    int width = (Integer)widthSpinner.getValue();
                    width *= Const.BlockSize;
                    String wallpaperPath = wallpaperPathLabel.getText();

                    RoomEditorCommander.validateAndCreateRoomProperty(name, sanity, noise, height, width, wallpaperPath, layer);
                }
            });
            propertyPanel.add(apply, panelGbc);

            panelGbc.gridx = 2;
            panelGbc.gridy = 6;
            JButton clear = new JButton("\t   Clear view   \t");
            clear.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    layer.post(new ClearRoomViewEvent(layer));
                }
            });
            propertyPanel.add(clear, panelGbc);

            // 8. Save & Load
            panelGbc.gridx = 1;
            panelGbc.gridy = 7;
            JButton save = new JButton("\tSave property\t");
            propertyPanel.add(save, panelGbc);

            panelGbc.gridx = 2;
            panelGbc.gridy = 7;
            JButton load = new JButton("\tLoad property\t");
            propertyPanel.add(load, panelGbc);

        //// panel area end
        dialogGbc.gridx = 0;
        dialogGbc.gridy = 1;
        window.add(propertyPanel, dialogGbc);

        window.setVisible(true);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                Gdx.app.exit();
            }
        });

    }

    private static boolean validateAndCreateRoomProperty(String name,
                                                         int sanity,
                                                         int noise,
                                                         int height,
                                                         int width,
                                                         String wallpaperPath, GameLayer layer) {

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Room name vacant!");
            return false;
        }

        layer.post(new ApplyRoomViewEvent(layer, name, sanity, noise, height, width, wallpaperPath));

        return true;
    }
}
