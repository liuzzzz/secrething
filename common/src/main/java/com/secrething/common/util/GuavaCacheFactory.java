package com.secrething.common.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuzz on 2018/4/6.
 */
public final class GuavaCacheFactory {
    private GuavaCacheFactory() {
        throw new UnsupportedOperationException("can not be created");
    }

    public static <K, V> LoadingCache<K, V> cacheInstance(long maxSize, long expireAfterWrite, TimeUnit afterWriteUnit, long expireAfterAccess, TimeUnit afterAccessUnit, CacheLoader<K, V> loader) {

        GuavaCacheBuilder builder = generatorBuilder(maxSize, expireAfterWrite, afterWriteUnit, expireAfterAccess, afterAccessUnit, loader);
        return builder.build();
    }
    public static <K, V> ConcurrentMap<K, V> mapInstance(long maxSize, long expireAfterWrite, TimeUnit afterWriteUnit, long expireAfterAccess, TimeUnit afterAccessUnit, CacheLoader<K, V> loader) {

        GuavaCacheBuilder builder = generatorBuilder(maxSize, expireAfterWrite, afterWriteUnit, expireAfterAccess, afterAccessUnit, loader);
        return builder.build().asMap();
    }

    public static <K, V> LoadingCache<K, V> cacheInstance(CacheBuilder builder, CacheLoader<K, V> loader) {
        if (null == loader)
            loader = new CacheLoader<K, V>() {
                @Override
                public V load(K key) throws Exception {
                    return null;
                }
            };
        return builder.build(loader);
    }
    public static <K, V> ConcurrentMap<K, V> mapInstance(CacheBuilder builder, CacheLoader<K, V> loader) {
        if (null == loader)
            loader = new CacheLoader<K, V>() {
                @Override
                public V load(K key) throws Exception {
                    return null;
                }
            };
        return builder.build(loader).asMap();
    }

    public static <K, V> LoadingCache<K, V> cacheInstance(long maxSize, long expireAfterWrite, TimeUnit afterWriteUnit, long expireAfterAccess, TimeUnit afterAccessUnit) {
        return cacheInstance(maxSize, expireAfterWrite, afterWriteUnit, expireAfterAccess, afterAccessUnit, null);
    }
    public static <K, V> ConcurrentMap<K, V> mapInstance(long maxSize, long expireAfterWrite, TimeUnit afterWriteUnit, long expireAfterAccess, TimeUnit afterAccessUnit) {
        return (ConcurrentMap<K, V>)cacheInstance(maxSize, expireAfterWrite, afterWriteUnit, expireAfterAccess, afterAccessUnit, null).asMap();
    }

    private static <K, V> CacheBuilder<K, V> generatorBuilder0(long maxSize, long expireAfterWrite, TimeUnit afterWriteUnit, long expireAfterAccess, TimeUnit afterAccessUnit) {
        CacheBuilder builder = CacheBuilder.newBuilder();
        if (maxSize < 0)
            maxSize = 1000;
        builder.maximumSize(maxSize);
        if (expireAfterWrite >= 0 && null != afterWriteUnit)
            builder.expireAfterWrite(expireAfterWrite, afterWriteUnit);
        if (expireAfterAccess >= 0 && null != afterAccessUnit)
            builder.expireAfterAccess(expireAfterAccess, afterAccessUnit);
        return builder;
    }

    public static <K, V> LoadingCache<K, V> cacheInstance(long maxSize, long expireAfterWrite, TimeUnit afterWriteUnit, CacheLoader<K, V> loader) {
        Assert.isTrue(expireAfterWrite >= 0);
        Assert.notNull(afterWriteUnit);
        return cacheInstance(maxSize, expireAfterWrite, afterWriteUnit, -1, null, loader);
    }

    public static <K, V> LoadingCache<K, V> cacheInstance(long maxSize, long expireAfterWrite, TimeUnit afterWriteUnit) {
        Assert.isTrue(expireAfterWrite >= 0);
        Assert.notNull(afterWriteUnit);
        return cacheInstance(maxSize, expireAfterWrite, afterWriteUnit, null);
    }

    public static <K, V> GuavaCacheBuilder<K, V> generatorBuilder(CacheBuilder<K, V> builder, CacheLoader<K, V> loader) {
        return new Builder<>(builder, loader);
    }

    public static <K, V> GuavaCacheBuilder<K, V> generatorBuilder(CacheBuilder<K, V> builder) {
        return new Builder<>(builder, null);
    }

    public static <K, V> GuavaCacheBuilder<K, V> generatorBuilder(long maxSize, long expireAfterWrite, TimeUnit afterWriteUnit, long expireAfterAccess, TimeUnit afterAccessUnit, CacheLoader<K, V> loader) {
        if (null == loader)
            loader = new CacheLoader<K, V>() {
                @Override
                public V load(K key) throws Exception {
                    return null;
                }
            };
        CacheBuilder builder = generatorBuilder0(maxSize, expireAfterWrite, afterWriteUnit, expireAfterAccess, afterAccessUnit);
        return new Builder<>(builder, loader);
    }

    public static <K, V> GuavaCacheBuilder<K, V> generatorBuilder(long maxSize, long expireAfterWrite, TimeUnit afterWriteUnit, long expireAfterAccess, TimeUnit afterAccessUnit) {
        return generatorBuilder(maxSize, expireAfterWrite, afterWriteUnit, expireAfterAccess, afterAccessUnit, null);
    }

    private static class Builder<K, V> implements GuavaCacheBuilder<K, V> {
        final CacheLoader<K, V> loader;
        final CacheBuilder<K, V> builder;

        Builder(CacheBuilder<K, V> builder, CacheLoader<K, V> loader) {
            Assert.notNull(builder);
            this.loader = loader;
            this.builder = builder;
        }

        @Override
        public LoadingCache<K, V> build() {
            return cacheInstance(builder, loader);
        }
    }
}
