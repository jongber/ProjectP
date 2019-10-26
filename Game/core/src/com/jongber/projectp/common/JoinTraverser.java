package com.jongber.projectp.common;

public interface JoinTraverser<ItemT> {
    void onJoin(ItemT target1, ItemT target2);
}
