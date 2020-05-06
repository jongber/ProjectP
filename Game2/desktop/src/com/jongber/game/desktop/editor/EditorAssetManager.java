package com.jongber.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.desktop.Utility;
import com.jongber.game.desktop.common.json.AsepriteJson;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EditorAssetManager {

    public static List<AnimationAsset> loadAseprite(File jsonFile) {
        AsepriteJson json = Utility.readJson(AsepriteJson.class, jsonFile);
        if (json == null) {
            Gdx.app.error("ERROR", "invalid aseprite json file!");
            return new ArrayList<>();
        }

        //// parse animation asset..
        String imgPath = jsonFile.getPath() + File.separator + json.meta.image;
        Texture t = AssetManager.getTexture(imgPath);

        return parseAnimAssets(json, t);
    }

    private static List<AnimationAsset> parseAnimAssets(@NotNull AsepriteJson json, Texture texture) {
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











