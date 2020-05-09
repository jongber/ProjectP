package com.jongber.game.core.util;

public class Tuple4<T1, T2, T3, T4> {
    private T1 item1;
    private T2 item2;
    private T3 item3;
    private T4 item4;

    public Tuple4() { }

    public Tuple4(T1 item1, T2 item2, T3 item3, T4 item4) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
    }

    // do not create set method..for readonly trait..

    public T1 getItem1() { return this.item1; }

    public T2 getItem2() { return this.item2; }

    public T3 getItem3() { return this.item3; }

    public T4 getItem4() { return this.item4; }

    @Override
    public int hashCode() {
        return item1.hashCode() << 3 + item2.hashCode() << 2 + item3.hashCode() << 1 + item4.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;

        if (this == o) return true;

        if (this.hashCode() == o.hashCode())
            return true;

        return false;
    }
}
