package com.jongber.game.desktop.editor.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AnimationAsset;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.component.TextureComponent;
import com.jongber.game.core.graphics.VFAnimation;
import com.jongber.game.core.sequence.SequencePlan;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.common.component.SpriteComponent;
import com.jongber.game.desktop.common.sequence.camera.CameraMoveSeq;
import com.jongber.game.desktop.common.sequence.camera.CameraRotationSeq;
import com.jongber.game.desktop.common.sequence.camera.CameraShakeSeq;
import com.jongber.game.desktop.editor.EditorAssetManager;
import com.jongber.game.desktop.editor.EditorCmd;
import com.jongber.game.desktop.editor.battle.seq.ChangeAnimSeq;
import com.jongber.game.desktop.editor.battle.seq.GameObjectMoveSeq;
import com.jongber.game.desktop.editor.battle.seq.SpriteScaleSeq;

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
        GameObject object = new GameObject(name);
        object.transform.local.translate(-16.0f * pos, 0.0f);


        SpriteComponent sc = new SpriteComponent();
        sc.set(assets.get("project/male.json Idle"), VFAnimation.PlayMode.LOOP);
        object.addComponent(sc);

        BattleComponent bc = new BattleComponent();
        bc.orgPos = new Vector2(object.transform.getLocalPos());
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
        bc.orgPos = new Vector2(object.transform.getLocalPos());
        object.addComponent(bc);

        return object;
    }

    public void createAttackPlan(SequencePlan plan, String name) {
        GameObject object = layer.getObjectAny(name);

        BattleComponent c = object.getComponent(BattleComponent.class);

        c.orgPos = object.transform.getLocalPos();
        c.orgScale = 1.0f;

        GameObjectMoveSeq s1 = new GameObjectMoveSeq(object, new Vector2(-16.0f, -32.0f), 0.2f);
        plan.addTimeSeq(0.0f, s1);

        SpriteScaleSeq s2 = new SpriteScaleSeq(object, 2.0f, 0.2f);
        plan.addTimeSeq(0.0f, s2);

        plan.addTimeSeq(0.1f, new CameraMoveSeq(new Vector3(0.0f, 0.0f, 0.5f), 0.2f));

        ChangeAnimSeq cs = new ChangeAnimSeq(object, assets.get("project/male.json aMelee"), VFAnimation.PlayMode.ONCE);
        plan.addLinkedSeq(s2, cs);

        plan.addLinkedSeq(cs, new CameraRotationSeq(14.0f, 0.2f));

        s1 = new GameObjectMoveSeq(object, new Vector2(-20.0f, -32.0f), 0.7f);
        plan.addLinkedSeq(cs, s1);

        CameraShakeSeq seq = new CameraShakeSeq(2.0f, 0.25f);
        plan.addLinkedSeq(cs, seq, 0.3f);

        s1 = new GameObjectMoveSeq(object, c.orgPos, 0.2f);
        plan.addLinkedSeq(seq, s1);

        s2 = new SpriteScaleSeq(object, c.orgScale, 0.2f);
        plan.addLinkedSeq(seq, s2);

        cs = new ChangeAnimSeq(object, assets.get("project/male.json Idle"), VFAnimation.PlayMode.LOOP);
        plan.addLinkedSeq(s2, cs);

        plan.addLinkedSeq(cs, new CameraMoveSeq(new Vector3(0.0f, 0.0f, 1.0f), 0.2f));
        plan.addLinkedSeq(cs, new CameraRotationSeq(-14.0f, 0.2f));
    }
}
