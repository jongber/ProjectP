package com.jongber.game.desktop.editor.sprite;

import com.jongber.game.desktop.Utility;
import com.jongber.game.desktop.editor.sprite.event.LoadAsepriteEvent;
import com.jongber.game.desktop.editor.common.ViewControlArea;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
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

public class SpriteEditorCmd extends JFrame {

    public static void pop(SpriteEditViewer view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SpriteEditorCmd cmd = new SpriteEditorCmd(view);
                cmd.init();
                cmd.setSize(300, 200);
                cmd.setVisible(true);
            }
        }).start();
    }

    String basePath;
    SpriteEditViewer view;
    ViewControlArea viewControlArea;
    AsepriteArea asepriteArea;
    JPanel animRoot = new JPanel();
    AnimationsArea animationsArea;

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
        gbc.weighty = 0.2;
        panel = this.createAsepriteArea();
        add(panel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        //gbc.weighty = 1;
        panel = this.initAnimPanel();
        add(panel, gbc);
    }

    JPanel initAnimPanel() {
        animRoot = new JPanel();
        animRoot.setBorder(BorderFactory.createTitledBorder("Animation Area"));
        animRoot.setEnabled(false);

        return animRoot;
    }

    JPanel createAsepriteArea() {
        this.asepriteArea = new AsepriteArea(this);
        return this.asepriteArea.createPanel();
    }

    void onAsepriteLoaded(String path, BufferedImage img, AsepriteJson json) {
        if (this.animRoot.isEnabled()) {
            this.animRoot.removeAll();
        }
        else {
            this.animRoot.setEnabled(true);
        }

        this.animationsArea = new AnimationsArea(img, json);
        this.animationsArea.apply(this.animRoot);
        this.pack();

        this.view.post(new LoadAsepriteEvent(this.view, path, json));
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
                        AsepriteJson json = Utility.readJson(AsepriteJson.class, selcted);
                        String imgPath = selcted.getParent() + File.separator + json.meta.image;
                        BufferedImage img = ImageIO.read(new File(imgPath));

                        cmd.onAsepriteLoaded(imgPath, img ,json);
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

    AsepriteJson json;
    BufferedImage img;

    AnimationsArea(BufferedImage img, AsepriteJson json) {
        this.json = json;
        this.img = img;
        initDataModel();
        initTable();
        initItemClick();
    }

    public JPanel apply(JPanel panel) {

        panel.add(scroll);

        return panel;
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

            AsepriteJson.FrameTag tag = json.meta.frameTags.get(row);
            values[0] = tag.name;

            for (int col = tag.from + 1; col <= tag.to + 1; ++col) {
                AsepriteJson.Frame frame = json.frames.get(col - 1);
                values[col] = "*";
            }

            model.addRow(values);
        }

        this.table = new JTable(model);

        this.scroll = new JScrollPane(this.table);
    }

    private Image subImage(int x, int y, int w, int h) {
        return this.img.getSubimage(x, y, w, h);
    }

    private void initItemClick() {
        this.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
//                int row = jTable1.rowAtPoint(evt.getPoint());
//                int col = jTable1.columnAtPoint(evt.getPoint());
//                if (row >= 0 && col >= 0) {
//            ......
//
//                }
            }
        });
    }
}

class SaveLoadArea {

}











