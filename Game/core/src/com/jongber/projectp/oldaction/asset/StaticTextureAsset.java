package com.jongber.projectp.oldaction.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class StaticTextureAsset {
    private Texture texture;
    private Vector2 pivot = new Vector2();

    public void set(Texture texture) {
        this.texture = texture;
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


    public Texture getTexture() {
        return this.texture;
    }

    public void dispose() {
        this.texture.dispose();
    }
}
