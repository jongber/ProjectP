package com.jongber.game.desktop.common.component;

import com.badlogic.gdx.graphics.Color;
import com.jongber.game.core.component.Component;

public class BorderComponent extends Component {
    public float width;
    public float height;
    public Color color;
    public int lineWidth;

    public BorderComponent(Color color, int lineWidth, float width, float height) {
        this.width = width;
        this.height = height;
        this.lineWidth = lineWidth;
        this.color = color;
    }
}
