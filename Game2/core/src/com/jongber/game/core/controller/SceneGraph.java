package com.jongber.game.core.controller;

import com.jongber.game.core.GameObject;
import com.jongber.game.core.util.PackedArray;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SceneGraph {
    private List<GameObject> sorted = new ArrayList<>();
    private Queue<GameObject> travel = new ArrayDeque<>(1024);

    public void build(PackedArray<GameObject> objects) {
        this.sorted.clear();
        this.travel.clear();

        for (GameObject node : objects) {
            travel.add(node);
        }

        while (travel.peek() != null) {
            GameObject node = travel.remove();
            this.sorted.add(node);

            for (int i = 0; i < node.getChildrenSize(); ++i) {
                travel.add(node.getChild(i));
            }
        }
    }

    public List<GameObject> getGraph() {
        return this.sorted;
    }
}
