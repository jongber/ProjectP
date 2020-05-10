package com.jongber.game.desktop.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.Utility;
import com.jongber.game.desktop.common.json.AnimationJson;
import com.jongber.game.desktop.common.json.AsepriteJson;
import com.jongber.game.desktop.common.json.JsonList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EditorAssetManager {

    public static JsonList<AnimationJson> toJson(List<Tuple2<AnimationAsset, String/*img*/>> assets) {
        JsonList<AnimationJson> jsons = new JsonList<>();

        for (Tuple2<AnimationAsset, String/*img*/> asset : assets) {
            AnimationJson json = new AnimationJson();

            json.image = asset.getItem2();
            json.name = asset.getItem1().getName();
            json.pivot = asset.getItem1().getPivot();

            for (int i = 0; i < asset.getItem1().getFrameLength(); ++i) {
                TextureRegion r = asset.getItem1().getTextureRegion(i);
                int d = asset.getItem1().getFrameDuration(i);

                json.frames.add(new Tuple2<>(new Rectangle(r.getRegionX(), r.getRegionY(), r.getRegionWidth(), r.getRegionHeight()), d));
            }

            jsons.list.add(json);
        }

        return jsons;
    }

    public static List<Tuple2<AnimationAsset, String>> loadAseprite(File jsonFile) {
        AsepriteJson json = Utility.readJson(AsepriteJson.class, jsonFile);
        if (json == null) {
            Gdx.app.error("ERROR", "invalid aseprite json file!");
            return new ArrayList<>();
        }

        return parseAnimAssets(jsonFile, json);
    }

    private static List<Tuple2<AnimationAsset, String>> parseAnimAssets(File jsonFile, AsepriteJson json) {
        List<Tuple2<AnimationAsset, String>> assets = new ArrayList<>();
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
            AnimationAsset asset = new AnimationAsset(String.format("%s %s",path, tag.name), regions, durations);

            path = base.toURI().relativize(jsonFile.getParentFile().toURI()).getPath();
            String imgPath = path + json.meta.image;
            assets.add(new Tuple2<>(asset, imgPath));

            regions.clear();
            durations.clear();
        }

        return assets;
    }
}











