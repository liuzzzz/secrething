package com.secrething.common.util;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @author liuzengzeng
 * @create 2018/1/13
 * This is no difference from using lock 😣
 */
@Deprecated
public final class ConcurrentNoLockMap<K, V> {
    private static final int GET = 0x1;
    private static final int PUT = 0x2;
    private static final int REM = 0x3;
    final HashMap<K, V> map;
    static final Adapter adapter;

    static {
        adapter = new Adapter();
        adapter.start();
    }

    public ConcurrentNoLockMap() {
        this.map = new HashMap<>();
    }

    static class Node<K, V> {
        K key;
        V v;
        HashMap<K, V> map;
        int operation;
        volatile boolean completed = false;
        final Thread invoker = Thread.currentThread();

        public Node(K key, V v, HashMap<K, V> map, int operation) {
            this.key = key;
            this.v = v;
            this.map = map;
            this.operation = operation;
        }

        boolean isCompleted() {
            return completed;
        }

        Node<K, V> handle() {
            switch (operation) {
                case GET:
                    this.v = map.get(key);
                    break;
                case PUT:
                    this.v = map.put(key, v);
                    break;
                case REM:
                    this.v = map.remove(key);
                    break;
                default:
                    throw new UnsupportedOperationException("unsupport operation");
            }
            this.completed = true;
            LockSupport.unpark(invoker);
            return this;
        }
    }

    public V get(Object key) {
        return operateByKey(key, GET);
    }

    private V operateByKey(Object key, int operation) {
        if (null == key)
            throw new NullPointerException("key can not be null");
        K k = null;
        try {
            k = (K) key;
        } catch (Exception e) {
            throw e;
        }
        Node<K, V> node = new Node<>(k, null, this.map, operation);
        adapter.queue.offer(node);
        while (!node.isCompleted())
            ParkUtil.park();
        return node.v;
    }

    public V put(K key, V val) {
        if (null == key)
            throw new NullPointerException("key can not be null");
        Node<K, V> node = new Node<>(key, null, this.map, PUT);
        adapter.queue.offer(node);
        while (!node.isCompleted())
            ParkUtil.park();
        return node.v;
    }

    public V remove(Object key) {
        return operateByKey(key, REM);
    }

    @Override
    protected void finalize() throws Throwable {
        if (null != adapter) {
            adapter.shut();
        }

    }

    static class Adapter extends Thread {
        volatile boolean run = true;
        ConcurrentLinkedQueue<Node> queue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            while (isRun()) {
                Node node = queue.poll();
                for (; null == node; node = queue.poll()) {
                    ParkUtil.park();
                }
                node.handle();
            }
        }

        boolean isRun() {
            return run;
        }

        void shut() {
            this.run = false;
            LockSupport.unpark(this);
        }
    }
}
