package com.jongber.game.desktop.editor.battle;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.editor.EditorAssetManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class BattleRule {

    HashMap<String, AnimationAsset> assets = new HashMap<>();

    public BattleRule(GameLayer layer) {
        init();
    }

    public void init() {
        List<Tuple2<AnimationAsset, String>> list = EditorAssetManager.loadAseprite(new File("project/male.json"));
        for (Tuple2<AnimationAsset, String> item : list) {
            assets.put(item.getItem1().getName(), item.getItem1());
        }
    }

    public void createPlayer() {
        GameObject object = new GameObject();
    }

}
