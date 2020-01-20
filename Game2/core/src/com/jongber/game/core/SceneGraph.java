package com.jongber.game.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class SceneGraph {
    private List<GameObject> sorted = new ArrayList<>();

    public List<GameObject> build(GameObject root) {
        this.sorted.clear();

        Queue<GameObject> travel = new ArrayDeque<>();
        travel.add(root);

        while (travel.peek() != null) {
            GameObject node = travel.remove();
            this.sorted.add(node);

            travel.addAll(Arrays.asList(node.getChildren()));
        }

        return this.sorted;
    }
}
