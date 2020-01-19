package com.jongber.game.core;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneGraph {

    public class Node {
        public Node parent = null;
        public PackedArray children = new PackedArray();
        public GameObject item;

        public Node() {}

        public Node(Node parent, GameObject object) {
            this.parent = parent;
            this.item = object;
        }

        public Node addChild(GameObject item) {
            Node child = new Node(this, item);
            this.children.add(child);
            return child;
        }

        public void delete() {
            if (this.parent == null) {
                return;
            }

            this.parent.children.remove(this);
        }
    }

    private Node root = new Node();
    private Map<GameObject, Node> nodeCache = new HashMap<>();
    private List<GameObject> traversed = new ArrayList<>();

    public void addObject(GameObject object) {
        this.addObject(null, object);
    }

    public void addObject(GameObject parent, GameObject object) {
        Node childNode;
        if (parent == null) {
            childNode = this.root.addChild(object);
        }
        else {
            Node node = this.findNode(parent);
            if (node == null) {
                Gdx.app.error("SceneGraph", "why found node null?");
                return;
            }

            childNode = node.addChild(object);
        }

        this.nodeCache.put(object, childNode);
    }

    public void removeObject(GameObject object) {
        Node node = this.nodeCache.remove(object);
        if (node == null) {
            Gdx.app.error("SceneGraph", "why node cache is null?");
            return;
        }

        node.delete();
    }



    private Node findNode(GameObject object) {
        Node node = nodeCache.get(object);
        if (node == null) {
            return this.root;
        }

        return node;
    }
}


















