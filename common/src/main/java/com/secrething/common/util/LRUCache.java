package com.secrething.common.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by liuzz on 2018/6/23.
 */
public class LRUCache<K, V> {


    private final int maxSize;
    private final Lock lock = new ReentrantLock();
    private final LinkedHashMap<K, V> map = new LinkedHashMap<K, V>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > maxSize;
        }
    };

    public LRUCache(int maxSize) {
        this.maxSize = maxSize;
    }

    public V put(K key, V value) {
        return lockProxy(() -> map.put(key, value));
    }

    public V get(Object key) {
        return lockProxy(() -> map.get(key));
    }

    private V lockProxy(Handle<V> handle) {
        lock.lock();
        try {
            return handle.handle();
        } finally {
            lock.unlock();
        }
    }

    private interface Handle<V> {
        V handle();
    }
}
