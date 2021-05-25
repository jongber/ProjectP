package com.projecta.game.desktop.editor.cmdwindow;

import com.badlogic.gdx.Gdx;
import com.projecta.game.desktop.editor.panel.HUDSpriteEditPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
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

        panel = new ImageLoadArea();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = row++;
        frame.add(panel, c);

        panel = new PivotArea();
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
        this.setBorder(BorderFactory.createTitledBorder("Sprite sheet path"));
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

    public TextureRegionArea() {
    }
}

