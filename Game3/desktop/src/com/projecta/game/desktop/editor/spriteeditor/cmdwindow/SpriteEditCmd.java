package com.projecta.game.desktop.editor.spriteeditor.cmdwindow;

import com.badlogic.gdx.Gdx;
import com.projecta.game.desktop.editor.spriteeditor.panel.HUDSpriteEditPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SpriteEditCmd extends JFrame {
    private HUDSpriteEditPanel hud;

    private ButtonArea buttonArea;

    private boolean isActive = false;

    public SpriteEditCmd(HUDSpriteEditPanel hud) {
        super();
        this.hud = hud;
    }

    public static void create(HUDSpriteEditPanel hud) {
        GridBagConstraints c = new GridBagConstraints();

        SpriteEditCmd frame = new SpriteEditCmd(hud);
        frame.setLayout(new GridBagLayout());

        int row = 0;

        frame.buttonArea = new ButtonArea(frame);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = row++;
        frame.add(frame.buttonArea, c);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                Gdx.app.exit();
            }
        });

        frame.init();
        frame.pack();
        frame.setSize(frame.getWidth(), frame.getHeight() + 100);
        frame.setVisible(true);
    }

    public void init() {
        this.inactivate();
    }

    public void apply() {
    }

    public void save() {
    }

    public void load() {
    }

    public void clear() {
    }

    public void inactivate() {
        this.buttonArea.inactivate();
    }
}

class ButtonArea extends JPanel {
    private SpriteEditCmd cmd;

    public JButton btCreate;
    public JButton btSave;
    public JButton btLoad;
    public JButton btClear;

    public ButtonArea(SpriteEditCmd cmd) {
        this.cmd = cmd;
        this.setBorder(BorderFactory.createTitledBorder("Save Load Area"));
        this.setLayout(new GridBagLayout());
        this.init();
        this.setListener();
    }

    private void init() {
        this.btSave = new JButton("Save");
        this.btLoad = new JButton("Load");
        this.btCreate = new JButton("Create");
        this.btClear = new JButton("Clear");

        int gridx = 0;
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx++;
        c.insets = new Insets(0, 10, 0, 10);
        this.add(this.btCreate, c);

        c.gridx = gridx++;
        this.add(this.btLoad, c);

        c.gridx = gridx++;
        this.add(this.btSave, c);

        c.gridx = gridx++;
        this.add(this.btClear, c);
    }

    private void setListener() {
    }

    public void inactivate() {
        this.btSave.setEnabled(false);
        this.btCreate.setEnabled(true);
        this.btClear.setEnabled(false);
    }

    public void activate() {
        this.btSave.setEnabled(true);
        this.btClear.setEnabled(true);
    }
}

class ImageLoadArea extends JPanel {
    public JLabel lbPath;
    public JButton btLoad;

    public ImageLoadArea() {
        this.setBorder(BorderFactory.createTitledBorder("Sprite sheet path"));
        this.setLayout(new GridBagLayout());

        this.init();
    }

    private void init() {
        GridBagConstraints c = new GridBagConstraints();

        this.lbPath = new JLabel("set image path");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.insets = new Insets(0, 10,0,0);
        this.add(this.lbPath, c);

        this.btLoad = new JButton("Load Image");
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 10,0,10);
        this.add(this.btLoad, c);

        this.setListener();
    }

    private void setListener() {
    }

    public void inactivate() {
        this.btLoad.setEnabled(false);
    }

    public void activate() {
        this.btLoad.setEnabled(true);
    }
}

class PivotArea extends JPanel {
    private JLabel xText;
    public JSpinner xSpinner;

    private JLabel yText;
    public JSpinner ySpinner;

    public PivotArea() {
        this.setBorder(BorderFactory.createTitledBorder("Pivot setting area"));
        this.setLayout(new GridBagLayout());

        this.init();
        this.setListener();
    }

    public void inactivate() {
        this.xSpinner.setEnabled(false);
        this.ySpinner.setEnabled(false);
    }

    public void activate() {
        this.xSpinner.setEnabled(true);
        this.ySpinner.setEnabled(true);
    }

    private void init() {
        this.xText = new JLabel("PivotX:");
        this.xSpinner = new JSpinner(new SpinnerNumberModel());
        this.yText = new JLabel("PivotY:");
        this.ySpinner = new JSpinner(new SpinnerNumberModel());

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        // top, left, bottom, right
        c.insets = new Insets(0, 10, 0, 10);
        this.add(this.xText, c);
        c.gridx = 1;
        c.insets = new Insets(0, 10, 0, 10);
        c.ipadx = 12;
        this.add(this.xSpinner, c);

        c.gridx = 2;
        c.insets = new Insets(0, 10, 0, 10);
        c.ipadx = 0;
        this.add(this.yText, c);

        c.gridx = 3;
        c.insets = new Insets(0, 10, 0, 10);
        c.ipadx = 12;
        this.add(this.ySpinner, c);
    }

    private void setListener() {
    }
}

class TextureRegionArea extends JPanel {

    private JLabel unitText;
    public JSpinner unitSpinner;

    private JLabel fromText;
    public JSpinner fromIndex;
    private JLabel toText;
    public JSpinner toIndex;

    private JLabel baseFrameText;
    public JSpinner baseFrameSpinner;

    public TextureRegionArea() {
        this.setBorder(BorderFactory.createTitledBorder("Texture Region area"));
        this.setLayout(new GridBagLayout());
        this.init();
        this.setListener();
    }

    public void inactivate() {
        unitSpinner.setEnabled(false);
        fromIndex.setEnabled(false);
        toIndex.setEnabled(false);
        baseFrameSpinner.setEnabled(false);
    }

    public void activate() {
        unitSpinner.setEnabled(true);
        fromIndex.setEnabled(true);
        toIndex.setEnabled(true);
        baseFrameSpinner.setEnabled(true);
    }

    private void init() {
        this.unitText = new JLabel("pixel unit per frame");
        //SpinnerNumberModel(double value, double minimum, double maximum, double stepSize)
        this.unitSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 2048, 1));

        this.fromText = new JLabel("index from");
        this.fromIndex = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        this.toText = new JLabel("index to");
        this.toIndex = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));

        this.baseFrameText = new JLabel("base frame time(ms)");
        this.baseFrameSpinner = new JSpinner(new SpinnerNumberModel(10, 10, 1000, 1));

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        // top, left, bottom, right
        c.insets = new Insets(0, 10, 0, 10);
        this.add(this.unitText, c);

        c.gridx = 1;
        this.add(this.unitSpinner, c);

        c.gridx = 0;
        c.gridy = 1;
        this.add(this.fromText, c);

        c.gridx = 1;
        this.add(this.fromIndex, c);

        c.gridx = 2;
        this.add(this.toText, c);

        c.gridx = 3;
        this.add(this.toIndex, c);

        c.gridx = 0;
        c.gridy = 2;
        this.add(this.baseFrameText, c);

        c.gridx = 1;
        this.add(this.baseFrameSpinner, c);
    }

    private void setListener() {
    }
}

