package com.jongber.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.Utility;
import com.jongber.game.desktop.common.json.AsepriteJson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditorAssetManager {

    private final static HashMap<Tuple2<File, String>, AnimationAsset> map = new HashMap<>();

    public static void loadAseprite(File jsonFile) {
        AsepriteJson json = Utility.readJson(AsepriteJson.class, jsonFile);
        if (json == null) {
            Gdx.app.error("ERROR", "invalid json file!");
            return;
        }

        //// parse animation asset..
    }

    private static List<AnimationAsset> parseAnimAssets(AsepriteJson json, Texture texture) {
        List<AnimationAsset> assets = new ArrayList<>();
        List<TextureRegion> regions = new ArrayList<>();
        List<Integer> durations = new ArrayList<>();

        for (AsepriteJson.FrameTag tag : json.meta.frameTags) {
            for (int i = tag.from; i <= tag.to; ++i) {
                AsepriteJson.xywh size = json.frames.get(i).spriteSourceSize;

                regions.add(new TextureRegion(texture, size.x, size.y, size.w, size.h));
                durations.add(json.frames.get(i).duration);
            }

            assets.add(new AnimationAsset(tag.name, regions, durations));

            regions.clear();
            durations.clear();
        }

        return assets;
    }
}











