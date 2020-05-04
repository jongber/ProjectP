package com.jongber.game.desktop.editor;

import com.jongber.game.core.GameLayer;

import javax.swing.JFrame;

public abstract class EditorCmd extends JFrame {

    public abstract void create(GameLayer layer);
}
