package com.jongber.game.desktop.editor.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.Utility;
import com.jongber.game.desktop.editor.common.ViewControlArea;
import com.jongber.game.desktop.editor.map.event.AddTextureEvent;
import com.jongber.game.desktop.editor.map.event.MapSizeEvent;
import com.jongber.game.desktop.editor.map.event.AddRoomEvent;
import com.jongber.game.desktop.editor.map.event.DelObjectEvent;
import com.jongber.game.desktop.common.event.ClearAllEvent;
import com.jongber.game.projectz.Const;
import com.jongber.game.projectz.json.MapJson;
import com.jongber.game.projectz.json.RoomJson;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

class MapEditorCmd extends JFrame {

    private MapEditorView view;
    private GameLayer roomLayer;
    private GameLayer backLayer;
    String basePath;

    MapInfoArea infoArea;
    MapPropsArea propsArea;
    RoomArea roomArea;
    private SaveLoadArea slArea;
    private ViewControlArea viewControlArea;

    private MapEditorCmd(MapEditorView view) {
        super();
        this.view = view;
        this.backLayer = view.backLayer;
        this.roomLayer = view.roomLayer;
        this.basePath = System.getProperty("user.dir") +
                File.separator + "android" + File.separator + "assets";
        this.viewControlArea = new ViewControlArea(this, view, roomLayer);
    }

    static void popMapEditorCmd(MapEditorView layer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MapEditorCmd cmd = new MapEditorCmd(layer);
                cmd.init();
            }
        }).start();
    }

    private void init() {
        setTitle("Map Editor Commander");
        setSize(450, 450);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                Gdx.app.exit();
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ///// active panel area
        JPanel activePanel = this.viewControlArea.createPanel();
        ///// active panel area end
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 10;
        this.add(activePanel, gbc);

        ///// size panel area
        JPanel sizeArea = createMapInfoPanel();
        ///// size panel area
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(sizeArea, gbc);

        ///// propsPanel panel area
        JPanel propsPanel = createMapPropsArea();
        ///// propsPanel panel area
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        this.add(propsPanel, gbc);

        ///// room panel area
        JPanel roomPanel = this.createRoomPanel();
        ///// room panel area end
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1;
        this.add(roomPanel, gbc);

        ///// save load panel
        JPanel saveLoadPanel = this.createSaveLoadPanel();
        ///// save load panel end
        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(saveLoadPanel, gbc);

        this.setDetailArea(false);

        setVisible(true);
    }

    private JPanel createSaveLoadPanel() {
        slArea = new SaveLoadArea(this);
        JPanel panel = new JPanel();

        panel.add(slArea.saveButton);
        panel.add(slArea.loadButton);

        return panel;
    }

    private JPanel createRoomPanel() {
        roomArea = new RoomArea(this.roomLayer, this);

        JPanel panel = roomArea.panel;

        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Room area"));

        JScrollPane scroll = new JScrollPane(roomArea.roomTable);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(roomArea.addButton);
        buttonPanel.add(roomArea.delButton);

        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createMapInfoPanel() {
        this.infoArea = new MapInfoArea(this.backLayer, this);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Map Size area"));
        panel.setLayout (new BorderLayout());

        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Map Name :"));
        namePanel.add(this.infoArea.nameText);
        panel.add(namePanel, BorderLayout.NORTH);

        JPanel sizePanel = new JPanel();
        sizePanel.add(new JLabel("Width :"));
        sizePanel.add(infoArea.widthSpinner);
        sizePanel.add(new JLabel("Height :"));
        sizePanel.add(infoArea.heightSpinner);
        sizePanel.add(new JLabel("Ground :"));
        sizePanel.add(infoArea.groundSpinner);
        panel.add(sizePanel, BorderLayout.CENTER);

        JPanel btPanel = new JPanel();
        btPanel.add(infoArea.applyButton);
        panel.add(btPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createMapPropsArea() {
        propsArea = new MapPropsArea(this.backLayer, this);

        JPanel panel = propsArea.panel;

        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Map Props area"));

        JScrollPane scroll = new JScrollPane(propsArea.table);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(propsArea.addButton);
        buttonPanel.add(propsArea.delButton);

        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    void setDetailArea(boolean enable) {
        this.roomArea.setEnable(enable);
        this.propsArea.setEnable(enable);
        this.slArea.setEnable(enable);
    }

    void onLoad(MapJson json) {
        clear();
        this.infoArea.onLoad(json);
        this.propsArea.onLoad(json);
        this.roomArea.onLoad(json);
    }

    void clear() {
        this.propsArea.clear();
        this.roomArea.clear();
    }
}

class MapInfoArea {
    private GameLayer layer;
    private MapEditorCmd cmd;

    JTextField nameText;
    JSpinner widthSpinner;
    JSpinner heightSpinner;
    JSpinner groundSpinner;
    JButton applyButton;

    MapInfoArea(GameLayer layer, MapEditorCmd cmd) {
        this.layer = layer;
        this.cmd = cmd;

        this.nameText = new JTextField(8);
        this.nameText.setText("NewMap");

        this.initSpinners();
        this.initButton();
    }

    void onLoad(MapJson json) {
        this.nameText.setText(json.name);
        this.widthSpinner.setValue(json.width);
        this.heightSpinner.setValue(json.height);
        this.groundSpinner.setValue(json.groundHeight);
        this.apply();
    }

    private void initSpinners() {
        SpinnerNumberModel model = new SpinnerNumberModel(16, 3, 100, 1);
        this.widthSpinner = new JSpinner(model);

        model = new SpinnerNumberModel(9, 9, 100, 1);
        this.heightSpinner = new JSpinner(model);

        model = new SpinnerNumberModel(3, 1, 100, 1);
        this.groundSpinner = new JSpinner(model);
    }

    private void initButton() {
        this.applyButton = new JButton("Apply");
        this.applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                apply();
            }
        });
    }

    private void apply() {
        int width = (int)widthSpinner.getValue() * Const.BlockSize;
        int height = (int)heightSpinner.getValue() * Const.BlockSize;
        int groundHeight = (int)groundSpinner.getValue() * Const.BlockSize;
        layer.post(new MapSizeEvent(layer,
                Color.RED,
                3,
                0, 0,
                width, height, groundHeight));

        cmd.setDetailArea(true);
    }
}

