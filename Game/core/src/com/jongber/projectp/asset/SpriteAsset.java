package com.jongber.projectp.asset;


import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Set;

public class SpriteAsset {

    private String name;
    private Texture texture;
    private HashMap<String, AnimationAsset> anims = new HashMap();

    public void setName(String name) {
        this.name = name;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void addAnimation(String tag, AnimationAsset anim) {
        anims.put(tag, anim);
    }

    public AnimationAsset getAnimation(String name) {
        return this.anims.get(name);
    }

    public Set<String> getFrameNames() {
        return this.anims.keySet();
    }

    public void dispose() {
        this.texture.dispose();
    }
}
