package com.jongber.game.core.sequence;

import com.jongber.game.core.util.Tuple2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class SequencePlan {

    private float totElapsed;
    private HashMap<GameSequence, List<Tuple2<Float, GameSequence>>> linkedPlans = new HashMap<>();
    private PriorityQueue<Tuple2<Float, GameSequence>> timePlan = new PriorityQueue<>(128, new SeqComparator());

    public void processEnd(GameSequence endedSeq) {
        List<Tuple2<Float, GameSequence>> linked = linkedPlans.remove(endedSeq);
        if (linked == null) return;

        for (Tuple2<Float, GameSequence> item : linked) {
            this.timePlan.add(new Tuple2<>(item.getItem1() + this.totElapsed, item.getItem2()));
        }
    }

    public void addLinkedSeq(GameSequence from, GameSequence next) {
        this.addLinkedSeq(from, next, 0.0f);
    }

    public void addLinkedSeq(GameSequence from, GameSequence next, float after) {
        List<Tuple2<Float, GameSequence>> linked = linkedPlans.get(from);
        if (linked == null) {
            linked = new ArrayList<>();
            linkedPlans.put(from, linked);
        }

        linked.add(new Tuple2<>(after, next));
    }

    public void addTimeSeq(float time, GameSequence seq) {
        timePlan.add(new Tuple2<>(time, seq));
    }

    public boolean ended() {
        return this.timePlan.isEmpty() && this.linkedPlans.size() == 0;
    }

    public GameSequence getNext(float time) {

        totElapsed = time;

        Tuple2<Float, GameSequence> plan = timePlan.peek();
        if (plan == null || plan.getItem1() > time) {
            return null;
        }

        return timePlan.remove().getItem2();
    }

    private class SeqComparator implements Comparator<Tuple2<Float, GameSequence>> {

        @Override
        public int compare(Tuple2<Float, GameSequence> lhs, Tuple2<Float, GameSequence> rhs) {
            if (lhs.getItem1() > rhs.getItem1()) {
                return 1;
            }
            else if (lhs.getItem1() > rhs.getItem1()) {
                return -1;
            }

            return 0;
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }
    }
}
