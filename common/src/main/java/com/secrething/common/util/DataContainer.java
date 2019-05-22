package com.secrething.common.util;


import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.CacheBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by liuzengzeng on 2017/12/6.
 * 基于map实现的一种多叉树 数据结构
 * 提供线程安全和不安全两种方式
 * 多加了一种guava的失效方式
 * 示例:
 * <pre>
 * put(data,a,b,c)
 * put(data,a,x,c)
 * {
 *  "nodes":{
 *      "a":{
 *          "nodes":{
 *                  "b":{
 *                      "nodes":{
 *                              "c":{
 *                                  "data":"data"
 *                               }
 *                      }
 *                  },
 *                  "x":{
 *                      "nodes":{
 *                              "c":{
 *                                  "data":"data"
 *                               }
 *                      }
 *                  }
 *          }
 *      }
 *  }
 * }
 * </pre>
 */
public class DataContainer<K, D> {
    public static final byte NOMAL = 1;
    public static final byte CONCURRENT = 2;
    public static final byte GUAVA = 3;
    private final Map<K, DataContainer<K, D>> children;
    private final transient GuavaCacheBuilder<K, DataContainer<K, D>> cacheBuilder;
    private final transient byte type;
    private final transient Sync sync;
    private D data;

    private DataContainer(byte type, D data, GuavaCacheBuilder<K, DataContainer<K, D>> cacheBuilder) {
        MapAndSync<K, D> mapAndSync = new MapAndSync(type, cacheBuilder).invoke();
        this.children = mapAndSync.getMap();
        this.sync = mapAndSync.getSync();
        this.cacheBuilder = cacheBuilder;
        this.type = type;
        this.data = data;

    }

    private DataContainer() {
        this(NOMAL, null, null);

    }


    private DataContainer(byte type) {
        this(type, null, null);

    }

    private final static boolean isSupport(byte type, CacheBuilder cacheBuilder) {
        return NOMAL == type || CONCURRENT == type || (GUAVA == type && null != cacheBuilder);
    }

    public final static <K, D> DataContainer<K, D> createInstance() {

        return new DataContainer<K, D>(NOMAL, null, null);
    }

    public final static <K, D> DataContainer<K, D> createConcurrentInstance() {
        return new DataContainer<K, D>(CONCURRENT, null, null);
    }

    public final static <K, D> DataContainer<K, D> createAutoExpireInstance(GuavaCacheBuilder<K, DataContainer<K, D>> cacheBuilder) {
        return new DataContainer<K, D>(GUAVA, null, cacheBuilder);
    }

    public final static <K, D> DataContainer<K, D> createAutoExpireInstance(long maxSize, long expireAfterWrite, TimeUnit afterWriteUnit, long expireAfterAccess, TimeUnit afterAccessUnit) {
        GuavaCacheBuilder<K, DataContainer<K, D>> builder = GuavaCacheFactory.generatorBuilder(maxSize, expireAfterWrite, afterWriteUnit, expireAfterAccess, afterAccessUnit);
        return new DataContainer<K, D>(GUAVA, null, builder);
    }

    static Sync lock() {
        return new RealLock();
    }

    static Sync unlock() {
        return new Sync() {
            @Override
            public void locakRead() {

            }

            @Override
            public void unlockRead() {

            }

            @Override
            public void lockWrite() {

            }

            @Override
            public void unlockWrite() {

            }
        };
    }

    public static void main(String[] args) {
        DataContainer<String, String> dataContainer = new DataContainer<>();
        dataContainer.put("1", "a", "b", "a");
        dataContainer.put("1", "a", "b", "c");
        dataContainer.put("1", "a", "b", "d");
        dataContainer.put("1", "a", "c", "e");
        dataContainer.put("1", "a", "c", "e");
        String s = JSONObject.toJSONString(dataContainer);
        System.out.println(s);
        DataContainer dataConta = JSONObject.parseObject(s, DataContainer.class);
        System.out.println(dataConta);
    }

    private final DataContainer<K, D> createChild(D data) {
        DataContainer<K, D> node = new DataContainer<>(this.type, data, this.cacheBuilder);
        return node;
    }

