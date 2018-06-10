package com.secrething.common.util;

import com.google.common.cache.LoadingCache;

/**
 * Created by liuzz on 2018/4/10.
 */
public interface GuavaCacheBuilder<K, V> {

    LoadingCache<K, V> build();
}
