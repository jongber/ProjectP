package com.jongber.game.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class SceneGraph implements GameObject.Callback{
    private boolean modified = true;
    private GameObject root;
    private List<GameObject> sorted = new ArrayList<>();

    public SceneGraph() {
        this.root = new GameObject(this);
    }

    public void addObject(GameObject obj) {
        this.root.addChild(obj);
    }

    public void removeObject(GameObject obj) {
            this.root.removeChild(obj);
        }

    public boolean build() {
        if (this.modified == false)
            return false;

        this.sorted.clear();

        Queue<GameObject> travel = new ArrayDeque<>();
        travel.add(this.root);

        while (travel.peek() != null) {
            GameObject node = travel.remove();
            this.sorted.add(node);

            for (Object object : node.getChildren()) {
                if (object instanceof GameObject) {
                    travel.add((GameObject) object);
                }
            }
        }

        this.modified = true;

        return true;
    }

    public List<GameObject> getGraph() {
        return this.sorted;
    }

    public GameObject getRoot() {
        return this.root;
    }

    @Override
    public void modified(GameObject object) {
        this.modified = true;
    }
}
