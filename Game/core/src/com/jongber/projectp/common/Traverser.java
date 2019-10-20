package com.jongber.projectp.common;

public interface Traverser<ItemT> {
    void onTraverse(ItemT item);
}
