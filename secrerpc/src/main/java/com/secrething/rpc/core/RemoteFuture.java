package com.secrething.rpc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by liuzengzeng on 2017/12/18.
 */
public class RemoteFuture extends AbstractFuture<RemoteResponse> {
    private static final Logger logger = LoggerFactory.getLogger(RemoteFuture.class);
    private final RemoteRequest remoteRequest;
    private final long timeLimit = 5000;
    public RemoteFuture(RemoteRequest request) {
        super();
        this.remoteRequest = request;
    }

    public RemoteRequest getRemoteRequest() {
        return remoteRequest;
    }

    @Override
    public RemoteResponse get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        RemoteResponse response = super.get(timeout, unit);
        if (null != response) {
            return response;
        } else {
            throw new RuntimeException("Timeout exception. Request id: " + this.remoteRequest.getRequestId()
                    + ". Request class name: " + this.remoteRequest.getBeanName()
                    + ". Request method: " + this.remoteRequest.getMethodName());
        }
    }

    @Override
    public void done(RemoteResponse remoteResponse) {
        super.done(remoteResponse);
        long responseTime = System.currentTimeMillis() - super.getBeginTime();
        if (responseTime > this.timeLimit) {
            logger.warn("Service response time is too slow. Request id = " + remoteResponse.getRequestId() + ". Response Time = " + responseTime + "ms");
        }
    }


}
