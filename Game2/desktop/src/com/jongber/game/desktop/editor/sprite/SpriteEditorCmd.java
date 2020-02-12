package com.jongber.game.desktop.editor.sprite;

import com.jongber.game.desktop.Utility;
import com.jongber.game.desktop.editor.sprite.event.LoadAsepriteEvent;
import com.jongber.game.desktop.editor.common.ViewControlArea;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class SpriteEditorCmd extends JFrame {

    public static void pop(SpriteEditViewer view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SpriteEditorCmd cmd = new SpriteEditorCmd(view);
                cmd.init();
                cmd.setSize(300, 350);
                cmd.setVisible(true);
            }
        }).start();
    }

    String basePath;
    SpriteEditViewer view;
    ViewControlArea viewControlArea;
    AsepriteArea asepriteArea;
    SpriteSheetArea sheetArea;

    SpriteEditorCmd(SpriteEditViewer view) {
        this.view = view;
        this.viewControlArea = new ViewControlArea(this, view, view.getLayer());
        this.basePath = System.getProperty("user.dir") +
                File.separator + "android" + File.separator + "assets";
    }

    void init() {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JPanel panel = this.viewControlArea.createPanel();
        add(panel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panel = this.createAsepriteArea();
        add(panel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel = this.createSpriteSheetArea();
        add(panel, gbc);
    }

    JPanel createAsepriteArea() {
        this.asepriteArea = new AsepriteArea(this);
        return this.asepriteArea.createPanel();
    }

    JPanel createSpriteSheetArea() {
        this.sheetArea = new SpriteSheetArea(this);
        return this.sheetArea.panel;
    }

    void onAsepriteLoaded(String imgPath, AsepriteJson json) {

        this.sheetArea.onLoadAsepriteJson(json, imgPath);
        this.view.post(new LoadAsepriteEvent(this.view, imgPath, json));
    }
}

class AsepriteArea {
    private SpriteEditorCmd cmd;
    JButton btLoad;

    AsepriteArea(SpriteEditorCmd cmd) {
        this.cmd = cmd;
        initLoadButton();
    }

    JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Aseprite Area"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Aseprite:  "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(this.btLoad, gbc);

        return panel;
    }

    void initLoadButton() {
        this.btLoad = new JButton("Load");
        this.btLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser(cmd.basePath);
                fc.setFileFilter(new FileNameExtensionFilter("json", "json"));

                int i = fc.showOpenDialog(null);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File selcted = fc.getSelectedFile();
                    try {
                        AsepriteJson json = Utility.readJson(AsepriteJson.class, selcted);
                        String imgPath = selcted.getParent() + File.separator + json.meta.image;

                        cmd.onAsepriteLoaded(imgPath, json);
                    }
                    catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Invalid AsepriteJson");
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

class SpriteSheetArea {
    JButton addSheet;
    JButton delSheet;

    DefaultTableModel model;
    JTable table;
    JScrollPane pane;

    SpriteEditorCmd cmd;

    JPanel panel = new JPanel();

    SpriteSheetArea(SpriteEditorCmd cmd) {
        this.cmd = cmd;
        initData();
        initTable();
        initAddButton();
        initDelButton();
        initPanel();
        setEnable(false);
    }

    private void initData() {
        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("Name");
    }

    private void initTable() {
        table = new JTable(model);
        pane = new JScrollPane(table);
    }

    private void initAddButton() {
        addSheet = new JButton("Add");
        addSheet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = JOptionPane.showInputDialog("Enter Animation Name");
                addRow(name);
            }
        });
    }

    private void initDelButton() {
        this.delSheet = new JButton("Del ");
        this.delSheet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int[] selected = table.getSelectedRows();
                for (int i = selected.length - 1; i >= 0; --i) {
                    model.removeRow(selected[i]);
                }
            }
        });
    }

    private void initPanel() {
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Animation list"));

        panel.add(pane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(this.addSheet);
        buttonPanel.add(this.delSheet);

        panel.add(buttonPanel, BorderLayout.EAST);
    }

    void setEnable(boolean enable) {
        this.panel.setEnabled(enable);
        this.pane.setEnabled(enable);
        this.addSheet.setEnabled(enable);
        this.delSheet.setEnabled(enable);
    }

    void onLoadAsepriteJson(AsepriteJson json, String path) {
        this.resetTable();
        this.setEnable(true);

        this.adjustColumn(json);
        this.addRows(json);

        this.cmd.pack();
    }

    private void resetTable() {
        panel.removeAll();
        initData();
        initTable();
        initAddButton();
        initDelButton();
        initPanel();
    }

    private void adjustColumn(AsepriteJson json) {
        for (int i = 0; i < json.frames.size(); ++i) {
            model.addColumn(i);
        }
    }

    private void addRows(AsepriteJson json) {
        for (int row = 0; row < json.meta.frameTags.size(); ++row) {
            String [] values = new String[model.getColumnCount()];

            AsepriteJson.FrameTag tag = json.meta.frameTags.get(row);
            values[0] = tag.name;

            for (int col = tag.from + 1; col <= tag.to + 1; ++col) {
                AsepriteJson.Frame frame = json.frames.get(col - 1);
                values[col] = "*";
            }

            model.addRow(values);
        }
    }

    private void addRow(String name) {
        this.model.addRow(new String[] {name});
    }
}

class SaveLoadArea {

}











