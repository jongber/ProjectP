package com.jongber.game.core.util;

public class Tuple2<T1, T2> {
    private T1 item1;
    private T2 item2;

    public Tuple2() {
    }

    public Tuple2(T1 item1, T2 item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    public T1 getItem1() { return this.item1; }

    public T2 getItem2() { return this.item2; }
}