    private final DataContainer<K, D> createParent() {
        return new DataContainer<K, D>(this.type, null, this.cacheBuilder);
    }

    public final D getData() {
        return lockRead(() -> data);

    }

    public void setData(D data) {
        this.data = data;
    }

    <T> T lockRead(Handler<T> handler) {
        sync.locakRead();
        try {
            return handler.readOrWrite();
        } finally {
            sync.unlockRead();
        }
    }

    <T> T lockWrite(Handler<T> handler) {
        sync.lockWrite();
        try {
            return handler.readOrWrite();
        } finally {
            sync.unlockWrite();
        }
    }

    public final Map<K, DataContainer<K, D>> getNodes() {
        return lockRead(() -> children);
    }

    public final DataContainer<K, D> getNode(final Object... keys) {
        final DataContainer<K, D> root = this;
        if (keysCantGet(keys))
            return null;
        if (keys.length == 1)
            return lockRead(() -> {
                DataContainer<K, D> chd = children.get(keys[0]);
                if (null == chd)
                    return root.copy();
                return chd;
            });
        Object key = keys[keys.length - 1];
        Object[] parentKeys = Arrays.copyOf(keys, keys.length - 1);
        return getNodeSplitKey(key, parentKeys);

    }

    public final D getNodeData(Object... keys) {
        if (keysCantGet(keys))
            return null;
        if (keys.length == 1)
            return getNodeDataSplitKey(keys[0]);
        int newlen = keys.length - 1;
        Object key = selectKey(newlen, keys);
        Object[] parentKeys = selectParentKeys(newlen, keys);
        return getNodeDataSplitKey(key, parentKeys);

    }

    public final void put(D data, K... keys) {
        if (keysCantPut(keys))
            return;
        if (keys.length == 1) {
            putSplitKey(keys[0], data);
            return;
        }
        int newlen = keys.length - 1;
        K key = (K)selectKey(newlen, keys);
        K[] parentKeys = (K[]) selectParentKeys(newlen, keys);
        putSplitKey(key, data, parentKeys);

    }

    public final void putNode(final DataContainer<K, D> node, K... keys) {
        if (keysCantPut(keys))
            return;
        if (keys.length == 1) {
            this.getNodes().put(keys[0], node);
        } else {
            int newlen = keys.length - 1;
            final K key = (K)selectKey(newlen, keys);
            final K[] parentKeys = (K[]) selectParentKeys(newlen, keys);
            lockWrite(new Handler<Void>() {
                @Override
                public Void readOrWrite() {
                    putBase(key, node, parentKeys);
                    return null;
                }
            });
        }


    }

    private Object selectKey(int index, Object... keys) {
        return keys[index];
    }

    private Object[] selectParentKeys(int len, Object... keys) {
        Object[] parentKeys = new Object[len];
        System.arraycopy(keys, 0, parentKeys, 0, len);
        return parentKeys;
    }

    public final DataContainer<K, D> remove(final Object... keys) {
        if (null == keys || keys.length < 1 || null == children)
            return null;
        else if (keys.length == 1) {
            return lockWrite(() -> children.remove(keys[0]));
        }
        Object[] parent = selectParentKeys(keys.length - 1, keys);
        final DataContainer<K, D> node = getNode(parent);
        return lockWrite(() -> node == null ? null : node.children.remove(keys[keys.length - 1]));

    }

    private void putBase(K key, DataContainer<K, D> node, K... parentKeys) {
        if (null == key || null == node)
            throw new NullPointerException("key or node can't be null");

        DataContainer<K, D> next = this;
        if (null != parentKeys && parentKeys.length > 0) {
            for (int i = 0; i < parentKeys.length; i++) {
                K parentKey = parentKeys[i];
                DataContainer<K, D> curr = next.children.get(parentKey);
                if (null == curr) {
                    curr = next.createParent();
                    next.children.put(parentKey, curr);
                }
                next = curr;
            }
        }
        next.children.put(key, node);
    }

