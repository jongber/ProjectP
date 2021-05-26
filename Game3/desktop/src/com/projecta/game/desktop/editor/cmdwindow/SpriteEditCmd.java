package com.projecta.game.desktop.editor.cmdwindow;

import com.badlogic.gdx.Gdx;
import com.projecta.game.desktop.editor.panel.HUDSpriteEditPanel;

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

    public SpriteEditCmd(HUDSpriteEditPanel hud) {
        super();
        this.hud = hud;
    }

    public static void create(HUDSpriteEditPanel hud) {
        JPanel panel = null;
        GridBagConstraints c = new GridBagConstraints();

        SpriteEditCmd frame = new SpriteEditCmd(hud);
        frame.setLayout(new GridBagLayout());

        int row = 0;

        panel = new SaveLoadArea();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = row++;
        frame.add(panel, c);

        panel = new ImageLoadArea();
        c.gridy = row++;
        frame.add(panel, c);

        panel = new PivotArea();
        c.gridy = row++;
        frame.add(panel, c);

        panel = new TextureRegionArea();
        c.gridy = row++;
        frame.add(panel, c);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                Gdx.app.exit();
            }
        });
        frame.pack();
        frame.setVisible(true);
    }
}

class SaveLoadArea extends JPanel {
    private JButton save;
    private JButton load;

    public SaveLoadArea() {
        this.setBorder(BorderFactory.createTitledBorder("Save Load Area"));
        this.setLayout(new GridBagLayout());
        this.init();
        this.setListener();
    }

    private void init() {
        this.save = new JButton("Save");
        this.load = new JButton("Load");

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(0, 10, 0, 10);
        this.add(this.save, c);

        c.gridx = 1;
        this.add(this.load, c);
    }

    private void setListener() {

    }
}

class ImageLoadArea extends JPanel {
    private JLabel pathLabel;
    private JButton loadButton;

    public ImageLoadArea() {
        this.setBorder(BorderFactory.createTitledBorder("Sprite sheet path"));
        this.setLayout(new GridBagLayout());

        this.init();
    }

    private void init() {
        GridBagConstraints c = new GridBagConstraints();

        this.pathLabel = new JLabel("set image path");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.insets = new Insets(0, 10,0,0);
        this.add(this.pathLabel, c);

        this.loadButton = new JButton("Load Image");
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 10,0,10);
        this.add(this.loadButton, c);

        this.setListener();
    }

    private void setListener() {
    }
}

class PivotArea extends JPanel {
    private JLabel xText;
    private JSpinner xSpinner;

    private JLabel yText;
    private JSpinner ySpinner;

    public PivotArea() {
        this.setBorder(BorderFactory.createTitledBorder("Pivot setting area"));
        this.setLayout(new GridBagLayout());

        this.init();
        this.setListener();
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
    private JSpinner unitSpinner;

    private JLabel fromText;
    private JSpinner fromIndex;
    private JLabel toText;
    private JSpinner toIndex;

    public TextureRegionArea() {
        this.setBorder(BorderFactory.createTitledBorder("Texture Region area"));
        this.setLayout(new GridBagLayout());
        this.init();
        this.setListener();
    }

    private void init() {
        this.unitText = new JLabel("pixel unit per frame");
        this.unitSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 2048, 1));

        this.fromText = new JLabel("index from");
        this.fromIndex = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        this.toText = new JLabel("index to");
        this.toIndex = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));

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
    }

    private void setListener() {

    }
}

