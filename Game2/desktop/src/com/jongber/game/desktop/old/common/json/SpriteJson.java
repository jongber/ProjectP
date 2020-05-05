package com.jongber.game.desktop.old.common.json;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.util.Tuple2;

import java.util.ArrayList;
import java.util.List;

public class SpriteJson {
    public String name;
    public String image;
    public List<Tuple2<Rectangle, Integer>> frames = new ArrayList<>();
    public Vector2 pivot = new Vector2();
}
