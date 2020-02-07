package com.jongber.game.desktop.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.component.TextureComponent;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.Utility;
import com.jongber.game.desktop.map.event.AddTextureEvent;
import com.jongber.game.desktop.map.event.MapSizeEvent;
import com.jongber.game.desktop.map.event.AddRoomEvent;
import com.jongber.game.desktop.map.event.DelObjectEvent;
import com.jongber.game.desktop.viewer.event.ShowGridEvent;
import com.jongber.game.projectz.Const;
import com.jongber.game.projectz.json.MapJson;
import com.jongber.game.projectz.json.RoomJson;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

class MapEditorCmd extends JFrame {

    private GameLayer roomLayer;
    private GameLayer backLayer;
    String basePath;

    MapSizeArea sizeArea;
    MapPropsArea propsArea;
    RoomArea roomArea;
    SaveLoadArea slArea;

    private MapEditorCmd(GameLayer roomLayer, GameLayer backLayer) {
        super();
        this.backLayer = backLayer;
        this.roomLayer = roomLayer;
        this.basePath = System.getProperty("user.dir") +
                File.separator + "android" + File.separator + "assets";
    }

    static void popMapEditorCmd(GameLayer roomLayer, GameLayer backLayer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MapEditorCmd cmd = new MapEditorCmd(roomLayer, backLayer);
                cmd.init();
            }
        }).start();
    }

    private void init() {
        setTitle("Map Editor Commander");
        setSize(450, 400);
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
        JPanel activePanel = this.createActivePanel();
        ///// active panel area end
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 10;
        this.add(activePanel, gbc);

        ///// size panel area
        JPanel sizeArea = createSizePanel();
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
                roomLayer.post(new ShowGridEvent(roomLayer, checked));
            }
        });
        activePanel.add(gridCheck, activeGbc);

        return activePanel;
    }

    private JPanel createSaveLoadPanel() {
        slArea = new SaveLoadArea(this.roomLayer, this.backLayer, this);
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

    private JPanel createSizePanel() {
        this.sizeArea = new MapSizeArea(this.backLayer, this);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Map Size area"));

        panel.add(new JLabel("Width :"));
        panel.add(sizeArea.widthSpinner);
        panel.add(new JLabel("Height :"));
        panel.add(sizeArea.heightSpinner);
        panel.add(new JLabel("Ground :"));
        panel.add(sizeArea.groundSpinner);
        panel.add(sizeArea.applyButton);

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
}

class MapSizeArea {
    private GameLayer layer;
    private MapEditorCmd cmd;

    JSpinner widthSpinner;
    JSpinner heightSpinner;
    JSpinner groundSpinner;
    JButton applyButton;

    MapSizeArea(GameLayer layer, MapEditorCmd cmd) {
        this.layer = layer;
        this.cmd = cmd;

        this.initSpinners();
        this.initButton();
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
        });
    }
}

class SaveLoadArea {
    private GameLayer roomLayer;
    private GameLayer backLayer;
    private String jsonPath;
    private MapEditorCmd cmd;

    JButton saveButton;
    JButton loadButton;

    public SaveLoadArea(GameLayer roomLayer, GameLayer backLayer, MapEditorCmd cmd) {
        this.roomLayer = roomLayer;
        this.backLayer = backLayer;
        this.cmd = cmd;

        this.initSave();
        this.initLoad();
    }

    private void initSave() {
        this.saveButton = new JButton("Save");
        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser(cmd.basePath);
                int i = fc.showSaveDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    onSave(f);
                }
            }
        });
    }

    private void onSave(File file) {
        MapJson json = new MapJson();
        json.width = (int)cmd.sizeArea.widthSpinner.getValue();
        json.height = (int)cmd.sizeArea.heightSpinner.getValue();
        json.groundHeight = (int)cmd.sizeArea.groundSpinner.getValue();

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
                json.rooms.add(item.getItem2());
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
                fc.setFileFilter(new FileNameExtensionFilter("json", "json"));
                int i = fc.showOpenDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    Utility.readJson(MapJson.class, fc.getSelectedFile());
                }
            }
        });
    }

    private void onLoad() {
    }

    public void setEnable(boolean enable) {
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
                    onAddProp(path);
                }
            }
        });
    }

    private void onAddProp(String path) {
        this.layer.post(new AddTextureEvent(this.layer, path, new AddTextureEvent.Callback() {
            @Override
            public void callback(AddTextureEvent event, GameObject created) {
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
                fc.setFileFilter(new FileNameExtensionFilter("json", "json"));

                int i = fc.showOpenDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    RoomJson json = Utility.readJson(RoomJson.class, file);
                    addRoom(json);
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

    private void addRoom(RoomJson json) {
        AddRoomEvent e = new AddRoomEvent(roomLayer, json, new AddRoomEvent.Callback() {
            @Override
            public void callback(GameObject created) {
                RoomArea.this.onRoomAdd(created, json);
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

    private void onRoomAdd(GameObject created, RoomJson json) {
        synchronized (this) {
            this.rooms.add(new Tuple2<>(created, json));
            this.roomData.addRow(new String[] {
                    created.name,
                    created.transform.getLocalPos().toString(),
                    created.transform.getWorldPos().toString()
            });
        }
    }
}

