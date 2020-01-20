package com.jongber.game.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class SceneGraph {
    private GameObject root;
    private List<GameObject> sorted = new ArrayList<>();

    public SceneGraph(GameObject root) {
        this.root = root;
    }

    public void addObject(GameObject obj) {
        this.root.addChild(obj);
    }

    public void removeObject(GameObject obj) {
            this.root.removeChild(obj);
        }

    public List<GameObject> build() {
        this.sorted.clear();

        Queue<GameObject> travel = new ArrayDeque<>();
        travel.add(this.root);

        while (travel.peek() != null) {
            GameObject node = travel.remove();
            this.sorted.add(node);

            travel.addAll(Arrays.asList(node.getChildren()));
        }

        return this.sorted;
    }

    public List<GameObject> getGraph() {
        return this.sorted;
    }

    public GameObject getRoot() {
        return this.root;
    }
}
