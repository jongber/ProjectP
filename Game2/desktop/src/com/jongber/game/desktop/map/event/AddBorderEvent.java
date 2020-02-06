package com.jongber.game.desktop.map.event;

import com.badlogic.gdx.graphics.Color;
import com.jongber.game.core.GameLayer;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.event.GameEvent;
import com.jongber.game.desktop.viewer.component.BorderComponent;

public class AddBorderEvent extends GameEvent {

    private GameLayer layer;
    private float width;
    private float height;
    private int lineWidth;
    private Color color;
    private float x;
    private float y;

    public AddBorderEvent(GameLayer layer, Color color, int lineWidth, float x, float y, int width, int height) {
        this.layer = layer;
        this.width = width;
        this.height = height;
        this.lineWidth = lineWidth;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    @Override
    public void handle() {
        GameObject object = new GameObject();
        object.transform.world.setToTranslation(this.x, this.y);

        object.addComponent(new BorderComponent(this.color, this.lineWidth, this.width, this.height));

        this.layer.addObject(object);
    }
}
