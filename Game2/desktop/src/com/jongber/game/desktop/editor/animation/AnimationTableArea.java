package com.jongber.game.desktop.editor.animation;

import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.graphics.VFAnimation;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.Utility;
import com.jongber.game.desktop.common.CallbackEvent;
import com.jongber.game.desktop.common.json.AnimationJson;
import com.jongber.game.desktop.common.json.JsonList;
import com.jongber.game.desktop.editor.EditorAssetManager;
import com.jongber.game.desktop.editor.EditorCmd;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class AnimationTableArea implements EditorCmd.AreaImpl {

    AnimationView view;

    JPanel panel;

    JTable table;
    DefaultTableModel tableModel;

    JButton importAseprite;
    JButton deleteRow;
    Timer timer;

    List<Tuple2<AnimationAsset, String/*img path*/>> assets = new ArrayList<>();
    HashSet<String> flags = new HashSet<>();

    public AnimationTableArea(AnimationView view) {
        this.view = view;

        this.tableModel = new DefaultTableModel();

        this.tableModel.addColumn("path");
        this.tableModel.addColumn("name");
        this.tableModel.addColumn("pivot.x");
        this.tableModel.addColumn("pivot.y");

        this.table = new JTable(tableModel);
        this.table.getSelectionModel().addListSelectionListener(this.rowSelectionListener);
        this.table.addPropertyChangeListener(tableListener);

        this.importAseprite = new JButton("import");
        this.importAseprite.addActionListener(importListener);

        this.deleteRow = new JButton("delete");
        this.deleteRow.addActionListener(this.deleteListener);
        this.deleteRow.setEnabled(false);

        this.timer = new Timer(60, this.timerListener);
        this.timer.start();
    }

    ActionListener importListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            File selected = loadAseprite();
            if (selected == null) {
                return;
            }

            importAseprite(selected);
        }
    };

    ActionListener deleteListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            synchronized (AnimationTableArea.this) {
                int[] selected = AnimationTableArea.this.table.getSelectedRows();
                for (int i = selected.length - 1; i >= 0; --i) {
                    Tuple2<AnimationAsset, String> removed = AnimationTableArea.this.assets.remove(selected[i]);
                    AnimationTableArea.this.tableModel.removeRow(selected[i]);

                    flags.remove(removed.getItem1().getName());
                }
            }
        }
    };

    ListSelectionListener rowSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            synchronized (AnimationTableArea.this) {
                if (listSelectionEvent.getValueIsAdjusting()) return;

                int row = table.getSelectedRow();
                if (row >= 0 && row < assets.size()) {
                    AnimationAsset asset = assets.get(row).getItem1();
                    view.post(new AnimationSelectEvent(view, asset, VFAnimation.PlayMode.LOOP));
                    System.out.println("selected " + row);
                }

                int[] selected = table.getSelectedRows();
                if (selected.length > 0) {
                    deleteRow.setEnabled(true);
                }
                else {
                    deleteRow.setEnabled(false);
                }
            }
        }
    };

    PropertyChangeListener tableListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent e) {
            if (e.getPropertyName() == "tableCellEditor") {
                cellTouched();
            }
        }
    };

    ActionListener timerListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            synchronized (AnimationTableArea.this) {
                int row = table.getSelectedRow();
                if (row >= 0 && row < assets.size()) {
                    updateItem(row, assets.get(row).getItem1());
                }
            }
        }
    };

    @Override
    public JPanel createPanel() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Animation Area"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,5);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(this.importAseprite, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(this.deleteRow, gbc);

        JScrollPane pane = new JScrollPane(this.table);
        gbc.gridx = 0; gbc.gridy = 1;
        //gbc.weightx = 1;
        panel.add(pane, gbc);

        return panel;
    }

    public void onSave(File file) {
        synchronized (this) {
            JsonList<AnimationJson> list = EditorAssetManager.toJson(this.assets);
            Utility.writeJson(list, file);
        }
    }

    public void onLoad(File file) {
        synchronized (this) {
            for (int i = assets.size() - 1; i >= 0; --i) {
                tableModel.removeRow(i);
            }

            assets.clear();
            flags.clear();
        }

        this.view.post(new CallbackEvent(new CallbackEvent.Callback() {
            @Override
            public void invoke() {
                try {
                    JsonList<AnimationJson> jsons = new JsonList<>();
                    jsons = Utility.readJson(jsons.getClass(), file);
                    List<Tuple2<AnimationAsset, String>> assets = EditorAssetManager.loadAnimations(jsons);

                    for (Tuple2<AnimationAsset, String> asset : assets) {
                        addItem(asset.getItem1(), asset.getItem2());
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Invalid Animation Json file!!");
                }
            }
        }));
    }

    private File loadAseprite() {
        JFileChooser fc = new JFileChooser(EditorCmd.BasePath);
        fc.setFileFilter(new FileNameExtensionFilter("json", "json"));
        if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        File selected = fc.getSelectedFile();
        if (!selected.getPath().contains(EditorCmd.BasePath)) {
            JOptionPane.showMessageDialog(null, "Invalid path, use only under android/asset");
            return null;
        }

        return selected;
    }

    private void importAseprite(File asepriteFile) {
        this.view.post(new CallbackEvent(new CallbackEvent.Callback() {
            @Override
            public void invoke() {
                try {
                    List<Tuple2<AnimationAsset, String>> assets = EditorAssetManager.loadAseprite(asepriteFile);
                    for (Tuple2<AnimationAsset, String> asset : assets) {
                        if (addItem(asset.getItem1(), asset.getItem2()) == false) {
                            JOptionPane.showMessageDialog(null, "Duplicated animation found");
                            return;
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Invalid Aseprite Json file!!");
                }
            }
        }));
    }

    private boolean addItem(AnimationAsset asset, String imgPath) {
        synchronized (this) {
            if (this.flags.contains(asset.getName())) {
                return false;
            }
            else {
                assets.add(new Tuple2<>(asset, imgPath));

                String[] split = asset.getName().split(" ");
                tableModel.addRow(new String[] {
                        split[0],
                        split[1],
                        Float.toString(asset.getPivot().x),
                        Float.toString(asset.getPivot().y)});

                this.flags.add(asset.getName());

                return true;
            }
        }
    }

    private void updateItem(int row, AnimationAsset asset) {
        synchronized (this) {
            String[] split = asset.getName().split(" ");
            table.setValueAt(split[0], row, 0);
            table.setValueAt(split[1], row, 1);
            table.setValueAt(Float.toString(asset.getPivot().x), row, 2);
            table.setValueAt(Float.toString(asset.getPivot().y), row, 3);
        }
    }

    private void cellTouched() {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();

        if (row < 0 || row >= assets.size()) {
            return;
        }

        AnimationAsset asset = assets.get(row).getItem1();

        //// only pivot change
        if (table.getColumnName(col).startsWith("pivot") == false) {
            String value =(String)table.getValueAt(row, col);
            String[] split = asset.getName().split(" ");
            if (split[0].equals(value) || split[1].equals(value) == false) {
                JOptionPane.showMessageDialog(null, "pivot only editable");
            }

            return;
        }

        try {
            float x = Float.parseFloat((String)table.getValueAt(row, 2));
            float y = Float.parseFloat((String)table.getValueAt(row, 3));
            asset.setPivot(x, y);
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "invalid float format!");
            e.printStackTrace();
        }
    }
}
