package com.jongber.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.desktop.Utility;
import com.jongber.game.desktop.common.json.AsepriteJson;

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

        return parseAnimAssets(jsonFile, json);
    }

    private static List<AnimationAsset> parseAnimAssets(File jsonFile, AsepriteJson json) {
        List<AnimationAsset> assets = new ArrayList<>();
        List<TextureRegion> regions = new ArrayList<>();
        List<Integer> durations = new ArrayList<>();
        File base = new File(EditorCmd.BasePath);

        for (AsepriteJson.FrameTag tag : json.meta.frameTags) {

            Texture t = AssetManager.getTexture(jsonFile.getParent() + File.separator + json.meta.image);

            for (int i = tag.from; i <= tag.to; ++i) {
                AsepriteJson.xywh size = json.frames.get(i).frame;

                regions.add(new TextureRegion(t, size.x, size.y, size.w, size.h));
                durations.add(json.frames.get(i).duration);
            }

            String path = base.toURI().relativize(jsonFile.toURI()).getPath();

            assets.add(new AnimationAsset(String.format("%s %s",path, tag.name), regions, durations));

            regions.clear();
            durations.clear();
        }

        return assets;
    }
}











