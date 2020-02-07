package com.jongber.game.projectz.json;

import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.util.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class MapJson {
    public int width;
    public int height;
    public int groundHeight;

    public List<Tuple2<String, Vector2>> backProps = new ArrayList<>();
    public List<RoomJson> rooms = new ArrayList<>();
}
