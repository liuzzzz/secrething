package com.secrething.adrift.search.core;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Created by liuzz on 2018-12-08 18:01.
 */
public class Holder<T> {

    private static final AtomicReferenceFieldUpdater<Holder, Object> updater = AtomicReferenceFieldUpdater.newUpdater(Holder.class, Object.class, "value");
    private volatile Object value;

    public T getValue() {
        return (T) value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean compareAndSet(T oldValue, T newValue) {
        return updater.compareAndSet(this, oldValue, newValue);
    }
}
