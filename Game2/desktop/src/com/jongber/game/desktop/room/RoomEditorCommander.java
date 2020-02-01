package com.jongber.game.desktop.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.desktop.room.event.AddPropEvent;
import com.jongber.game.desktop.room.event.ClearRoomViewEvent;
import com.jongber.game.desktop.room.event.DelPropEvent;
import com.jongber.game.desktop.room.event.ShowGridEvent;
import com.jongber.game.projectz.Const;
import com.jongber.game.desktop.room.event.ApplyRoomViewEvent;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class RoomEditorCommander extends JFrame {

    private GameLayer layer;
    private JTextField roomNameField;
    private String wallpaperPath = "";
    private JButton propAddButton = new JButton("Add prop");
    private JButton propDelButton = new JButton("Del prop ");
    private JTable propTable;
    private DefaultTableModel propData;

    private final List<GameObject> propObjects = new ArrayList<>();
    private Timer timer;

    public RoomEditorCommander(GameLayer layer) {
        this.layer = layer;
        this.roomNameField = new JTextField(8);
        this.roomNameField.setText("Room Name");
        this.initPropArea();
        this.initTimer();
    }

    static void popRoomUI(GameLayer layer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                _popRoomUI(layer);
            }
        }).start();
    }

    private static void _popRoomUI(GameLayer layer) {

        RoomEditorCommander window = new RoomEditorCommander(layer);
        window.setTitle("Room Editor Commander");
        window.setSize(450, 500);
        //propertyPanel.setResizable(false);
        window.setLayout(new GridBagLayout());
        GridBagConstraints dialogGbc = new GridBagConstraints();
        dialogGbc.fill = GridBagConstraints.HORIZONTAL;

        ///// active panel area
        JPanel activePanel = window.createActivePanel(layer);
        ///// active panel area end

        dialogGbc.gridx = 0;
        dialogGbc.gridy = 0;
        dialogGbc.ipady = 10;
        window.add(activePanel, dialogGbc);

        //// property panel
        JPanel propertyPanel = window.createRoomPropertyPanel(layer);
        //// property end

        dialogGbc.gridx = 0;
        dialogGbc.gridy = 1;
        window.add(propertyPanel, dialogGbc);

        //// propObjects panel
        JPanel propsPanel = window.createPropsPanel();
        //// propObjects end

        dialogGbc.gridx = 0;
        dialogGbc.gridy = 2;
        dialogGbc.weightx = 1;
        dialogGbc.weighty = 0.3;
        //dialogGbc.insets = new Insets(0,10,0,10);
        dialogGbc.fill = GridBagConstraints.BOTH;
        window.add(propsPanel, dialogGbc);

        window.setVisible(true);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                Gdx.app.exit();
            }
        });
    }

    private JPanel createActivePanel(GameLayer layer) {
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

        return activePanel;
    }

    private JPanel createRoomPropertyPanel(GameLayer layer) {
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
        propertyPanel.add(this.roomNameField, panelGbc);

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

                    RoomEditorCommander.this.wallpaperPath = baseFile.toURI().relativize(selectedFile.toURI()).getPath();

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
                String name = RoomEditorCommander.this.roomNameField.getText();
                int sanity = sanitySlider.getValue();
                int noise = noiseSlider.getValue();
                int height = 48;
                int width = (Integer)widthSpinner.getValue();
                width *= Const.BlockSize;
                String wallpaperPath = RoomEditorCommander.this.wallpaperPath;

                if (RoomEditorCommander.this.validateAndCreateRoomProperty(name, sanity, noise, height, width, wallpaperPath, layer)) {
                    RoomEditorCommander.this.onApplied();
                }
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
                RoomEditorCommander.this.onClear();
            }
        });
        propertyPanel.add(clear, panelGbc);

        return propertyPanel;
    }

    private JPanel createPropsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Editor Cmd"));

        JScrollPane pane = new JScrollPane(propTable);

        panel.add(pane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        this.propAddButton.setEnabled(false);
        this.propDelButton.setEnabled(false);

        buttonPanel.add(this.propAddButton);
        buttonPanel.add(this.propDelButton);

        panel.add(buttonPanel, BorderLayout.EAST);
        return panel;
    }

    private void initPropArea() {

        propData = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        propData.addColumn("Path");
        propData.addColumn("Position");
        propTable = new JTable(this.propData);

        propAddButton.addActionListener(new ActionListener() {
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

                    String path = baseFile.toURI().relativize(selectedFile.toURI()).getPath();
                    onPropAdd(path);
                }
            }
        });

        propDelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = RoomEditorCommander.this.propTable.getSelectedRow();
                if (row >= 0) {
                    RoomEditorCommander.this.onPropDelete(row);
                }
            }
        });
    }

    private void initTimer() {
        this.timer = new Timer(45, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                synchronized (RoomEditorCommander.this.propObjects) {
                    int rows = RoomEditorCommander.this.propData.getRowCount();
                    for (int i = 0; i < rows; ++i) {
                        GameObject object = RoomEditorCommander.this.propObjects.get(i);
                        Vector2 pos = object.transform.getLocalPos();

                        RoomEditorCommander.this.propData.setValueAt(pos.toString(), i, 1);
                    }
                }
            }
        });
    }

    private boolean validateAndAddProps(GameLayer layer, String path) {
        layer.post(new AddPropEvent(layer,
                                    RoomEditorCommander.this.roomNameField.getText(),
                                    path,
                                    new GameEvent.Callback() {
                                        @Override
                                        public void callback(GameEvent event) {
                                            AddPropEvent e = (AddPropEvent)event;
                                            synchronized (RoomEditorCommander.this.propObjects) {
                                                RoomEditorCommander.this.propData.addRow(new String[] {path, "0, 0"});
                                                RoomEditorCommander.this.propObjects.add(e.created);
                                            }
                                        }
                                    }));

        return true;
    }

    private boolean validateAndCreateRoomProperty(String name,
                                                         int sanity,
                                                         int noise,
                                                         int height,
                                                         int width,
                                                         String wallpaperPath, GameLayer layer) {

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Room name vacant!");
            return false;
        }

        if (width <= 0) {
            JOptionPane.showMessageDialog(null, "Width cannot under Zero!");
            return false;
        }

        String basePath = System.getProperty("user.dir") +
                File.separator + "android" +
                File.separator + "assets" +
                File.separator + wallpaperPath;
        File f = new File(basePath);
        if (f.isDirectory() || f.exists() == false) {
            JOptionPane.showMessageDialog(null, "Where wallpaper !!");
            return false;
        }

        layer.post(new ApplyRoomViewEvent(layer, name, sanity, noise, height, width, wallpaperPath));

        return true;
    }

    private void onPropAdd(String path) {
        RoomEditorCommander.this.validateAndAddProps(layer, path);

        if (this.timer.isRunning() == false) {
            this.timer.start();
        }
    }

    private void onPropDelete(int row) {
        synchronized (this.propObjects) {
            RoomEditorCommander.this.propData.removeRow(row);
            layer.post(new DelPropEvent(layer, roomNameField.getText(), propObjects.get(row)));
            this.propObjects.remove(row);
        }

        if (propData.getRowCount() == 0) {
            this.timer.stop();
        }
    }

    private void onApplied() {
        this.propAddButton.setEnabled(true);
        this.propDelButton.setEnabled(true);
    }

    private void onClear() {
        int cnt = this.propData.getRowCount();
        for (int i = 0; i < cnt; ++i) {
            this.propData.removeRow(0);
        }

        this.propAddButton.setEnabled(false);
        this.propDelButton.setEnabled(false);

        synchronized (this.propObjects) {
            this.propObjects.clear();
        }

        this.timer.stop();
    }
}
