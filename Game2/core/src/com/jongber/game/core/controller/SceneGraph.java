package com.jongber.game.core.controller;

import com.jongber.game.core.GameObject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SceneGraph extends Controller {
    private List<GameObject> sorted = new ArrayList<>();

    @Override
    public void build(List<GameObject> objects) {
        this.sorted.clear();

        Queue<GameObject> travel = new ArrayDeque<>();
        for (GameObject node : objects) {
            travel.add(node);
        }

        while (travel.peek() != null) {
            GameObject node = travel.remove();
            this.sorted.add(node);

            for (Object object : node.getChildren()) {
                if (object instanceof GameObject) {
                    travel.add((GameObject) object);
                }
            }
        }
    }

    public List<GameObject> getGraph() {
        return this.sorted;
    }

}
