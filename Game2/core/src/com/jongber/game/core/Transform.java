package com.jongber.game.core;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Transform {
    public Rectangle local = new Rectangle();
    public List<Rectangle> parentStack = new ArrayList<>();
}
