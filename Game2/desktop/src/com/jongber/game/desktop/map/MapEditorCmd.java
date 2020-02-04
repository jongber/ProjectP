package com.jongber.game.desktop.map;

import com.badlogic.gdx.Gdx;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.desktop.Utility;
import com.jongber.game.desktop.map.event.AddRoomEvent;
import com.jongber.game.desktop.viewer.event.ShowGridEvent;
import com.jongber.game.projectz.json.RoomJson;

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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

class MapEditorCmd extends JFrame {

    GameLayer roomLayer;
    String basePath;

    RoomArea roomArea;

    private MapEditorCmd(GameLayer roomLayer) {
        super();
        this.roomLayer = roomLayer;
        this.basePath = System.getProperty("user.dir") +
                File.separator + "android" + File.separator + "assets" + File.separator + "projectz";
    }

    static void popMapEditorCmd(GameLayer layer) {
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
        setSize(400, 400);
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

        ///// room panel area
        JPanel roomPanel = this.createRoomPanel();
        ///// room panel area end
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(roomPanel, gbc);

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

    private JPanel createRoomPanel() {
        roomArea = new RoomArea(this.roomLayer, this);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Room area"));

        panel.add(roomArea.addButton);

        return panel;
    }
}

class RoomArea {
    GameLayer layer;
    MapEditorCmd cmd;
    String basePath;

    JButton addButton = new JButton("Add room");
    JButton delButton = new JButton("Del room ");
    JTable roomTable;
    DefaultTableModel roomData;

    List<GameObject> rooms = new ArrayList<>();

    public final List<GameObject> propObjects = new ArrayList<>();

    public RoomArea(GameLayer layer, MapEditorCmd cmd) {
        this.layer = layer;
        this.cmd = cmd;
        this.basePath = cmd.basePath + File.separator + "house" + File.separator + "room";
        initTable();
        initAdd();
    }

    private void initTable() {
        roomData = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        roomData.addColumn("Name");
        roomData.addColumn("Pos");

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
                }
            }
        });
    }

    private void initDel() {
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    private void addRoom(RoomJson json) {
        AddRoomEvent e = new AddRoomEvent(layer, json, new AddRoomEvent.Callback() {
            @Override
            public void callback(GameObject created) {
                RoomArea.this.rooms.add(created);
            }
        });

        this.layer.post(e);
    }
}

