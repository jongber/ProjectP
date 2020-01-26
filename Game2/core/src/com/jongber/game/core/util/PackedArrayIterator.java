package com.jongber.game.core.util;

import java.util.Iterator;

public class PackedArrayIterator<T> implements Iterator {

    private final T[] array;
    private final int length;
    private int index = -1;

    public PackedArrayIterator(T[] array, int length) {
        this.array = array;
        this.length = length;
    }

    @Override
    public boolean hasNext() {
        return this.index < this.length;
    }

    @Override
    public T next() {
        ++index;
        return array[index];
    }
}
