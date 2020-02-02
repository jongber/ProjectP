package com.jongber.game.projectz.json;

import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.util.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class RoomJson {
    public String name;
    public List<Tuple2<String, Vector2>> props = new ArrayList<>();
}
