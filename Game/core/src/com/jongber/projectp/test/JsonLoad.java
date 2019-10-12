package com.jongber.projectp.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.projectp.asset.aseprite.AsepriteJson;
import com.jongber.projectp.asset.aseprite.Frame;

public class JsonLoad extends ApplicationAdapter {

    @Override
    public void create () {
        AsepriteJson json = AsepriteJson.load("object/hero.json");
        createAsset(json);
    }

    @Override
    public void render() {
    }

    @Override
    public void dispose() {
    }

    private void createAsset(AsepriteJson json) {

        Texture texture = new Texture(json.getImgPath());
        TextureRegion[] regions = new TextureRegion[json.frames.size()];

        for (int i = 0; i < regions.length; ++i) {
            Frame f = json.frames.get(i);
            regions[i] = new TextureRegion(texture, f.frame.x, f.frame.y, f.frame.w, f.frame.h);
        }
    }
}