package com.jongber.game.desktop.editor;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class EditorCmd extends JFrame {

    public interface AreaImpl {
        JPanel createPanel();
    }

    public final static String BasePath;

    static {
        BasePath = System.getProperty("user.dir") +
                File.separator + "android" + File.separator + "assets";
    }

    public abstract void create(EditorView layer);
}
