package com.jongber.game.projectz.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.component.Component;

public class RoomProperty extends Component {
    public final String name;
    public final String texturePath;
    public final TextureRegion textureRegion;
    public int sanity;
    public int noise;
    public final int height;
    public final int width;

    public RoomProperty(String name,
                        String texturePath,
                        TextureRegion region,
                        int width,
                        int height,
                        int sanity,
                        int noise) {
        this.name = name;
        this.texturePath = texturePath;
        this.textureRegion = region;
        this.sanity = sanity;
        this.noise = noise;
        this.width = width;
        this.height = height;
    }
}
