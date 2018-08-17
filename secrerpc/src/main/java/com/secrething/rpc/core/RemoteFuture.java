package com.secrething.rpc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by liuzengzeng on 2017/12/18.
 */
public class RemoteFuture extends AbstractFuture<RemoteResponse> {
    private static final Logger logger = LoggerFactory.getLogger(RemoteFuture.class);
    private final RemoteRequest remoteRequest;
    private final Sync sync;
    private final long beginTime = System.currentTimeMillis();
    private final long timeLimit = 5000;
    private RemoteResponse remoteResponse;

    public RemoteFuture(RemoteRequest request) {
        this.remoteRequest = request;
        this.sync = new Sync();
    }

    public RemoteRequest getRemoteRequest() {
        return remoteRequest;
    }

    public long getBeginTime() {
        return beginTime;
    }

    @Override
    public boolean isDone() {
        return sync.isDone();
    }

    @Override
    public RemoteResponse get() throws InterruptedException, ExecutionException {
        sync.acquire(-1);
        return remoteResponse;
    }

    @Override
    public RemoteResponse get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        boolean success = sync.tryAcquireNanos(-1, unit.toNanos(timeout));
        if (success) {
            return remoteResponse;
        } else {
            throw new RuntimeException("Timeout exception. Request id: " + this.remoteRequest.getRequestId()
                    + ". Request class name: " + this.remoteRequest.getBeanName()
                    + ". Request method: " + this.remoteRequest.getMethodName());
        }
    }

    public void done(RemoteResponse remoteResponse) {
        this.remoteResponse = remoteResponse;
        sync.release(1);
        long responseTime = System.currentTimeMillis() - beginTime;
        if (responseTime > this.timeLimit) {
            logger.warn("Service response time is too slow. Request id = " + remoteResponse.getRequestId() + ". Response Time = " + responseTime + "ms");
        }
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
                if (compareAndSetState(PENDING, DONE))
                    return true;
            return false;
        }

        private boolean isDone() {
            return getState() == DONE;
        }
    }
}
