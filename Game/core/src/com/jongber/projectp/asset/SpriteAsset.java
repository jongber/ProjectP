package com.jongber.projectp.asset;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Set;

public class SpriteAsset {

    private String name;
    private Texture texture;
    private HashMap<String, AnimationAsset> anims = new HashMap();
    private Vector2 pivot = new Vector2();

    public void setName(String name) {
        this.name = name;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void addAnimation(String tag, AnimationAsset anim) {
        anims.put(tag, anim);
    }

    public void setPivot(Vector2 pivot) {
        this.pivot = pivot;
    }

    public int getPivotX() {
        return (int)this.pivot.x;
    }

    public int getPivotY() {
        return (int)this.pivot.y;
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