class SaveLoadArea {
    private MapEditorCmd cmd;

    JButton saveButton;
    JButton loadButton;

    SaveLoadArea(MapEditorCmd cmd) {
        this.cmd = cmd;

        this.initSave();
        this.initLoad();
    }

    private void initSave() {
        this.saveButton = new JButton("Save");
        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String jsonPath = cmd.basePath
                        + File.separator + cmd.infoArea.nameText.getText() + ".map";

                JFileChooser fc = new JFileChooser(cmd.basePath);
                fc.setSelectedFile(new File(jsonPath));
                fc.setFileFilter(new FileNameExtensionFilter("Map data file", "map"));

                int i = fc.showSaveDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    if (f.exists()) {
                        int r = JOptionPane.showConfirmDialog(null, "Exist file, overwrite?", "Caution", JOptionPane.YES_NO_OPTION);
                        if (r != 0)
                            return;
                    }

                    save(f);
                }
            }
        });
    }

    private void save(File file) {
        MapJson json = new MapJson();
        json.name = cmd.infoArea.nameText.getText();
        json.width = (int)cmd.infoArea.widthSpinner.getValue();
        json.height = (int)cmd.infoArea.heightSpinner.getValue();
        json.groundHeight = (int)cmd.infoArea.groundSpinner.getValue();

        synchronized (cmd.propsArea) {
            int rows = cmd.propsArea.data.getRowCount();
            for (int i = 0; i < rows; ++i) {
                GameObject object = cmd.propsArea.objects.get(i);
                String path = (String)cmd.propsArea.data.getValueAt(i, 0);
                Tuple2<String, Vector2> info = new Tuple2<>(path, object.transform.getLocalPos());
                json.backProps.add(info);
            }
        }

        synchronized (cmd.roomArea) {
            for (Tuple2<GameObject, RoomJson> item : cmd.roomArea.rooms) {
                Tuple2<RoomJson, Vector2> jsonElem = new Tuple2<>(item.getItem2(), item.getItem1().transform.getLocalPos());
                json.rooms.add(jsonElem);
            }
        }

        Utility.writeJson(json, file);
    }

    private void initLoad() {
        this.loadButton = new JButton("Load");
        this.loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser(cmd.basePath);
                fc.setFileFilter(new FileNameExtensionFilter("Map data file", "map"));
                int i = fc.showOpenDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    try {
                        MapJson json = Utility.readJson(MapJson.class, fc.getSelectedFile());
                        load(json);
                    }
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Invalid json file, Map json file plz");
                    }
                }
            }
        });
    }

    private void load(MapJson json) {
        cmd.clear();
        cmd.onLoad(json);
    }

    void setEnable(boolean enable) {
        this.saveButton.setEnabled(enable);
    }
}

