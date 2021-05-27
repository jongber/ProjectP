package com.projecta.game.desktop.editor.spriteeditor.cmdwindow;

import com.badlogic.gdx.graphics.Texture;
import com.projecta.game.core.util.Struct2;
import com.projecta.game.core.util.Struct3;
import com.projecta.game.core.util.Tuple2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileFilter;

public class SpriteCreateDialog extends JDialog {

    SpriteEditCmd cmd;

    ImageLoadArea imageArea;
    PivotArea pivotArea;
    TextureRegionArea regionArea;
    JButton btOk;

    public SpriteCreateDialog(SpriteEditCmd spriteEditCmd, String create_sprite, boolean b) {
        super(spriteEditCmd, create_sprite, b);
        this.cmd = spriteEditCmd;
        this.setLayout(new GridBagLayout());
        this.init();
        this.setListener();
    }

    private void init() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 10, 5, 10);
        c.fill = GridBagConstraints.VERTICAL;

        this.imageArea = new ImageLoadArea();
        c.gridx = 0;
        c.gridy = 0;
        this.add(this.imageArea, c);

        this.pivotArea = new PivotArea();
        c.gridy += 1;
        this.add(this.pivotArea, c);

        this.regionArea = new TextureRegionArea();
        c.gridy += 1;
        this.add(this.regionArea, c);

        this.btOk = new JButton("OK");
        c.gridy+=1;
        this.add(this.btOk, c);
    }

    private void setListener() {
        this.btOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SpriteCreateDialog.this.onClickOK();
            }
        });
    }

    private void onClickOK() {
        Tuple2<Boolean, String> validity = this.checkValidity();
        if (validity.getItem1() == false) {
            JOptionPane.showMessageDialog(null, validity.getItem2());
            return;
        }
    }

    private Tuple2<Boolean, String> checkValidity() {
        String err = "";
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.imageArea.imageFile);
        }
        catch (Exception e) {
            err = "invalid image!!";
            return new Tuple2<>(false, err);
        }

        Struct3<Integer, Integer, Integer> values = this.regionArea.getValues();
        int pixelUnit = values.item1;
        int from = values.item2;
        int to = values.item3;

        if (pixelUnit == 0 || from > to) {
            err = "pixel unit / from, to has problem";
            return new Tuple2<>(false, err);
        }

        int xUnit = image.getWidth() / pixelUnit;
        int yUnit = image.getHeight() / pixelUnit;

        if (xUnit == 0 || yUnit == 0) {
            err = "pixel unit too big";
            return new Tuple2<>(false, err);
        }

        if (from < 0 || from >= xUnit * yUnit || to >= xUnit * yUnit) {
            err = "index too big";
            return new Tuple2<>(false, err);
        }

        return new Tuple2<>(true, err);
    }
}

class ImageLoadArea extends JPanel {
    public JLabel lbPath;
    public JButton btLoad;
    public File imageFile;

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
        this.btLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
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

                ImageLoadArea.this.onLoad(f);
            }
        });
    }

    private void onLoad(File f) {
        this.lbPath.setText(f.getAbsolutePath());
        this.imageFile = f;
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
    }

    public Struct2<Integer, Integer> getPivot() {
        return new Struct2<>((Integer)xSpinner.getValue(), (Integer)ySpinner.getValue());
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
    }

    public Struct3<Integer, Integer, Integer> getValues() {
        return new Struct3<>((Integer)this.unitSpinner.getValue(), (Integer)this.fromIndex.getValue(), (Integer)this.toIndex.getValue());
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
}