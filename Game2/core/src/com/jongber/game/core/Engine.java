package com.jongber.game.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Engine implements Entity.EntityEventHandler {
    private Entity root = new Entity(this);
    private List<Entity> sortedList = new ArrayList<>();
    private List<Controller> controllers = new ArrayList<>();

    private boolean nodeChanged = true;

    public Engine() {
        this.buildGraph();
    }

    public void update(float delta) {
    }

    //// need optimization
    private void buildGraph() {
        if (this.nodeChanged == false) {
            return;
        }

        this.sortedList.clear();

        Queue<Entity> travel = new ArrayDeque<>();
        travel.add(this.root);

        while (travel.peek() != null) {
            Entity node = travel.remove();
            this.sortedList.add(node);

            travel.addAll(node.getChildren());
        }

        for (Controller controller : this.controllers) {
            controller.rebuild(this.sortedList);
        }

        this.nodeChanged = false;
    }

    @Override
    public void handleChanged() {
        this.nodeChanged = true;
    }
}
