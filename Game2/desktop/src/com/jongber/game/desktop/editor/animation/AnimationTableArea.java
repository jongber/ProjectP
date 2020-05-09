package com.jongber.game.desktop.editor.animation;

import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.graphics.VFAnimation;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.common.CallbackEvent;
import com.jongber.game.desktop.editor.EditorAssetManager;
import com.jongber.game.desktop.editor.EditorCmd;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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

    List<Tuple2<String/*anime name*/, AnimationAsset>> assets = new ArrayList<>();

    public AnimationTableArea(AnimationView view) {
        this.view = view;

        this.tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.tableModel.addColumn("path");
        this.tableModel.addColumn("name");
        this.tableModel.addColumn("pivot");

        this.table = new JTable(tableModel);
        this.table.getSelectionModel().addListSelectionListener(this.rowSelectionListener);

        this.importAseprite = new JButton("import");
        this.importAseprite.addActionListener(importListener);

        this.deleteRow = new JButton("delete");
        this.deleteRow.addActionListener(this.deleteListener);
        this.deleteRow.setEnabled(false);
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
            synchronized (this) {
                int[] selected = AnimationTableArea.this.table.getSelectedRows();
                for (int i = selected.length - 1; i >= 0; --i) {
                    AnimationTableArea.this.assets.remove(selected[i]);
                    AnimationTableArea.this.tableModel.removeRow(selected[i]);
                }
            }
        }
    };

    ListSelectionListener rowSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            synchronized (this) {
                int row = table.getSelectedRow();
                if (row >= 0 && row < assets.size()) {
                    Tuple2<String, AnimationAsset> asset = assets.get(row);
                    view.post(new AnimationSelectEvent(view, asset.getItem2(), VFAnimation.PlayMode.LOOP));
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
                List<AnimationAsset> assets = EditorAssetManager.loadAseprite(asepriteFile);
                for (AnimationAsset asset : assets) {
                    addItem(asset);
                }
            }
        }));
    }

    private void addItem(AnimationAsset asset) {
        synchronized (this) {
            assets.add(new Tuple2<>(asset.getName(), asset));

            String[] split = asset.getName().split(" ");
            tableModel.addRow(new String[] {split[0], split[1], asset.getPivot().toString()});
        }
    }
}
