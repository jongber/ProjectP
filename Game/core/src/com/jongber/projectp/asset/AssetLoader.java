package com.jongber.projectp.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.projectp.asset.aseprite.AsepriteJson;
import com.jongber.projectp.asset.aseprite.Frame;
import com.jongber.projectp.asset.aseprite.FrameTag;
import com.jongber.projectp.asset.aseprite.Layer;

import java.util.Arrays;
import java.util.List;

public class AssetLoader {

    public static StaticTextureAsset loadTexture(String name, AsepriteJson json) {
        StaticTextureAsset asset = new StaticTextureAsset();

        Texture texture = new Texture((json.getImgPath()));
        asset.set(name, texture);

        //// *set pivot point
        //// y좌표축 방향이 다름..
        int height = texture.getHeight();
        for (Layer layer : json.meta.layers) {
            if (layer.name.startsWith("P_pivot")) {
                String[] split = layer.name.split("_");
                asset.setPivot(new Vector2(Integer.parseInt(split[2]), height - Integer.parseInt(split[3])));
            }
        }

        return asset;
    }

    public static SpriteAsset loadSprite(String name, AsepriteJson json) {

        SpriteAsset asset = new SpriteAsset();

        Texture texture = new Texture(json.getImgPath());
        TextureRegion[] regions = new TextureRegion[json.frames.size()];

        for (int i = 0; i < json.frames.size(); ++i) {
            Frame f = json.frames.get(i);
            regions[i] = new TextureRegion(texture, f.frame.x, f.frame.y, f.frame.w, f.frame.h);
        }

        asset.setName(name);
        asset.setTexture(texture);

        //// *set animation sequence
        for (FrameTag tag : json.meta.frameTags) {

            if (tag.name.startsWith("E_"))
                continue;

            int from = tag.from;
            int to = tag.to + 1;

            List<Frame> frameSlice = json.frames.subList(from, to);
            TextureRegion[] regionSlice = Arrays.copyOfRange(regions, from, to);

            AnimationAsset anim = new AnimationAsset(tag.name, regionSlice, frameSlice);
            asset.addAnimation(tag.name, anim);
        }

        //// *set pivot point
        //// y좌표축 방향이 다름..
        int height = regions[0].getRegionHeight();
        for (Layer layer : json.meta.layers) {
            if (layer.name.startsWith("P_pivot")) {
                String[] split = layer.name.split("_");
                asset.setPivot(new Vector2(Integer.parseInt(split[2]), height - Integer.parseInt(split[3])));
            }
        }

        return asset;
    }
}