class MapPropsArea {
    private GameLayer layer;
    private String basePath;
    private MapEditorCmd cmd;

    JPanel panel = new JPanel();
    JButton addButton = new JButton("Add Map Prop");
    JButton delButton = new JButton("Del Map Prop ");
    JTable table;
    DefaultTableModel data;

    List<GameObject> objects = new ArrayList<>();
    private Timer timer;

    MapPropsArea(GameLayer layer, MapEditorCmd cmd) {
        this.layer = layer;
        this.basePath = cmd.basePath
                + File.separator + "projectz";
        this.cmd = cmd;

        this.initAdd();
        this.initDel();
        this.initTable();
        this.initTimer();
    }

    void setEnable(boolean enable) {
        this.table.setEnabled(enable);
        this.addButton.setEnabled(enable);
        this.delButton.setEnabled(enable);
        this.panel.setEnabled(enable);
        if (enable) {
            if (!this.timer.isRunning())
                this.timer.start();
        }
        else {
            this.timer.stop();
        }
    }

    void onLoad(MapJson json) {
        for (Tuple2<String, Vector2> prop : json.backProps) {
            onAddProp(prop.getItem1(), prop.getItem2());
        }
    }

    void clear() {
        synchronized (this) {
            this.timer.stop();
            this.layer.post(new ClearAllEvent(this.layer));
            int rows = this.data.getRowCount();
            for (int i = 0; i < rows; ++i) {
                this.data.removeRow(0);
            }
            objects.clear();
        }
    }

    private void initAdd() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser(basePath);
                fc.setFileFilter(new FileNameExtensionFilter("image files", "png", "PNG", "JPG", "jpg"));

                int i = fc.showOpenDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File assetDir = new File(cmd.basePath);
                    File file = fc.getSelectedFile();
                    if (!file.getPath().contains(assetDir.getPath())) {
                        JOptionPane.showMessageDialog(null, "Invalid path, use only under android/asset");
                        return;
                    }

                    String path = assetDir.toURI().relativize(file.toURI()).getPath();
                    onAddProp(path, Vector2.Zero);
                }
            }
        });
    }

    private void onAddProp(String path, Vector2 pos) {
        this.layer.post(new AddTextureEvent(this.layer, path, new AddTextureEvent.Callback() {
            @Override
            public void callback(AddTextureEvent event, GameObject created) {
                created.transform.local.setToTranslation(pos);
                objects.add(created);
                data.addRow(new String[] { event.texturePath, created.transform.getWorldPos().toString() });
            }
        }));
    }

    private void initDel() {
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onDelProp();
            }
        });
    }

    private void onDelProp() {
        int[] selected = this.table.getSelectedRows();
        delProps(selected);
    }

    private void delProps(int[] rows) {
        synchronized (this) {
            if (rows.length == 0)
                return;

            for (int i = rows.length - 1; i >= 0; --i) {
                this.data.removeRow(i);
                GameObject removed = this.objects.remove(i);
                this.layer.post(new DelObjectEvent(this.layer, removed));
            }
        }
    }

    private void initTable() {
        data = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        data.addColumn("Path");
        data.addColumn("Position");

        table = new JTable(data);
    }

    private void initTimer() {
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                synchronized (this) {
                    int rows = data.getRowCount();
                    for (int i = 0; i < rows; ++i) {
                        GameObject object = objects.get(i);
                        Vector2 worldPos = object.transform.getWorldPos();

                        data.setValueAt(worldPos, i, 1);
                    }
                }
            }
        });

        timer.start();
    }
}

