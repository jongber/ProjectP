package com.jongber.game.desktop.room.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.component.Component;

public class PropProperty extends Component {
    public TextureRegion texture;

    public PropProperty(TextureRegion region) {
        this.texture = region;
    }
}
