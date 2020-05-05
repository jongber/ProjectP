package com.jongber.game.desktop.old.editor.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.graphics.VFAnimation;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.Utility;
import com.jongber.game.desktop.common.json.AsepriteJson;
import com.jongber.game.desktop.common.component.SpriteComponent;
import com.jongber.game.desktop.old.editor.sprite.event.AddSpriteEvent;
import com.jongber.game.desktop.old.editor.sprite.event.LoadSpriteJsonEvent;
import com.jongber.game.desktop.common.CallbackEvent;
import com.jongber.game.desktop.old.editor.sprite.event.ChangeSpriteEvent;
import com.jongber.game.desktop.old.editor.sprite.event.LoadAsepriteEvent;
import com.jongber.game.desktop.old.editor.common.ViewControlArea;
import com.jongber.game.desktop.old.common.event.ClearAllEvent;
import com.jongber.game.desktop.common.json.SpriteJson;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    SaveLoadArea saveLoadArea;

    File baseFile;

    SpriteEditorCmd(SpriteEditViewer view) {
        this.view = view;
        this.viewControlArea = new ViewControlArea(this, view, view.getLayer());
        this.basePath = System.getProperty("user.dir") +
                File.separator + "android" + File.separator + "assets";
        this.baseFile = new File(this.basePath);
    }

    void init() {
        setTitle("Sprite Editor");
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

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel = this.createSaveLoadArea();
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

    JPanel createSaveLoadArea() {
        this.saveLoadArea = new SaveLoadArea(this, this.view.getLayer());
        return this.saveLoadArea.createPanel();
    }

    void onAsepriteLoaded(String imgPath, AsepriteJson json) {
        this.sheetArea.onLoadAsepriteJson(json);
        this.view.post(new ClearAllEvent(this.view.getLayer()));
        this.view.post(new LoadAsepriteEvent(this.view, imgPath, json, this.sheetArea));
        this.saveLoadArea.onAsepriteLoaded(imgPath);
    }

    void onSpriteJsonLoaded(SpriteJson json) {

        //// gdx의 asset folder에 파일이 생성된거 인지를 못함..
        json.image = basePath + File.separator + json.image;

        this.sheetArea.onLoadSpriteJson(json);
        this.view.post(new ClearAllEvent(this.view.getLayer()));
        this.view.post(new LoadSpriteJsonEvent(this.view, json, this.sheetArea));
        this.saveLoadArea.onSpriteJsonLoaded(json);
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

class SpriteSheetArea implements LoadAsepriteEvent.Callback, LoadSpriteJsonEvent.Callback {

    JButton addSheet;
    JButton delSheet;

    DefaultTableModel model;
    JTable table;
    JScrollPane pane;

    SpriteEditorCmd cmd;

    GameObject created;
    GameLayer layer;

    JPanel panel = new JPanel();
    Timer timer;

    JPanel pivopPanel = new JPanel();
    JTextField pivotX = new JTextField(4);
    JTextField pivotY = new JTextField(4);

    SpriteSheetArea(SpriteEditorCmd cmd) {
        this.cmd = cmd;
        this.layer = cmd.view.getLayer();
        initData();
        initTable();
        initAddButton();
        initDelButton();
        initPanel();
        initTimer();
        initPivotPanel();
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
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pane = new JScrollPane(table);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    SpriteSheetArea.this.onRowSelected(row);
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        SpriteSheetArea.this.onRowSelected(row);
                    }
                }
                else if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    int col = table.getSelectedColumn();
                    SpriteSheetArea.this.onDoubleClicked(row, col);
                }
            }
        });
    }

    private void initAddButton() {
        addSheet = new JButton("Add");
        addSheet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = JOptionPane.showInputDialog("Enter Animation Name");
                addNewSprite(name);
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

    private void initTimer() {
        if (this.timer == null) {
            this.timer = new Timer(60, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    synchronized (this) {
                        if (created == null) return;

                        SpriteComponent c = created.getComponent(SpriteComponent.class);

                        int lastCol = model.getColumnCount() - 1;
                        int rowCnt = model.getRowCount();
                        for (int i = 0; i < rowCnt; ++i) {
                            String name = (String)model.getValueAt(i, 0);
                            SpriteComponent.AnimData data = c.assetMap.get(name);
                            if (data != null)
                                model.setValueAt(data.pivot.toString(), i, lastCol);
                        }
                    }
                }
            });
        }
    }

    private void initPivotPanel() {
        pivopPanel.add(new JLabel("pivotX :"));
        pivopPanel.add(this.pivotX);
        pivopPanel.add(new JLabel("pivotY :"));
        pivopPanel.add(this.pivotY);
    }

    void setEnable(boolean enable) {
        this.panel.setEnabled(enable);
        this.pane.setEnabled(enable);
        this.addSheet.setEnabled(enable);
        this.delSheet.setEnabled(enable);
        if (this.timer != null) {
            if (enable) this.timer.start();
            else this.timer.stop();
        }
    }

    void onLoadAsepriteJson(AsepriteJson json) {
        this.resetTable();
        this.setEnable(true);

        this.adjustColumn(json);
        this.addRows(json);

        this.cmd.pack();
    }

    void onLoadSpriteJson(SpriteJson json) {
        this.resetTable();
        this.setEnable(true);

        this.adjustColumn(json);
        this.addRows(json);

        this.cmd.pack();
    }

    void onRowSelected(int row) {
        String name = (String)model.getValueAt(row, 0);
        layer.post(new ChangeSpriteEvent(created, name));
    }

    void onDoubleClicked(int row, int col) {

        String name = (String)model.getValueAt(row, 0);

        if (col == table.getColumnCount() - 1) {    // the last column..
            String pivotStr = (String)model.getValueAt(row, model.getColumnCount() - 1);
            this.adjustPivot(name, pivotStr);
        }
        else if (col != 0) {
            this.adjustFrame(name, row, col);
        }
    }

    private void adjustFrame(String name, int row, int col) {
        this.layer.post(new CallbackEvent(new CallbackEvent.Callback() {
            @Override
            public void invoke() {
                synchronized (this) {
                    List<Integer> frames = new ArrayList<>();

                    for (int i = 1; i < model.getColumnCount() - 1; ++ i) {
                        String value = (String)model.getValueAt(row, i);
                        if (col == i) {
                            if (value == null || value.equals("")) {
                                value = "*";
                            }
                            else if (value.equals("*")) {
                                value = "";
                            }

                            model.setValueAt(value, row, i);
                        }

                        if (value != null && value.equals("*")) {
                            frames.add(i - 1);
                        }
                    }

                    SpriteComponent c = created.getComponent(SpriteComponent.class);

                    List<Integer> durations = new ArrayList<>();
                    List<TextureRegion> regions = new ArrayList<>();

                    for (int i = 0; i < frames.size(); ++i) {
                        regions.add(c.totalImages.get(frames.get(i)));
                        durations.add(c.totalDurations.get(frames.get(i)));
                    }

                    AnimationAsset asset = new AnimationAsset(name, regions, durations);
                    SpriteComponent.AnimData old = c.assetMap.remove(name);
                    c.assetMap.put(name, new SpriteComponent.AnimData(asset, old.pivot));
                    c.currentAnimation = new VFAnimation(asset, VFAnimation.PlayMode.LOOP);
                }
            }
        }));
    }

    private void adjustPivot(String name, String pivotStr) {

        int result = JOptionPane.showConfirmDialog(null,
                this.pivopPanel,
                "Enter Pivot " + pivotStr,
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                float x = Float.parseFloat(pivotX.getText());
                float y = Float.parseFloat(pivotY.getText());

                this.layer.post(new CallbackEvent(new CallbackEvent.Callback() {
                    @Override
                    public void invoke() {
                        synchronized (SpriteSheetArea.this) {
                            SpriteComponent c = created.getComponent(SpriteComponent.class);
                            SpriteComponent.AnimData data = c.assetMap.get(name);
                            data.pivot = new Vector2(x, y);
                        }
                    }
                }));
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid Pivot value, must float plz");
            }
        }
    }

    private void resetTable() {
        synchronized (this) {
            panel.removeAll();
            this.timer.stop();
            initData();
            initTable();
            initAddButton();
            initDelButton();
            initPanel();
        }
    }

    private void adjustColumn(AsepriteJson json) {
        for (int i = 0; i < json.frames.size(); ++i) {
            model.addColumn(i);
        }

        model.addColumn("Pivot");
    }

    private void adjustColumn(SpriteJson json) {
        for (int i = 0; i < json.frames.size(); ++i) {
            model.addColumn(i);
        }

        model.addColumn("Pivot");
    }

    private void addRows(AsepriteJson json) {
        for (int row = 0; row < json.meta.frameTags.size(); ++row) {
            String [] values = new String[model.getColumnCount()];

            AsepriteJson.FrameTag tag = json.meta.frameTags.get(row);
            values[0] = tag.name;

            for (int col = tag.from + 1; col <= tag.to + 1; ++col) {
                values[col] = "*";
            }

            model.addRow(values);
        }
    }

    private void addRows(SpriteJson json) {
        String[] values = new String[model.getColumnCount()];

        values[0] = json.name;

        for (int i = 1; i < model.getColumnCount() - 1; ++i) {
            values[i] = "*";
        }

        values[model.getColumnCount() - 1] = json.pivot.toString();

        model.addRow(values);
    }

    private void addNewSprite(String name) {
        synchronized (this.timer) {

            if (this.created != null) {
                SpriteComponent c = created.getComponent(SpriteComponent.class);

                if (name == null || name.length() == 0 || c.assetMap.containsKey(name)) {
                    JOptionPane.showMessageDialog(null, "Invalid Animation Name!!");
                    return;
                }
            }

            this.model.addRow(new String[] {name});
            this.layer.post(new AddSpriteEvent(this.created, name));
        }
    }

    @Override
    public void callback(GameObject created) {
        synchronized (this) {
            this.created = created;
        }
    }
}

