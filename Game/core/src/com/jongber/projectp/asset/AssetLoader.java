package com.jongber.projectp.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.projectp.asset.aseprite.AsepriteJson;
import com.jongber.projectp.asset.aseprite.Frame;

import java.util.ArrayList;
import java.util.List;

public class AssetLoader {
    public static List<Texture> textures = new ArrayList<>();

    public static SpriteAsset load(String tag, AsepriteJson json) {

        Texture texture = new Texture(json.getImgPath());
        TextureRegion[] regions = new TextureRegion[json.frames.size()];

        for (int i = 0; i < regions.length; ++i) {
            Frame f = json.frames.get(i);
            regions[i] = new TextureRegion(texture, f.frame.x, f.frame.y, f.frame.w, f.frame.h);
        }

        SpriteAsset asset = new SpriteAsset();
        asset.texture = texture;
        asset.regions = regions;
        asset.tag = tag;

        return asset;
    }

    public static void dispose() {
        for (Texture texture : textures) {
            texture.dispose();
        }
    }
}























