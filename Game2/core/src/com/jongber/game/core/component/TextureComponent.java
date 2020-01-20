package com.jongber.game.core.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.Component;

public class TextureComponent extends Component {

    public final TextureRegion region;
    public Vector2 pivot = new Vector2();

    public TextureComponent(TextureRegion region) {
        this.region = region;
    }
}
