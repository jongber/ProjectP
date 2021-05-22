package com.projecta.game.desktop.common.data;

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

    public static class MouseMove {
        public int x;
        public int y;
    }

    public MouseMove move = new MouseMove();
    public ButtonState button = new ButtonState();
    public DragState drag = new DragState();
}