class RoomArea {
    private GameLayer roomLayer;
    private String basePath;

    JPanel panel = new JPanel();
    JButton addButton = new JButton("Add room");
    JButton delButton = new JButton("Del room ");
    JTable roomTable;
    private DefaultTableModel roomData;

    List<Tuple2<GameObject, RoomJson>> rooms = new ArrayList<>();
    private Timer timer;

    RoomArea(GameLayer roomLayer, MapEditorCmd cmd) {
        this.roomLayer = roomLayer;
        this.basePath = cmd.basePath
                + File.separator + "projectz"
                + File.separator + "house"
                + File.separator + "room";
        initTable();
        initAdd();
        initDel();

        initTimer();
    }

    void setEnable(boolean enable) {
        this.roomTable.setEnabled(enable);
        this.addButton.setEnabled(enable);
        this.delButton.setEnabled(enable);
        this.panel.setEnabled(enable);
        if (enable) {
            if (!this.timer.isRunning())
                this.timer.start();
        }
        else {
            this.timer.stop();
        }
    }

    void onLoad(MapJson json) {
        for (Tuple2<RoomJson, Vector2> tuple : json.rooms) {
            addRoom(tuple.getItem1(), tuple.getItem2());
        }
    }

    void clear() {
        synchronized (this) {
            this.timer.stop();
            this.roomLayer.post(new ClearAllEvent(roomLayer));
            int rows = this.roomData.getRowCount();
            for (int i = 0; i < rows; i++) {
                this.roomData.removeRow(0);
            }
            this.rooms.clear();
        }
    }

    private void initTimer() {
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                synchronized (this) {
                    int rows = roomData.getRowCount();
                    for (int i = 0; i < rows; ++i) {
                        GameObject object = rooms.get(i).getItem1();
                        Vector2 localPos = object.transform.getLocalPos();
                        Vector2 worldPos = object.transform.getWorldPos();

                        roomData.setValueAt(localPos, i, 1);
                        roomData.setValueAt(worldPos, i, 2);
                    }
                }
            }
        });

        timer.start();
    }

    private void initTable() {
        roomData = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        roomData.addColumn("Name");
        roomData.addColumn("Local pos");
        roomData.addColumn("World pos");

        roomTable = new JTable(roomData);
    }

    private void initAdd() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser(basePath);
                fc.setFileFilter(new FileNameExtensionFilter("Room data file", "room"));

                int i = fc.showOpenDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    RoomJson json = Utility.readJson(RoomJson.class, file);
                    addRoom(json, Vector2.Zero);
                }
            }
        });
    }

    private void initDel() {
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onRoomDelete();
            }
        });
    }

    private void addRoom(RoomJson json, Vector2 pos) {
        AddRoomEvent e = new AddRoomEvent(roomLayer, json, new AddRoomEvent.Callback() {
            @Override
            public void callback(GameObject created) {
                RoomArea.this.onRoomAdd(created, json, pos);
            }
        });

        this.roomLayer.post(e);
    }

    private void onRoomDelete() {
        this.deleteRooms(this.roomTable.getSelectedRows());
    }

    private void deleteRooms(int[] rows) {
        synchronized (this) {
            if (rows.length == 0)
                return;

            for (int i = rows.length - 1; i >= 0; --i) {
                this.roomData.removeRow(i);
                Tuple2<GameObject, RoomJson> removed = this.rooms.remove(i);

                roomLayer.post(new DelObjectEvent(roomLayer, removed.getItem1()));
            }
        }
    }

    private void onRoomAdd(GameObject created, RoomJson json, Vector2 pos) {
        synchronized (this) {
            created.transform.local.setToTranslation(pos);
            this.rooms.add(new Tuple2<>(created, json));
            this.roomData.addRow(new String[] {
                    created.name,
                    created.transform.getLocalPos().toString(),
                    created.transform.getWorldPos().toString()
            });
        }
    }
}