class SaveLoadArea {
    JButton btSave;
    JButton btLoad;

    SpriteEditorCmd cmd;
    SpriteSheetArea sheet;
    GameLayer layer;

    String imgPath;

    SaveLoadArea(SpriteEditorCmd cmd, GameLayer layer) {
        this.cmd = cmd;
        this.sheet = cmd.sheetArea;
        this.layer = layer;

        initSave();
        initLoad();
    }

    private void initSave() {
        btSave = new JButton("Save");
        btSave.setEnabled(false);

        btSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser(cmd.basePath);
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setAcceptAllFileFilterUsed(false);
                int result = fc.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File saveDir = fc.getSelectedFile();
                    if (saveDir.getPath().contains(cmd.basePath) == false) {
                        JOptionPane.showMessageDialog(null, "Invalid path, use only under android/asset");
                        return;
                    }

                    processSave(saveDir);
                }
            }
        });
    }

    private void initLoad() {
        btLoad = new JButton("Load");
        btLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser(cmd.basePath);
                fc.setDialogTitle("Select directory to save all Sprites");
                fc.setFileFilter(new FileNameExtensionFilter("Sprite file", "sprite"));

                int result = fc.showOpenDialog(null);
                if (result != JFileChooser.APPROVE_OPTION)
                    return;

                try
                {
                    File selected = fc.getSelectedFile();
                    SpriteJson json = Utility.readJson(SpriteJson.class, selected);

                    File imgFile = new File(cmd.basePath + File.separator + json.image);
                    if (imgFile.exists() == false || imgFile.canWrite() == false) {
                        JOptionPane.showMessageDialog(null, "Image file cannot access!!");
                        return;
                    }

                    processLoad(json);
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Invalid sprite json file!!");
                    e.printStackTrace();
                }
            }
        });
    }

    void processSave(File saveDir){
        this.layer.post(new CallbackEvent(new CallbackEvent.Callback() {
            @Override
            public void invoke() {
                synchronized (sheet) {
                    List<SpriteJson> jsons = new ArrayList<>();

                    SpriteComponent c = sheet.created.getComponent(SpriteComponent.class);
                    int rows = sheet.model.getRowCount();
                    int cols = sheet.model.getColumnCount();

                    for (int row = 0; row < rows; ++row) {
                        SpriteJson json = new SpriteJson();
                        json.name = (String)sheet.model.getValueAt(row, 0);

                        for (int col = 1; col < cols - 1; ++col) {
                            if ("*".equals(sheet.model.getValueAt(row, col))) {
                                TextureRegion region = c.totalImages.get(col - 1);
                                int duration = c.totalDurations.get(col - 1);

                                Rectangle rect = new Rectangle(region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight());
                                json.frames.add(new Tuple2<>(rect, duration));
                            }
                        }

                        json.pivot = c.assetMap.get(json.name).pivot;

                        File imgFile = new File(imgPath);
                        String assetPath = cmd.baseFile.toURI().relativize(saveDir.toURI()).getPath();
                        json.image = assetPath + imgFile.getName();

                        jsons.add(json);
                    }

                    SaveLoadArea.this.validateAndWrite(saveDir, jsons);
                }
            }
        }));
    }

    void processLoad(SpriteJson json) {
        cmd.onSpriteJsonLoaded(json);
    }

    JPanel createPanel() {
        JPanel panel = new JPanel();

        panel.add(this.btSave);
        panel.add(this.btLoad);

        return panel;
    }

    void onAsepriteLoaded(String imgPath) {
        this.btSave.setEnabled(true);
        this.imgPath = imgPath;
    }

    void onSpriteJsonLoaded(SpriteJson json) {
        this.btSave.setEnabled(true);
        this.imgPath = cmd.basePath + File.separator + json.image;
    }

    private void validateAndWrite(File saveDir, List<SpriteJson> jsons) {

        boolean overwrite = false;
        // validate
        for (SpriteJson json : jsons) {
            File file = new File(saveDir.getPath() + File.separator + json.name + ".sprite");
            if (file.exists()) {
                overwrite = true;
            }

            File imgFile = new File(cmd.basePath + File.separator + json.image);
            if (imgFile.exists()) {
                overwrite = true;
            }
        }

        if (overwrite) {
            int r = JOptionPane.showConfirmDialog(null, "Exist file, overwrite?", "Caution", JOptionPane.YES_NO_OPTION);
            if (r != 0) {
                return;
            }
        }

        // save
        try {
            for (SpriteJson json : jsons) {
                File file = new File(saveDir.getPath() + File.separator + json.name + ".sprite");
                Utility.writeJson(json, file);

                File copiedFile = new File(this.cmd.basePath + File.separator + json.image);
                if (!copiedFile.exists()) {
                    File org = new File(this.imgPath);
                    Utility.copy(org, copiedFile);
//                    Path org = Paths.get(this.imgPath);
//                    Path copied = Paths.get(copiedFile.getPath());
//                    Files.copy(org, copied, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}


























