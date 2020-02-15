package com.jongber.game.desktop.common.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.component.Component;

public class RoomProperty extends Component {
    public TextureRegion textureRegion;
    public int sanity;
    public int noise;
    public int height;
    public int width;

    public RoomProperty(TextureRegion region, int width, int height, int sanity, int noise) {
        this.textureRegion = region;
        this.sanity = sanity;
        this.noise = noise;
        this.width = width;
        this.height = height;
    }
}
