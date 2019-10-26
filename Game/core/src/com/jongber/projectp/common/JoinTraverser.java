package com.jongber.projectp.common;

public interface JoinTraverser<ItemT> {
    void onJoin(ItemT main, ItemT target);
}
