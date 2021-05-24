package com.projecta.game.desktop.editor.panel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.projecta.game.core.base.layer.GameLayer;
import com.projecta.game.core.base.layer.GameLayerMessage;
import com.projecta.game.desktop.common.Env;
import com.projecta.game.desktop.common.panel.HUDPanel;
import com.projecta.game.desktop.common.pipeline.PerfRender;
import com.projecta.game.desktop.editor.SpriteEditor;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class SpriteEditorHUDPanel extends HUDPanel {

    private AtomicBoolean jfcRunning = new AtomicBoolean(false);

    private Skin skin;

    private SpriteFramePanel framePanel;
    private SpriteAnimatePanel animatePanel;
    private TextureRegionPanel regionPanel;

    private Texture texture;

    public SpriteEditorHUDPanel(SpriteFramePanel framePanel, SpriteAnimatePanel animatePanel, TextureRegionPanel regionPanel) {
        this.framePanel = framePanel;
        this.animatePanel = animatePanel;
        this.regionPanel = regionPanel;

        this.init();
    }

    private void init() {
        this.addPipeline(new PerfRender());

        this.skin = new Skin(Gdx.files.internal("skin/holo/uiskin.json"));

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        int widthSum = 0;

        Button button = new TextButton("Create", this.skin, "colored");
        button.setPosition(width * 0.005f, height * 0.05f);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SpriteEditorHUDPanel.this.createClicked();
            }
        });
        this.stage.addActor(button);

        widthSum += button.getWidth();

        button = new TextButton("Load", this.skin, "colored");
        button.setPosition(width * 0.005f + widthSum, height * 0.05f);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SpriteEditorHUDPanel.this.loadClicked();
            }
        });
        this.stage.addActor(button);

        widthSum += button.getWidth();

        button = new TextButton("Save", this.skin, "colored");
        button.setPosition( width * 0.005f + widthSum, height * 0.05f);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SpriteEditorHUDPanel.this.saveClicked();
            }
        });
        this.stage.addActor(button);

        widthSum += button.getWidth();

        button = new TextButton("Clear", this.skin, "colored");
        button.setPosition(width * 0.005f + widthSum, height * 0.05f);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SpriteEditorHUDPanel.this.clearClicked();
            }
        });
        this.stage.addActor(button);
    }

    @Override
    public void dispose() {
        super.dispose();
        this.skin.dispose();
        if (this.texture != null) {
            this.texture.dispose();
        }
    }

    private void createClicked() {

        if (jfcRunning.compareAndSet(false, true)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JFileChooser jfc = new JFileChooser();
                        jfc.addChoosableFileFilter(new FileFilter() {
                            @Override
                            public boolean accept(File f) {
                                if (f.isDirectory()) {
                                    return true;
                                } else {
                                    String name = f.getName().toLowerCase();
                                    return name.endsWith(".jpg") || name.endsWith("png") || name.endsWith("jpeg");
                                }
                            }

                            @Override
                            public String getDescription() {
                                return "Image files : jpg, png";
                            }
                        });
                        jfc.setAcceptAllFileFilterUsed(true);
                        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

                        int result = jfc.showOpenDialog(null);
                        if (result != JFileChooser.APPROVE_OPTION) {
                            return;
                        }

                        File f = jfc.getSelectedFile();
                        String name = f.getName().toLowerCase();
                        if (!(name.endsWith(".jpg") || name.endsWith("png") || name.endsWith("jpeg"))) {
                            JOptionPane.showMessageDialog(null, "must be image file!!");
                            return;
                        }

                        SpriteEditorHUDPanel.this.postMessage(new GameLayerMessage() {
                            @Override
                            public void handle(GameLayer layer) {
                                SpriteEditorHUDPanel.this.handleCreate(f);
                            }
                        });
                    }
                    finally {
                        jfcRunning.set(false);
                    }
                }
            }).start();
        }
    }

    private void handleCreate(File file) {
        if (this.texture != null) {
            this.texture.dispose();
        }

        this.texture = new Texture(Gdx.files.internal(file.getAbsolutePath()));
    }

    private void loadClicked() {
    }

    private void saveClicked() {
    }

    private void clearClicked() {
    }
}
