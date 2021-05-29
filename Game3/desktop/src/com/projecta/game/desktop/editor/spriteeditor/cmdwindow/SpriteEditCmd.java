package com.projecta.game.desktop.editor.spriteeditor.cmdwindow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.projecta.game.core.base.layer.GameLayer;
import com.projecta.game.core.base.layer.GameLayerMessage;
import com.projecta.game.desktop.common.Adjuster;
import com.projecta.game.desktop.common.Env;
import com.projecta.game.desktop.editor.spriteeditor.panel.HUDSpriteEditPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
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
        this.setTitle("Sprite Editor Commander");
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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void init() {
        this.inactivate();
    }

    public void onCreate() {
        SpriteCreateDialog dialog = new SpriteCreateDialog(this, "Create Sprite", true);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void receiveCreate(String imagePath,
                              Vector2 pivot,
                              int pixelUnitX,
                              int pixelUnitY,
                              int from, int to,
                              int frameMS) {

        this.hud.postMessage(new GameLayerMessage() {
            @Override
            public void handle(GameLayer layer) {
                String path = Adjuster.adjustFilePath(imagePath);
                SpriteEditCmd.this.hud.createSprite(path, pivot, pixelUnitX, pixelUnitY, from, to, frameMS);
            }
        });

        this.buttonArea.activate();
    }

    public void onSave() {
    }

    public void onLoad() {
    }

    public void onClear() {
        this.hud.clearPanels();
        this.inactivate();
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
        this.btCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ButtonArea.this.cmd.onCreate();
            }
        });
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

