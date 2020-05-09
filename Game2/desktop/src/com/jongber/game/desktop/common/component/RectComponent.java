package com.jongber.game.desktop.common.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class RectComponent {
    public float lineWidth = 1.0f;
    public Color color = Color.RED;
    public ShapeRenderer.ShapeType type;
    public Rectangle rect = new Rectangle(0, 0, 32.0f, 32.0f);
}
