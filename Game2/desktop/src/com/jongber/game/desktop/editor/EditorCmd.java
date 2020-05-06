package com.jongber.game.desktop.editor;

import javax.swing.JFrame;

public abstract class EditorCmd extends JFrame {

    public abstract void create(EditorView layer);
}
