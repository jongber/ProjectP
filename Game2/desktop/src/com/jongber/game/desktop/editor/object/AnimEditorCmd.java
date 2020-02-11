package com.jongber.game.desktop.editor.object;

import com.jongber.game.desktop.Utility;
import com.jongber.game.desktop.editor.common.ViewControlArea;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
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

public class AnimEditorCmd extends JFrame {

    public static void pop(AnimEditViewer view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AnimEditorCmd cmd = new AnimEditorCmd(view);
                cmd.init();
                cmd.setSize(300, 200);
                cmd.setVisible(true);
            }
        }).start();
    }

    String basePath;
    AnimEditViewer view;
    ViewControlArea viewControlArea;
    AsepriteArea asepriteArea;
    AnimationsArea animationsArea;

    AnimEditorCmd(AnimEditViewer view) {
        this.view = view;
        this.viewControlArea = new ViewControlArea(this, view, null);
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
        gbc.gridy = 2;
        gbc.weighty = 1;
        panel = this.createAsepriteArea();
        add(panel, gbc);
    }

    JPanel createAsepriteArea() {
        this.asepriteArea = new AsepriteArea(this);
        return this.asepriteArea.createPanel();
    }

    void onAsepriteLoaded(AsepriteJson json) {
        if (this.animationsArea != null)
            this.remove(this.animationsArea.getPanel());

        this.animationsArea = new AnimationsArea(json);
        this.addAnimationPanel();
    }

    private void addAnimationPanel() {
        JPanel panel = this.animationsArea.getPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 1;
        add(panel, gbc);

        this.pack();
    }
}

class AsepriteArea {
    private AnimEditorCmd cmd;
    JButton btLoad;

    AsepriteJson loadedJson;

    AsepriteArea(AnimEditorCmd cmd) {
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
        panel.add(new JLabel("Aseprite:"), gbc);

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
                        loadedJson = Utility.readJson(AsepriteJson.class, selcted);
                        cmd.onAsepriteLoaded(loadedJson);
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

class AnimationsArea {
    DefaultTableModel model;
    JTable table;
    JScrollPane scroll;

    JPanel panel;

    AsepriteJson json;

    AnimationsArea(AsepriteJson json) {
        this.json = json;
        initDataModel();
        initTable();
        initPanel();
    }

    JPanel getPanel() {
        return this.panel;
    }

    private void initDataModel() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("Tag");

        for (int i = 0; i < json.frames.size(); ++i) {
            model.addColumn(i);
        }
    }

    private void initTable() {

        for (int row = 0; row < json.meta.frameTags.size(); ++row) {
            String [] values = new String[model.getColumnCount()];
            FrameTag tag = json.meta.frameTags.get(row);
            values[0] = tag.name;

            for (int col = tag.from + 1; col <= tag.to + 1; ++col) {
                values[col] = "*";
            }

            model.addRow(values);
        }

        this.table = new JTable(model);
        this.scroll = new JScrollPane(this.table);
    }

    private void initPanel() {
        this.panel = new JPanel();

        panel.add(this.scroll);
    }
}

class SaveLoadArea {

}















