package com.jongber.game.desktop.map.event;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.core.util.Tuple2;
import com.jongber.game.desktop.viewer.component.PropProperty;
import com.jongber.game.desktop.viewer.component.RoomProperty;
import com.jongber.game.projectz.json.RoomJson;

public class AddRoomEvent extends GameEvent {

    public interface Callback {
        void callback(GameObject created);
    }

    private Callback callback;
    private GameLayer layer;
    private RoomJson json;

    public AddRoomEvent(GameLayer layer, RoomJson json, Callback callback) {
        this.layer = layer;
        this.callback = callback;
        this.json = json;
    }

    @Override
    public void handle() {

        Texture tex = AssetManager.getTexture(json.wallpaperPath);
        TextureRegion region = new TextureRegion(tex, json.width, json.height);

        RoomProperty p = new RoomProperty(region, json.width, json.height, json.sanity, json.noise);

        GameObject room = new GameObject();
        room.name = json.name;
        room.addComponent(p);

        this.layer.addObject(room);

        for (Tuple2<String, Vector2> tuple : json.props) {
            Texture texture = AssetManager.getTexture(tuple.getItem1());
            TextureRegion propRegion = new TextureRegion(texture);
            PropProperty prop = new PropProperty(propRegion);

            GameObject created = new GameObject();
            created.addComponent(prop);
            created.transform.local.setToTranslation(tuple.getItem2());

            this.layer.addObject(room, created);
        }

        this.callback.callback(room);
    }
}
