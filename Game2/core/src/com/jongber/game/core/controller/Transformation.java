package com.jongber.game.core.controller;

import com.jongber.game.core.GameObject;
import com.jongber.game.core.component.TransformComponent;

import java.util.List;

public class Transformation extends Controller implements Controller.Updater {

    private List<GameObject> graph = null;

    @Override
    public void build(List<GameObject> graph) {
        this.graph = graph;
    }

    @Override
    public void update(float elapsed) {
        for (GameObject node : this.graph) {
            GameObject parentNode = node.getParent();
            if (parentNode == null) {
                node.transform.world.set(node.transform.local);
            }
            else {
                TransformComponent transform = node.transform;
                TransformComponent parentTransform = parentNode.transform;

                transform.world.set(parentTransform.world);
                transform.world.mul(node.transform.local);
            }
        }
    }
}
