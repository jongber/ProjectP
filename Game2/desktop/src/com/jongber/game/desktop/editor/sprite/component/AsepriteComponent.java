package com.jongber.game.desktop.editor.sprite.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.component.Component;
import com.jongber.game.desktop.editor.sprite.AsepriteJson;

import java.util.ArrayList;
import java.util.List;

public class AsepriteComponent extends Component {

    Texture texture;
    AsepriteJson json;

    public List<TextureRegion> regions = new ArrayList<>();

    public AsepriteComponent(AsepriteJson json, Texture texture) {
        this.json = json;
        this.texture = texture;

        for (AsepriteJson.Frame f : json.frames) {
            TextureRegion region = new TextureRegion(texture);
            region.setRegion(f.frame.x, f.frame.y, f.frame.w, f.frame.h);
            regions.add(region);
        }
    }
}
