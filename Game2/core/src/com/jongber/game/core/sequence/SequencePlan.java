package com.jongber.game.core.sequence;

import com.jongber.game.core.util.Tuple2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

public class SequencePlan {
    private Queue<Tuple2<Float, GameSequence>> timePlans = new ArrayDeque<>();
    private HashMap<GameSequence, List<GameSequence>> linkedPlans = new HashMap<>();

    public void addLinkSeq(GameSequence from, List<GameSequence> next) {
        List<GameSequence> seqs = linkedPlans.get(from);
        if (seqs == null){
            linkedPlans.put(from, next);
        }
        else {
            seqs.addAll(next);
        }
    }

    public void addLinkSeq(GameSequence from, GameSequence next) {
        List<GameSequence> seqs = linkedPlans.get(from);
        if (seqs == null){
            seqs = new ArrayList<>();
            seqs.add(next);
            linkedPlans.put(from, seqs);
        }
        else {
            seqs.add(next);
        }
    }

    public void removeLinkSeq(GameSequence seq) {
        linkedPlans.remove(seq);
    }

    public void addTimeSeq(float time, GameSequence seq) {
        timePlans.add(new Tuple2<>(time, seq));
    }

    public boolean ended() {
        return this.timePlans.isEmpty() && this.linkedPlans.size() == 0;
    }

    public GameSequence getNext(float time) {

        Tuple2<Float, GameSequence> plan = timePlans.peek();
        if (plan == null || plan.getItem1() > time) {
            return null;
        }

        return timePlans.remove().getItem2();
    }

    public List<GameSequence> getNext(GameSequence ended) {
        return this.linkedPlans.get(ended);
    }
}
