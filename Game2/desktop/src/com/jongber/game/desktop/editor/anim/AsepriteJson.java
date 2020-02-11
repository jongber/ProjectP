package com.jongber.game.desktop.editor.anim;

import java.util.ArrayList;
import java.util.List;

public class AsepriteJson {

    public static class Frame {
        public String filename = "";
        public xywh frame = new xywh();
        public boolean rotated;
        public boolean trimmed;
        public xywh spriteSourceSize = new xywh();
        public wh sourceSize = new wh();
        public int duration;
    }

    public static class Meta {
        public String app;
        public String version;
        public String image;
        public String format;
        public wh size = new wh();
        public int scale;
        public List<FrameTag> frameTags = new ArrayList<>();
        public List<String> layers = new ArrayList<>();
        public List<String> slices = new ArrayList<>();
    }

    public static class FrameTag {
        public String name;
        public int from;
        public int to;
        public String direction;
    }

    public static class xy {
        public int x;
        public int y;
    }

    public static class xywh {
        public int x;
        public int y;
        public int w;
        public int h;
    }

    public static class wh {
        public int w;
        public int h;
    }

    public List<Frame> frames = new ArrayList<>();
    public Meta meta;
}