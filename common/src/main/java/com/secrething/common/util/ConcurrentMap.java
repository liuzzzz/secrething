package com.secrething.common.util;

import java.util.function.Function;

/**
 * Created by Idroton on 2018/9/2 20:48.
 */
public interface ConcurrentMap<K, V> extends java.util.concurrent.ConcurrentMap<K, V> {
    /**
     * 获取一个Value
     * 如果没有就new一个,如果有就拿出来
     * 名字太难起了
     * @param key
     * @param func 可创建一个V的函数
     * @return
     */
    default V putIfAbsent(K key, Function<?, V> func) {
        V v = get(key);
        if (null != v)
            return v;
        V n = func.apply(null);
        V o = putIfAbsent(key, n);
        if (null == o)
            return n;
        return o;
    }
}
