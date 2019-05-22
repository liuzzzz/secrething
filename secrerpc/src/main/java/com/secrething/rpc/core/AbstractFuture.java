package com.secrething.rpc.core;

import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by liuzengzeng on 2017/12/20.
 */
public abstract class AbstractFuture<T> implements Future<T> {

    private final Sync sync;
    private final long beginTime = System.currentTimeMillis();

    private T response;

    public AbstractFuture() {
        this.sync = new Sync();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCancelled() {
        throw new UnsupportedOperationException();
    }

    private static final class Sync extends AbstractQueuedSynchronizer {
        private static final int DONE = 1;
        private static final int PENDING = 0;

        @Override
        protected boolean tryAcquire(int arg) {
            return getState() == DONE;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (PENDING == getState())
                setState(DONE);
            return true;
        }

        private boolean isDone() {
            return getState() == DONE;
        }
    }

    public long getBeginTime() {
        return beginTime;
    }

    @Override
    public boolean isDone() {
        return sync.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        sync.acquire(-1);
        return response;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        sync.tryAcquireNanos(-1, unit.toNanos(timeout));
        return response;

    }

    public void done(T remoteResponse) {
        this.response = remoteResponse;
        sync.release(1);
    }
}
