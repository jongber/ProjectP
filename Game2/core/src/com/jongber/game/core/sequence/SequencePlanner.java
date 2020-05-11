package com.jongber.game.core.sequence;

import com.jongber.game.core.GameLayer;
import com.jongber.game.core.util.Tuple2;

import java.util.ArrayDeque;
import java.util.Queue;

public class SequencePlanner {

    GameLayer layer;

    public SequencePlanner(GameLayer layer) {
        this.layer = layer;
    }

    public Queue<Tuple2<Float, GameSequence>> getPlan() {
        return new ArrayDeque<>();
    }
}
