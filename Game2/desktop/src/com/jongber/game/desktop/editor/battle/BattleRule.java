package com.jongber.game.desktop.editor.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.component.TextureComponent;
import com.jongber.game.core.graphics.VFAnimation;
import com.jongber.game.core.sequence.SequencePlan;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.common.component.RectComponent;
import com.jongber.game.desktop.common.component.SpriteComponent;
import com.jongber.game.desktop.common.sequence.CameraShakeSeq;
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

        this.createBg();

        this.layer.addObject(createPlayer("Player1", 1));
        this.layer.addObject(createPlayer("Player2", 2));
        this.layer.addObject(createPlayer("Player3", 3));
        this.layer.addObject(createPlayer("Player4", 4));

        this.layer.addObject(createEnemy("Enemy1", 1));
        this.layer.addObject(createEnemy("Enemy2", 2));
        this.layer.addObject(createEnemy("Enemy3", 3));
        this.layer.addObject(createEnemy("Enemy4", 4));
    }

    public void createBg() {
        // projectz_old/house/wallpapers/3x3_1.png
        for (int i = -5; i < 5; ++ i) {
            GameObject object = new GameObject();
            Texture t = AssetManager.getTexture("projectz_old/house/wallpapers/3x3_1.png");
            TextureRegion r = new TextureRegion(t);
            object.addComponent(new TextureComponent(r));

            object.transform.local.translate(48.0f*i, 0.0f);
            this.layer.addObject(object);
        }

    }

    public GameObject createPlayer(String name, float pos) {
        GameObject object = new GameObject("Player");
        object.transform.local.translate(-16.0f * pos, 0.0f);


        SpriteComponent sc = new SpriteComponent();
        sc.set(assets.get("project/male.json Idle"), VFAnimation.PlayMode.LOOP);
        object.addComponent(sc);

        BattleComponent bc = new BattleComponent();
        bc.battlePosition = new Vector2(object.transform.getLocalPos());
        object.addComponent(bc);

        return object;
    }

    public GameObject createEnemy(String name, float pos) {
        GameObject object = new GameObject(name);
        object.transform.local.translate(16.0f * pos, 0.0f);


        SpriteComponent sc = new SpriteComponent();
        sc.set(assets.get("project/male.json Idle"), VFAnimation.PlayMode.LOOP);
        sc.flip(true, false);
        object.addComponent(sc);

        BattleComponent bc = new BattleComponent();
        bc.battlePosition = new Vector2(object.transform.getLocalPos());
        object.addComponent(bc);

        return object;
    }

    public SequencePlan createAttackPlan() {
        SequencePlan plan = new SequencePlan();

        CameraShakeSeq seq = new CameraShakeSeq(this.layer, 2.0f, 0.15f);

        plan.addTimeSeq(0.2f, seq);

        return plan;
    }
}
