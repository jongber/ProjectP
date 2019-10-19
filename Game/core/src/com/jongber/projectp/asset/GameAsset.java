package com.jongber.projectp.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.projectp.asset.json.AsepriteJson;
import com.jongber.projectp.asset.aseprite.Frame;
import com.jongber.projectp.asset.aseprite.FrameTag;
import com.jongber.projectp.asset.aseprite.Layer;
import com.jongber.projectp.asset.json.GameObjectJson;
import com.jongber.projectp.object.GameObject;
import com.jongber.projectp.object.component.SceneryComponent;
import com.jongber.projectp.object.component.SpriteComponent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class GameAsset {

    private static HashMap<String, SpriteAsset> spriteAssets;
    private static HashMap<String, StaticTextureAsset> textureAssets;

    static {
        GameAsset.spriteAssets = new HashMap<>();
        GameAsset.textureAssets = new HashMap<>();
    }

    public static StaticTextureAsset loadTexture(String filename) {
        AsepriteJson json = AsepriteJson.load(filename);
        return GameAsset.loadTexture(filename, json);
    }

    public static StaticTextureAsset loadTexture(String filename, AsepriteJson json) {

        if (GameAsset.textureAssets.containsKey(filename)) {
            return GameAsset.textureAssets.get(filename);
        }

        StaticTextureAsset asset = new StaticTextureAsset();

        Texture texture = new Texture((json.getImgPath()));
        asset.set(texture);

        //// *set pivot point
        //// y좌표축 방향이 다름..
        int height = texture.getHeight();
        for (Layer layer : json.meta.layers) {
            if (layer.name.startsWith("P_pivot")) {
                String[] split = layer.name.split("_");
                asset.setPivot(new Vector2(Integer.parseInt(split[2]), height - Integer.parseInt(split[3])));
            }
        }

        GameAsset.textureAssets.put(filename, asset);

        return asset;
    }

    public static SpriteAsset loadSprite(String filename) {
        AsepriteJson json = AsepriteJson.load(filename);
        return GameAsset.loadSprite(filename, json);
    }

    public static SpriteAsset loadSprite(String filename, AsepriteJson json) {

        if (GameAsset.spriteAssets.containsKey(filename)) {
            return GameAsset.spriteAssets.get(filename);
        }

        SpriteAsset asset = new SpriteAsset();

        Texture texture = new Texture(json.getImgPath());
        TextureRegion[] regions = new TextureRegion[json.frames.size()];

        for (int i = 0; i < json.frames.size(); ++i) {
            Frame f = json.frames.get(i);
            regions[i] = new TextureRegion(texture, f.frame.x, f.frame.y, f.frame.w, f.frame.h);
        }

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

        GameAsset.spriteAssets.put(filename, asset);

        return asset;
    }

    public static GameObject inflate(String filename) {
        GameObjectJson json = GameObjectJson.load(filename);
        return GameAsset.inflate(json);
    }

    public static GameObject inflate(GameObjectJson json) {
        GameObject object = new GameObject(json.name);

        if (json.sprite != null) {
            SpriteAsset asset = GameAsset.loadSprite(json.sprite);
            object.addComponent(SpriteComponent.class, new SpriteComponent(asset));
        }

        if (json.scenery != null) {
            StaticTextureAsset asset = GameAsset.loadTexture(json.scenery.filename);
            object.addComponent(SceneryComponent.class, new SceneryComponent(asset, json.scenery.moveRatio));
        }

        if (json.transform != null) {
            object.setTransform(new Vector2(json.transform.x, json.transform.y));
        }

        return object;
    }

    public static void dispose() {
        Set<String> keys = GameAsset.spriteAssets.keySet();
        for (String key : keys) {
            SpriteAsset item = GameAsset.spriteAssets.get(key);
            item.dispose();
        }
        GameAsset.spriteAssets.clear();

        keys = GameAsset.textureAssets.keySet();
        for (String key : keys) {
            StaticTextureAsset item = GameAsset.textureAssets.get(key);
            item.dispose();
        }
        GameAsset.textureAssets.clear();
    }
}























