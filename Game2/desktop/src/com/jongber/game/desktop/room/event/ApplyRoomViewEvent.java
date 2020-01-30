package com.jongber.game.desktop.room.event;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.asset.AssetManager;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.projectz.component.RoomProperty;

public class ApplyRoomViewEvent extends GameEvent {

    private GameLayer layer;
    private String name;
    private int sanity;
    private int noise;
    private int height;
    private int width;
    private String wallpaperPath;

    public ApplyRoomViewEvent(GameLayer layer,
                              String name,
                              int sanity,
                              int noise,
                              int height,
                              int width,
                              String wallpaperPath) {
        this.layer = layer;
        this.name = name;
        this.sanity = sanity;
        this.noise = noise;
        this.height = height;
        this.width = width;
        this.wallpaperPath = wallpaperPath;
    }

    @Override
    public void handle() {
        this.layer.resetObject();

        Texture tex = AssetManager.getTexture(this.wallpaperPath);
        TextureRegion region = new TextureRegion(tex, this.width, this.height);

        RoomProperty p = new RoomProperty(name, wallpaperPath, region, width, height, sanity, noise);

        GameObject object = new GameObject();
        object.addComponent(p);

        this.layer.addObject(object);
    }
}
