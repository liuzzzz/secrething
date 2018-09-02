package com.secrething.common.util;

import sun.misc.Unsafe;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * @author liuzengzeng
 * @create 2018/1/13
 * This is no difference from using lock 😣
 */
@Deprecated
public final class ConcurrentNoLockMap<K, V> {
    static  Adapter adapter;
    private static final int GET = 0x1;
    private static final int PUT = 0x2;
    private static final int REM = 0x3;
    private static final long ABASE;
    private static final int ASHIFT;
    private static Node[] objects = new Node[5];
    private static Unsafe U = UnsafeInstance.getUnsafe();

    static {
        //adapter = new Adapter();
        //adapter.start();
    }

    static {
        Class<?> ak = Node[].class;
        ABASE = U.arrayBaseOffset(ak);
        int scale = U.arrayIndexScale(ak);
        if ((scale & (scale - 1)) != 0)
            throw new Error("data type scale not a power of two");
        ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
    }

    final HashMap<K, V> map;

    public ConcurrentNoLockMap() {
        this.map = new HashMap<>();
    }

    static final <K, V> boolean casTabAt(Node<K, V>[] tab, int i,
                                         Node<K, V> c, Node<K, V> v) {
        return U.compareAndSwapObject(tab, ((long) i << ASHIFT) + ABASE, c, v);
    }

    public static void main(String[] args) {
        casTabAt(objects,1,new Node<>("hi",null,null,0),new Node<>("hello",null,null,0));
        System.out.println(objects);
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

    static class Node<K, V> {
        final Thread invoker = Thread.currentThread();
        K key;
        V v;
        HashMap<K, V> map;
        int operation;
        volatile boolean completed = false;

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
