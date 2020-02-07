package com.jongber.game.desktop.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.Utility;
import com.jongber.game.desktop.room.event.AddPropEvent;
import com.jongber.game.desktop.room.event.ClearRoomViewEvent;
import com.jongber.game.desktop.room.event.DelPropEvent;
import com.jongber.game.desktop.viewer.event.ShowGridEvent;
import com.jongber.game.projectz.Const;
import com.jongber.game.desktop.room.event.ApplyRoomViewEvent;
import com.jongber.game.projectz.json.RoomJson;

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
import java.io.IOException;
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
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;


class RoomEditorCommander extends JFrame {

    private GameLayer layer;

    final String basePath;
    final PropertyArea property;
    final PropsArea props;
    private final SaveLoadArea saveLoadArea;

    private final Timer timer;

    private RoomEditorCommander(GameLayer layer) {
        this.basePath = System.getProperty("user.dir") +
                File.separator + "android" + File.separator + "assets";

        this.layer = layer;
        this.property = new PropertyArea(layer, this);
        this.props = new PropsArea(layer, this, property.roomNameField);
        this.saveLoadArea = new SaveLoadArea(this, layer);

        this.timer = new Timer(60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                synchronized (props) {
                    int rows = props.propData.getRowCount();
                    for (int i = 0; i < rows; ++i) {
                        GameObject object = props.propObjects.get(i);
                        Vector2 pos = object.transform.getLocalPos();

                        props.propData.setValueAt(pos, i, 1);
                    }
                }
            }
        });
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
        window.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ///// active panel area
        JPanel activePanel = window.createActivePanel();
        ///// active panel area end

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 10;
        window.add(activePanel, gbc);

        //// property panel
        JPanel propertyPanel = window.createRoomPropertyPanel();
        //// property end

        gbc.gridx = 0;
        gbc.gridy = 1;
        window.add(propertyPanel, gbc);

        //// propObjects panel
        JPanel propsPanel = window.createPropsPanel();
        //// propObjects end

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        window.add(propsPanel, gbc);

        //// write load panel
        JPanel saveLoadPanel = window.createSaveLoadPanel();
        //// write load panel end

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        window.add(saveLoadPanel, gbc);

        window.setVisible(true);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                Gdx.app.exit();
            }
        });
    }

    public void onApplied() {
        this.property.onApplied();
        this.props.onApplied();
        this.timer.start();
        this.saveLoadArea.onApplied();
    }

    public void onClear() {
        layer.post(new ClearRoomViewEvent(layer));
        this.property.onClear();
        this.props.onClear();
        this.timer.stop();
        this.saveLoadArea.onClear();
    }

    public void fromRoomJson(RoomJson json) {
        this.onClear();

        property.roomNameField.setText(json.name);
        property.sanitySlider.setValue(json.sanity);
        property.widthSpinner.setValue(json.width / Const.BlockSize);
        property.wallpaperPath = json.wallpaperPath;

        ImageIcon icon = new ImageIcon(basePath + File.separator + json.wallpaperPath);
        Image image = icon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        property.wallpaperLabel.setIcon(new ImageIcon(image));
        property.wallpaperButton.setText("reload");

        property.noiseSlider.setValue(json.noise);

        property.validateAndCreateRoomProperty();

        for (Tuple2<String, Vector2> item : json.props) {
            props.validateAndAddProps(item.getItem1(), item.getItem2());
        }

        this.onApplied();
    }

    private JPanel createActivePanel() {
        JPanel activePanel = new JPanel();
        activePanel.setLayout(new GridBagLayout());
        activePanel.setBorder(BorderFactory.createTitledBorder("Viewer Cmd"));
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

    private JPanel createRoomPropertyPanel() {
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
        propertyPanel.add(this.property.roomNameField, panelGbc);

        // 2. sanity
        panelGbc.gridx = 0;
        panelGbc.gridy = 1;
        propertyPanel.add(this.property.sanityLabel, panelGbc);

        panelGbc.gridx = 1;
        panelGbc.gridy = 1;
        propertyPanel.add(this.property.sanitySlider, panelGbc);

        // 3. noise
        panelGbc.gridx = 0;
        panelGbc.gridy = 2;
        propertyPanel.add(this.property.noiseLabel, panelGbc);

        panelGbc.gridx = 1;
        panelGbc.gridy = 2;
        propertyPanel.add(this.property.noiseSlider, panelGbc);

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
        propertyPanel.add(this.property.widthSpinner, panelGbc);

        panelGbc.gridx = 2;
        panelGbc.gridy = 4;
        propertyPanel.add(new Label("block"), panelGbc);

        // 6. wallpaper
        panelGbc.gridx = 0;
        panelGbc.gridy = 5;
        propertyPanel.add(property.wallpaperLabel, panelGbc);

        panelGbc.gridx = 1;
        panelGbc.gridy = 5;
        propertyPanel.add(property.wallpaperButton, panelGbc);

        // 7. Apply & Clear
        panelGbc.gridx = 1;
        panelGbc.gridy = 6;
        panelGbc.insets = new Insets(10,10,0,0);
        propertyPanel.add(property.applyButton, panelGbc);

        panelGbc.gridx = 2;
        panelGbc.gridy = 6;
        propertyPanel.add(property.clearButton, panelGbc);

        return propertyPanel;
    }

    private JPanel createPropsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Prop list"));

        JScrollPane pane = new JScrollPane(this.props.propTable);
        panel.add(pane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(this.props.propAddButton);
        buttonPanel.add(this.props.propDelButton);

        panel.add(buttonPanel, BorderLayout.EAST);
        return panel;
    }

    private JPanel createSaveLoadPanel() {
        JPanel panel = new JPanel();

        panel.add(this.saveLoadArea.saveButton);
        panel.add(this.saveLoadArea.loadButton);

        return panel;
    }
}

