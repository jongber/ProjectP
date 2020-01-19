package com.jongber.game;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Engine {
    private Entity root = new Entity();
    private List<Entity> sortedList = new ArrayList<>();
    private List<Controller> controllers = new ArrayList<>();

    public Engine() {
        this.sortEntities();
    }

    public void update(float delta) {

    }

    private void sortEntities() {
        sortedList.clear();

        Queue<Entity> travel = new ArrayDeque<>();
        travel.add(this.root);

        while (travel.peek() != null) {
            Entity node = travel.remove();
            this.sortedList.add(node);

            travel.addAll(node.getChildren());
        }
    }
}
