package com.jongber.game.core.util;

import com.badlogic.gdx.utils.reflect.ArrayReflection;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("unchecked")
public class PackedArray<T> implements Iterable<T>{
    private T[] items;
    private Map<T, Integer> indexMap = new HashMap<>();
    private int tailIndex = -1;

    public PackedArray() {
        this.items =  (T[])new Object[128];
    }

    public void add(T item) {
        this.tailIndex++;
        if (this.tailIndex >= this.items.length) {
            Object[] newArr = new Object[this.items.length * 2];
            System.arraycopy(this.items, 0, newArr, 0, this.items.length);
            this.items = (T[])newArr;
        }

        this.items[this.tailIndex] = item;
        this.indexMap.put(item, this.tailIndex);
    }

    public boolean remove(T item) {
        Integer index = this.indexMap.get(item);
        if (index == null) {
            return false;
        }

        return this.remove(index);
    }

    private boolean remove(int index) {
        if (this.tailIndex == index) {
            this.indexMap.remove(this.items[index]);
            this.items[index] = null;
            this.tailIndex--;
        }
        else if (this.tailIndex > index) {
            this.indexMap.remove(this.items[index]);
            T item = this.items[this.tailIndex];
            this.items[index] = item;
            this.indexMap.put(item, index);
            this.items[this.tailIndex] = null;
            this.tailIndex --;
        }
        else {
            return false;
        }

        return true;
    }

    public void clearAll() {
        this.indexMap.clear();
        this.items = (T[])new Object[128];
        this.tailIndex = -1;
    }

    public T get(int index) {

        if (index > size()) {
            throw new IndexOutOfBoundsException("PackedArray index:" + index + " size:" + size());
        }

        return this.items[index];
    }

    public T[] toArray (Class type) {
        T[] result = (T[]) ArrayReflection.newInstance(type, size());
        System.arraycopy(items, 0, result, 0, size());
        return result;
    }

    public int size() {
        return this.tailIndex + 1;
    }

    @Override
    @NotNull
    public Iterator<T> iterator() {
        return new PackedArrayIterator<T>(this.items, this.size());
    }

}