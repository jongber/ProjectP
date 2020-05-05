package com.jongber.game.old.json;

import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.util.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class RoomJson {
    public String name;
    public int sanity;
    public int noise;
    public int height;
    public int width;
    public String wallpaperPath;

    public List<Tuple2<String, Vector2>> props = new ArrayList<>();
}