class PropertyArea {
    private GameLayer layer;
    private RoomEditorCommander cmd;

    JTextField roomNameField;

    Label sanityLabel;
    JSlider sanitySlider;

    Label noiseLabel;
    JSlider noiseSlider;

    int heightBlock = 3;
    JSpinner widthSpinner;

    String wallpaperPath = "";
    JLabel wallpaperLabel;
    JButton wallpaperButton;

    JButton applyButton;
    JButton clearButton;

    PropertyArea(GameLayer layer, RoomEditorCommander cmd) {
        this.layer = layer;
        this.cmd = cmd;

        this.roomNameField = new JTextField(8);
        this.roomNameField.setText("RoomName");

        initSanity();
        initNoise();
        initWidth();
        initWallpaper();
        initApply();
        initClear();
    }

    public void onApplied() {

    }

    public void onClear() {

    }

    private void initSanity() {
        this.sanityLabel = new Label("Sanity: 50");

        this.sanitySlider = new JSlider();
        this.sanitySlider.setMinimum(0);
        this.sanitySlider.setMaximum(100);
        this.sanitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                sanityLabel.setText("Sanity: " + sanitySlider.getValue());
            }
        });
    }

    private void initNoise() {
        this.noiseLabel = new Label("Noise: 50");
        this.noiseSlider =  new JSlider();
        this.noiseSlider.setMinimum(0);
        this.noiseSlider.setMaximum(100);
        this.noiseSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                noiseLabel.setText("Noise: " + noiseSlider.getValue());
            }
        });
    }

    private void initWidth() {
        SpinnerNumberModel model = new SpinnerNumberModel(3, 3, 50, 1);
        this.widthSpinner = new JSpinner(model);
        this.widthSpinner.setValue(3);
    }

    private void initWallpaper() {
        wallpaperLabel = new JLabel("Wallpaper: ");
        wallpaperButton = new JButton("Load");
        wallpaperButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File baseFile = new File(cmd.basePath);

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

                    wallpaperPath = baseFile.toURI().relativize(selectedFile.toURI()).getPath();

                    ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                    Image image = icon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
                    wallpaperLabel.setIcon(new ImageIcon(image));
                    wallpaperButton.setText("reload");
                }
            }
        });
    }

    private void initApply() {
        applyButton = new JButton("\tApply property\t");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (validateAndCreateRoomProperty()) {
                    PropertyArea.this.cmd.onApplied();
                }
            }
        });
    }

    private void initClear() {
        clearButton = new JButton("\t   Clear view   \t");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cmd.onClear();
            }
        });
    }

    boolean validateAndCreateRoomProperty() {
        String name = roomNameField.getText();
        int width = (Integer)this.widthSpinner.getValue();
        width *= Const.BlockSize;
        int sanity = this.sanitySlider.getValue();
        int noise = this.noiseSlider.getValue();
        int height = this.heightBlock * Const.BlockSize;

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Room name vacant!");
            return false;
        }

        if ((Integer)this.widthSpinner.getValue() <= 0) {
            JOptionPane.showMessageDialog(null, "Width cannot under Zero!");
            return false;
        }

        String basePath = cmd.basePath + File.separator + wallpaperPath;
        File f = new File(basePath);
        if (f.isDirectory() || f.exists() == false) {
            JOptionPane.showMessageDialog(null, "Where wallpaper !!");
            return false;
        }

        layer.post(new ApplyRoomViewEvent(layer, name, sanity, noise, height, width, wallpaperPath));

        return true;
    }
}

class PropsArea {
    private GameLayer layer;
    private JTextField roomField;
    private RoomEditorCommander cmd;

    public JButton propAddButton = new JButton("Add prop");
    public JButton propDelButton = new JButton("Del prop ");
    public JTable propTable;
    public DefaultTableModel propData;

    public final List<GameObject> propObjects = new ArrayList<>();

    public PropsArea(GameLayer layer, RoomEditorCommander cmd, JTextField roomField) {
        this.layer = layer;
        this.roomField = roomField;
        this.cmd = cmd;

        propData = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        propData.addColumn("Path");
        propData.addColumn("Position");
        propTable = new JTable(this.propData);

        initPropAdd();
        initPropDel();
    }

    public void onApplied() {
        this.propAddButton.setEnabled(true);
        this.propDelButton.setEnabled(true);

        synchronized (this) {
            this.propObjects.clear();
            int cnt = this.propData.getRowCount();
            for (int i = 0; i < cnt; ++i) {
                this.propData.removeRow(0);
            }
        }
    }

