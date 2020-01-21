package com.jongber.game.core.util;

import java.util.HashMap;
import java.util.Map;

public class PackedArray<T> {
    private T[] items;
    private Map<T, Integer> indexMap = new HashMap<>();
    private int tailIndex = -1;


    public PackedArray() {
        this.items = (T[])new Object[128];
    }

    public T[] getArray() {
        return this.items;
    }

    public void add(T item) {
        this.tailIndex++;
        if (this.tailIndex >= this.items.length) {
            T[] newArr = (T[])new Object[this.items.length * 2];
            System.arraycopy(this.items, 0, newArr, 0, this.items.length);
            this.items = newArr;
        }

        this.items[this.tailIndex] = item;
        this.indexMap.put(item, this.tailIndex);
    }

    public void remove(T item) {
        Integer index = this.indexMap.get(item);
        if (index == null) {
            return;
        }

        this.remove(index);
    }

    public void remove(int index) {
        if (this.tailIndex < index) {
            return;
        }
        else if (this.tailIndex == index) {
            this.indexMap.remove(this.items[index]);
            this.items[index] = null;
            this.tailIndex--;
        }
        else {
            this.indexMap.remove(this.items[index]);
            T item = this.items[this.tailIndex];
            this.items[index] = item;
            this.indexMap.put(item, index);
            this.items[this.tailIndex] = null;
            this.tailIndex --;
        }
    }

    public void clearAll() {
        this.indexMap.clear();
        this.items = (T[]) new Object[128];
    }

    public boolean isContained(Object object) {
        return this.indexMap.containsKey(object);
    }

    public int size() {
        return this.tailIndex + 1;
    }
}