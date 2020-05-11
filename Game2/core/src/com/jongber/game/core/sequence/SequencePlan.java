package com.jongber.game.core.sequence;

import com.jongber.game.core.util.Tuple2;

import java.util.ArrayDeque;
import java.util.Queue;

public class SequencePlan {
    public Queue<Tuple2<Float, GameSequence>> plans = new ArrayDeque<>();

    public boolean ended() {
        return this.plans.isEmpty();
    }

    public GameSequence getNext(float time) {

        Tuple2<Float, GameSequence> plan = plans.peek();
        if (plan == null || plan.getItem1() > time) {
            return null;
        }

        return plans.remove().getItem2();
    }
}
