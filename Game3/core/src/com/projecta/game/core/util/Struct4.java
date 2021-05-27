package com.projecta.game.core.util;

public class Struct4<T1, T2, T3, T4> {
    public T1 item1;
    public T2 item2;
    public T3 item3;
    public T4 item4;

    public Struct4() {
    }

    public Struct4(T1 item1, T2 item2, T3 item3, T4 item4) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
    }

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