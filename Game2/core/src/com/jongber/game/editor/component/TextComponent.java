package com.jongber.game.editor.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.jongber.game.core.component.Component;

public class TextComponent extends Component {
    public final static int DefaultSize = 24;
    public String text;
    public Color color = Color.WHITE;
    public Vector2 pos = new Vector2();
    public float width = 0;
    public int align = Align.left;
    public int fontSize = DefaultSize;

    public TextComponent(String text) {
        this.text = text;
    }

    public TextComponent(String text, int fontSize, Color color, Vector2 pos, float width, int align) {
        this.text = text;
        this.color = color;
        this.pos.set(pos);
        this.width = width;
        this.align = align;
        this.fontSize = fontSize;
    }
}
