package com.jongber.game.desktop.editor.map.component;

import com.jongber.game.core.component.Component;

public class GroundProperty extends Component {
    public float height;
    public float width;

    public GroundProperty(float width, float height) {
        this.width = width;
        this.height = height;
    }
}
