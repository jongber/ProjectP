package com.jongber.projectp.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.projectp.asset.json.AsepriteJson;
import com.jongber.projectp.asset.aseprite.Frame;
import com.jongber.projectp.asset.aseprite.FrameTag;
import com.jongber.projectp.asset.aseprite.Layer;
import com.jongber.projectp.asset.json.GameObjectJson;
import com.jongber.projectp.asset.json.GameSettingJson;
import com.jongber.projectp.asset.json.GameWorldJson;
import com.jongber.projectp.common.FnTr;
import com.jongber.projectp.game.World;
import com.jongber.projectp.graphics.VFAnimation;
import com.jongber.projectp.object.GameObject;
import com.jongber.projectp.object.component.GameLogicComponent;
import com.jongber.projectp.object.component.SceneryComponent;
import com.jongber.projectp.object.component.SpriteComponent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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

        if (GameAsset.textureAssets.containsKey(filename)) {
            return GameAsset.textureAssets.get(filename);
        }

        AsepriteJson json = AsepriteJson.load(filename);
        return GameAsset.loadTexture(filename, json);
    }

    public static SpriteAsset loadSprite(String filename) {

        if (GameAsset.spriteAssets.containsKey(filename)) {
            return GameAsset.spriteAssets.get(filename);
        }

        AsepriteJson json = AsepriteJson.load(filename);
        return GameAsset.loadSprite(filename, json);
    }

    public static GameObject inflate(String filename) {
        GameObjectJson json = GameObjectJson.load(filename);
        return GameAsset.inflate(json);
    }

    public static GameObject inflate(GameObjectJson json) {
        GameObject object = new GameObject(json.name);

        if (json.sprite != null) {
            try {
                SpriteAsset asset = GameAsset.loadSprite(json.sprite.filename);
                SpriteComponent component = new SpriteComponent(asset);
                VFAnimation.PlayMode mode = VFAnimation.PlayMode.LOOP;
                if (mode.toString().compareToIgnoreCase(json.sprite.mode) != 0) {
                    mode = VFAnimation.PlayMode.ONCE;
                }
                component.setAnimation(json.sprite.animation, mode);
                object.addComponent(SpriteComponent.class, component);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (json.scenery != null) {
            StaticTextureAsset asset = GameAsset.loadTexture(json.scenery.filename);
            object.addComponent(SceneryComponent.class, new SceneryComponent(asset, json.scenery.moveRatio));
        }

        if (json.logic != null) {
            try {
                Class logicClass = Class.forName("com.jongber.projectp.game.detail." + json.logic);
                GameLogicComponent compoenent = new GameLogicComponent((GameLogicComponent.LogicImpl) logicClass.newInstance());
                object.addComponent(GameLogicComponent.class, compoenent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return object;
    }

    public static World inflate(GameSettingJson settingJson, String filename) {
        World world = new World(settingJson);

        GameWorldJson json = GameWorldJson.load(filename);
        if (json.sceneries != null) {
            Iterator<FnTr> it = json.sceneries.iterator();
            while (it.hasNext()) {
                FnTr fntr = it.next();

                GameObject scenery = GameAsset.inflate(fntr.filename);
                scenery.setTransform(new Vector2(fntr.transform.x, fntr.transform.y));

                world.sceneries.add(scenery);
            }
        }

        if (json.objects != null) {
            Iterator<FnTr> it = json.objects.iterator();
            while (it.hasNext()) {
                FnTr fntr = it.next();

                GameObject object = GameAsset.inflate(fntr.filename);
                object.setTransform(new Vector2(fntr.transform.x, fntr.transform.y));

                world.objects.add(object);
            }
        }


        return world;
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

    private static SpriteAsset loadSprite(String filename, AsepriteJson json) {

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

    private static StaticTextureAsset loadTexture(String filename, AsepriteJson json) {

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
}























