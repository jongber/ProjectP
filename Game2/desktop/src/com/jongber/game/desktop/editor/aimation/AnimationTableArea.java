package com.jongber.game.desktop.editor.aimation;

import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.core.util.Tuple3;
import com.jongber.game.core.util.Tuple4;
import com.jongber.game.desktop.Utility;
import com.jongber.game.desktop.common.CallbackEvent;
import com.jongber.game.desktop.common.json.AsepriteJson;
import com.jongber.game.desktop.editor.EditorAssetManager;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.EditorView;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class AnimationTableArea implements EditorCmd.AreaImpl {

    EditorView view;

    JPanel panel;

    JTable animTable;
    DefaultTableModel animData;

    JButton importAseprite;

    List<Tuple2<String/*anime name*/, AnimationAsset>> items = new ArrayList<>();

    public AnimationTableArea(EditorView view) {
        this.view = view;

        this.animData = new DefaultTableModel();
        this.animData.addColumn("name");
        this.animData.addColumn("pivot");

        this.animTable = new JTable(animData);

        this.importAseprite = new JButton("import");
        this.importAseprite.addActionListener(importListener);
    }

    ActionListener importListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fc = new JFileChooser(EditorCmd.BasePath);
            fc.setFileFilter(new FileNameExtensionFilter("json", "json"));
            if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File selected = fc.getSelectedFile();
            if (!selected.getPath().contains(EditorCmd.BasePath)) {
                JOptionPane.showMessageDialog(null, "Invalid path, use only under android/asset");
                return;
            }

            onImportAseprite(selected);
        }
    };

    @Override
    public JPanel createPanel() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Animation Area"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5,5,5,5);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(this.importAseprite);

        JScrollPane pane = new JScrollPane(this.animTable);
        gbc.gridx = 0; gbc.gridy = 1;
        //gbc.weightx = 1;
        panel.add(pane, gbc);

        return panel;
    }

    private void onImportAseprite(File asepriteFile) {
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
            items.add(new Tuple2<>(asset.getName(), asset));

            animData.addRow(new String[] {asset.getName(), asset.getPivot().toString()});
        }
    }
}
