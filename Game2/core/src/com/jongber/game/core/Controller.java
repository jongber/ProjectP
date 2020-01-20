package com.jongber.game.core;

import java.util.List;

public abstract class Controller {

    public abstract void build(List<GameObject> graph);

    public abstract void update(float delta);
}
