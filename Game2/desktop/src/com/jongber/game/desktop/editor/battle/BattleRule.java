package com.jongber.game.desktop.editor.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.graphics.VFAnimation;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.common.component.SpriteComponent;
import com.jongber.game.desktop.editor.EditorAssetManager;
import com.jongber.game.desktop.editor.EditorCmd;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class BattleRule {

    HashMap<String, AnimationAsset> assets = new HashMap<>();

    public BattleRule(GameLayer layer) {
        init();
    }

    public void init() {
        FileHandle handle = Gdx.files.internal(EditorCmd.BasePath + "/project/male.json");

        List<Tuple2<AnimationAsset, String>> list = EditorAssetManager.loadAseprite(handle.file());
        for (Tuple2<AnimationAsset, String> item : list) {
            assets.put(item.getItem1().getName(), item.getItem1());
        }

        createPlayer();
    }

    public void createPlayer() {
        GameObject object = new GameObject("Player");
        SpriteComponent sprite = new SpriteComponent();
        sprite.set(assets.get("project/male.json Idle"), VFAnimation.PlayMode.LOOP);

        object.addComponent(sprite);

    }

}
