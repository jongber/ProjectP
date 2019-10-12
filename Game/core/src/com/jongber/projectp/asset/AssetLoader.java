package com.jongber.projectp.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.projectp.asset.aseprite.AsepriteJson;
import com.jongber.projectp.asset.aseprite.Frame;
import com.jongber.projectp.asset.aseprite.FrameTag;

import java.util.ArrayList;
import java.util.List;

public class AssetLoader {

    public static SpriteAsset loadSprite(String name, AsepriteJson json) {

        SpriteAsset asset = new SpriteAsset();

        Texture texture = new Texture(json.getImgPath());
        List<TextureRegion> regions = new ArrayList<>();

        for (int i = 0; i < json.frames.size(); ++i) {
            Frame f = json.frames.get(i);
            regions.add(new TextureRegion(texture, f.frame.x, f.frame.y, f.frame.w, f.frame.h));
        }

        asset.setName(name);
        asset.setTexture(texture);

        for (FrameTag tag : json.meta.frameTags) {

            if (tag.name.startsWith("E_"))
                continue;

            int from = tag.from;
            int to = tag.to + 1;

            List<Frame> frameSlice = json.frames.subList(from, to);
            TextureRegion[] regionSlice = regions.subList(from, to).toArray(new TextureRegion[to - from]);

            AnimationAsset anim = new AnimationAsset(tag.name, regionSlice, frameSlice);
            asset.addAnimation(tag.name, anim);
        }

        return asset;
    }
}























