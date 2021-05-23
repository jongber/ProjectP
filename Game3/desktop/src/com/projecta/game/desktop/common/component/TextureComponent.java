package com.projecta.game.desktop.common.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.projecta.game.core.base.object.GameObjectComponent;

public class TextureComponent extends GameObjectComponent {
    public TextureRegion region;
    public Vector2 pivot;

    public TextureComponent(TextureRegion region, Vector2 pivot) {
        this.region = region;
        this.pivot = pivot;
    }

    public TextureComponent(TextureRegion region) {
        this(region, new Vector2());
    }
}
