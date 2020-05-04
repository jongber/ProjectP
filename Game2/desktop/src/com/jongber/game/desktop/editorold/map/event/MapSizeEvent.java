package com.jongber.game.desktop.editorold.map.event;

import com.badlogic.gdx.graphics.Color;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.desktop.editorold.map.component.GroundProperty;
import com.jongber.game.desktop.common.component.BorderComponent;

public class MapSizeEvent extends GameEvent {

    private static final String StrMapSizeBorder = "_StrMapSizeBorder";

    private GameLayer layer;
    private float width;
    private float height;
    private int lineWidth;
    private Color color;
    private float x;
    private float y;
    private float groundHeight;

    public MapSizeEvent(GameLayer layer,
                        Color color,
                        int lineWidth,
                        float x, float y,
                        int width, int height, int groundHeight) {
        this.layer = layer;
        this.width = width;
        this.height = height;
        this.lineWidth = lineWidth;
        this.color = color;
        this.x = x;
        this.y = y;
        this.groundHeight = groundHeight;
    }

    @Override
    public void handle() {
        GameObject object = this.layer.getObjectAny(StrMapSizeBorder);
        if (object == null) {
            object = new GameObject(StrMapSizeBorder);
            object.transform.world.setToTranslation(this.x, this.y);

            object.addComponent(new BorderComponent(this.color, this.lineWidth, this.width, this.height));
            object.addComponent(new GroundProperty(this.width, this.groundHeight));

            this.layer.addObject(object);
        }
        else {
            BorderComponent c = object.getComponent(BorderComponent.class);
            c.color = this.color;
            c.height = this.height;
            c.width = this.width;
            c.lineWidth = this.lineWidth;
            object.transform.local.setToTranslation(this.x, this.y);

            GroundProperty g = object.getComponent(GroundProperty.class);
            g.width = this.width;
            g.height = this.groundHeight;
        }
    }
}