    public void onClear() {
        this.propAddButton.setEnabled(false);
        this.propDelButton.setEnabled(false);

        synchronized (this) {
            this.propObjects.clear();
            int cnt = this.propData.getRowCount();
            for (int i = 0; i < cnt; ++i) {
                this.propData.removeRow(0);
            }
        }
    }

    private void initPropAdd() {
        propAddButton.setEnabled(false);
        propAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File baseFile = new File(cmd.basePath);

                JFileChooser fc = new JFileChooser(baseFile);
                fc.setAcceptAllFileFilterUsed(false);
                fc.setFileFilter(new FileNameExtensionFilter("image files", "png", "PNG", "JPG", "jpg"));

                int i = fc.showOpenDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fc.getSelectedFile();
                    if (selectedFile.getPath().contains(baseFile.getPath()) == false) {
                        JOptionPane.showMessageDialog(null, "Invalid path, use only under android/asset");
                        return;
                    }

                    String path = baseFile.toURI().relativize(selectedFile.toURI()).getPath();
                    validateAndAddProps(path, Vector2.Zero);
                }
            }
        });
    }

    private void initPropDel() {
        propDelButton.setEnabled(false);
        propDelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                synchronized (PropsArea.this) {
                    int row = propTable.getSelectedRow();
                    if (row < 0) {
                        return;
                    }

                    propData.removeRow(row);
                    layer.post(new DelPropEvent(layer, propObjects.get(row)));
                }
            }
        });
    }

    void validateAndAddProps(String path, Vector2 pos) {
        layer.post(new AddPropEvent(layer,
                roomField.getText(),
                path,
                pos,
                new AddPropEvent.Callback() {
                    @Override
                    public void callback(GameObject created) {
                        synchronized (PropsArea.this) {
                            PropsArea.this.propData.addRow(new String[] {path, "0, 0"});
                            PropsArea.this.propObjects.add(created);
                        }
                    }
                }));
    }
}

class SaveLoadArea {
    private RoomEditorCommander cmd;
    private GameLayer layer;
    private String jsonPath;

    JButton saveButton;
    JButton loadButton;

    public SaveLoadArea(RoomEditorCommander cmd, GameLayer layer) {
        this.cmd = cmd;
        this.layer = layer;
        this.jsonPath = cmd.basePath + File.separator + "projectz"
                + File.separator + "house" + File.separator;
        initSave();
        initLoad();

    }

    public void onApplied() {
        saveButton.setEnabled(true);
    }

    public void onClear() {
        saveButton.setEnabled(false);
    }

    private void initSave() {
        saveButton = new JButton("Save");
        this.saveButton.setEnabled(false);
        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    File baseFile = new File(jsonPath + cmd.property.roomNameField.getText() + ".json");

                    JFileChooser fc = new JFileChooser(baseFile);
                    fc.setSelectedFile(baseFile);
                    fc.setFileFilter(new FileNameExtensionFilter("json", "json"));

                    int i = fc.showSaveDialog(null);
                    if (i == JFileChooser.APPROVE_OPTION) {
                        String extension = fc.getFileFilter().getDescription();
                        File f = fc.getSelectedFile();
                        if (f.getPath().endsWith(extension) == false) {
                            f = new File(f.getPath() + "." + extension);
                        }

                        if (f.exists() == false) {
                            f.createNewFile();
                        }
                        else {
                            int confirm = JOptionPane.showConfirmDialog(null, "Exist file, overwrite?", "Caution", JOptionPane.YES_NO_OPTION);
                            if (confirm != 0) {
                                return;
                            }

                            f.delete();
                            f.createNewFile();
                        }

                        saveJson(f);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveJson(File file) {
        synchronized (cmd.props) {
            RoomJson roomJson = createRoomJson();
            Utility.writeJson(roomJson, file);
        }
    }

    private void initLoad() {
        loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser(jsonPath);
                fc.setFileFilter(new FileNameExtensionFilter("json", "json"));
                int i = fc.showOpenDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File selected = fc.getSelectedFile();
                    if (selected.exists() == false) return;

                    RoomJson roomJson = Utility.readJson(RoomJson.class, selected);
                    cmd.fromRoomJson(roomJson);
                }
            }
        });
    }

    private RoomJson createRoomJson() {
        RoomJson json = new RoomJson();

        json.name = cmd.property.roomNameField.getText();
        json.height = cmd.property.heightBlock * Const.BlockSize;
        json.width = (int)cmd.property.widthSpinner.getValue() * Const.BlockSize;
        json.noise = cmd.property.noiseSlider.getValue();
        json.sanity = cmd.property.sanitySlider.getValue();
        json.wallpaperPath = cmd.property.wallpaperPath;

        int rows = cmd.props.propData.getRowCount();
        for (int i = 0; i < rows; ++i) {
            GameObject object = cmd.props.propObjects.get(i);

            String path = (String)cmd.props.propData.getValueAt(i, 0);
            json.props.add(new Tuple2<>(path, object.transform.getLocalPos()));
        }

        return json;
    }
}