package com.jongber.game.desktop.editor.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.component.TextureComponent;
import com.jongber.game.core.graphics.VFAnimation;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.common.component.RectComponent;
import com.jongber.game.desktop.common.component.SpriteComponent;
import com.jongber.game.desktop.editor.EditorAssetManager;
import com.jongber.game.desktop.editor.EditorCmd;

import java.util.HashMap;
import java.util.List;

public class BattleRule {

    GameLayer layer;
    HashMap<String, AnimationAsset> assets = new HashMap<>();

    public BattleRule(GameLayer layer) {
        this.layer = layer;
        init();
    }

    public void init() {
        FileHandle handle = Gdx.files.internal(EditorCmd.BasePath + "/project/male.json");

        List<Tuple2<AnimationAsset, String>> list = EditorAssetManager.loadAseprite(handle.file());
        for (Tuple2<AnimationAsset, String> item : list) {
            item.getItem1().setPivot(16.0f, 0.0f);
            assets.put(item.getItem1().getName(), item.getItem1());
        }

        GameObject object;
//        object = createBg();
//        object = createPlayer();
//        object = createEnemy("Enemy1", 1);
//        object = createEnemy("Enemy2", 2);
        object = createBg();

        this.layer.addObject(object);
        this.layer.addObject(object, createPlayer());
        this.layer.addObject(object, createEnemy("Enemy1", 1));
        this.layer.addObject(object, createEnemy("Enemy2", 2));
    }

    public GameObject createBg() {
        // projectz_old/house/wallpapers/3x3_1.png
        GameObject object = new GameObject();
        Texture t = AssetManager.getTexture("projectz_old/house/wallpapers/3x3_1.png");
        TextureRegion r = new TextureRegion(t);
        object.addComponent(new TextureComponent(r));

        return object;
    }

    public GameObject createPlayer() {
        GameObject object = new GameObject("Player");

        SpriteComponent sc = new SpriteComponent();
        sc.set(assets.get("project/male.json Idle"), VFAnimation.PlayMode.LOOP);
        object.addComponent(sc);

        BattleComponent bc = new BattleComponent();
        object.addComponent(bc);

        object.transform.local.translate(-16.0f, 0.0f);

        return object;
    }

    public GameObject createEnemy(String name, float pos) {
        GameObject object = new GameObject(name);

        SpriteComponent sc = new SpriteComponent();
        sc.set(assets.get("project/male.json Idle"), VFAnimation.PlayMode.LOOP);
        sc.flip(true, false);
        object.addComponent(sc);

        BattleComponent bc = new BattleComponent();
        object.addComponent(bc);

        object.transform.local.translate(16.0f * pos, 0.0f);

        return object;
    }
}
