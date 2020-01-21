package com.jongber.game.core.controller;

import com.jongber.game.core.Component;
import com.jongber.game.core.GameObject;
import com.jongber.game.core.component.TextureComponent;

import java.util.ArrayList;
import java.util.List;

public class TextureRenderer {

    private List<GameObject> list = new ArrayList<>();

    public void build(List<GameObject> graph) {
        this.list.clear();

        for (GameObject node : graph) {
             Component component = node.getComponent(TextureComponent.class);
             if (component instanceof TextureComponent) {
                 list.add(node);
             }
        }
    }
}