    private boolean hasChildren() {
        return lockRead(() -> null != children && !children.isEmpty());
    }

    /**
     * 查找最后一个节点,返回最后一个叶子节点
     *
     * @param parentKeys
     * @return Node
     */

    private DataContainer<K, D> findNode(Object... parentKeys) {
        if (!hasChildren())
            return this;
        DataContainer<K, D> next = this;
        for (int i = 0; i < parentKeys.length; i++) {
            if (next.hasChildren()) {
                next = next.children.get(parentKeys[i]);
                if (null == next)
                    return null;
            }
            if (i == (parentKeys.length - 1) || !next.hasChildren()) {
                return next;
            }
        }
        return null;

    }

    private void putSplitKey(final K key, final D data, final K... parentKeys) {
        final DataContainer<K, D> curr = this;
        lockWrite((Handler<Void>) () -> {
            DataContainer<K, D> node = curr.createChild(data);
            putBase(key, node, parentKeys);
            return null;
        });
    }

    public DataContainer<K, D> copy() {
        return new DataContainer<>(this.type, this.data, this.cacheBuilder);
    }

    private DataContainer<K, D> getNodeSplitKey(final Object key, final Object... parentKeys) {
        final DataContainer<K, D> root = this;
        return lockRead(() -> {
            if (null != parentKeys && parentKeys.length > 0) {
                DataContainer<K, D> node = findNode(parentKeys); //循环方式
                if (null == node || null == node.children)
                    return root.copy();
                DataContainer<K, D> chd = node.children.get(key);
                if (null == chd)
                    return root.copy();
                return chd;
            }
            DataContainer<K, D> chd = children.get(key);
            if (null == chd)
                return root.copy();
            return chd;
        });
    }

    private D getNodeDataSplitKey(final Object key, final Object... parentKeys) {
        return lockRead(() -> {
            DataContainer<K, D> node = getNodeSplitKey(key, parentKeys);
            if (null == node)
                return null;
            return node.getData();
        });
    }

    private boolean keysCantPut(Object... keys) {
        return null == keys || keys.length < 1;
    }

    private boolean keysCantGet(Object... keys) {
        return keysCantPut(keys) || null == this.children;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    static interface Handler<T> {
        T readOrWrite();
    }

    static interface Sync {
        void locakRead();

        void unlockRead();

        void lockWrite();

        void unlockWrite();
    }

    private static class RealLock extends ReentrantReadWriteLock implements Sync {
        ReadLock readLock = readLock();
        WriteLock writeLock = writeLock();

        @Override
        public void locakRead() {
            readLock.lock();
        }

        @Override
        public void unlockRead() {
            readLock.unlock();
        }

        @Override
        public void lockWrite() {
            writeLock.lock();
        }

        @Override
        public void unlockWrite() {
            writeLock.unlock();
        }
    }

    private static class MapAndSync<K, D> {
        private byte type;
        private GuavaCacheBuilder<K, DataContainer<K, D>> cacheBuilder;
        private Map<K, DataContainer<K, D>> map;
        private Sync sync;

        public MapAndSync(byte type, GuavaCacheBuilder<K, DataContainer<K, D>> cacheBuilder) {
            this.type = type;
            this.cacheBuilder = cacheBuilder;
        }

        public Map<K, DataContainer<K, D>> getMap() {
            return map;
        }

        public Sync getSync() {
            return sync;
        }

        public MapAndSync<K, D> invoke() {
            switch (type) {
                case GUAVA: {
                    Assert.notNull(cacheBuilder, "heihei not support");
                    map = cacheBuilder.build().asMap();
                    sync = lock();
                    break;
                }
                case NOMAL: {
                    Assert.isTrue(cacheBuilder == null, "heihei not support");
                    map = new HashMap<>();
                    sync = unlock();
                    break;
                }
                case CONCURRENT: {
                    Assert.isTrue(cacheBuilder == null, "heihei not support");
                    map = new HashMap<>();
                    sync = lock();
                    break;
                }
                default: {
                    throw new IllegalArgumentException("heihei type not support");
                }
            }
            return this;
        }
    }
}
