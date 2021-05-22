package com.projecta.game.desktop.common;

public class MouseState {

    public static class ButtonState{
        public boolean touchDown;
        public int x;
        public int y;
        public int button;
    }

    public static class DragState {
        public int x;
        public int y;
    }

    public ButtonState button = new ButtonState();
    public DragState drag = new DragState();
}
