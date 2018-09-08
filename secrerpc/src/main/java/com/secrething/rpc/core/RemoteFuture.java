package com.secrething.rpc.core;

import com.secrething.common.util.ConcurrentHashMap;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by liuzengzeng on 2017/12/18.
 */
public class RemoteFuture<R extends TransportData,S extends TransportData> extends AbstractFuture<S> implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RemoteFuture.class);
    private final R needSend;
    private final long timeLimit = 5000;
    private final Channel channel;
    private final static Map<String,RemoteFuture> FUTURES = new ConcurrentHashMap<>();
    public RemoteFuture(R request, Channel channel) {
        super();
        this.needSend = request;
        this.channel = channel;
        FUTURES.put(request.transportId(),this);

    }
    public static RemoteFuture getFuture(String id){
        return FUTURES.get(id);
    }
    public static RemoteFuture removeFuture(String id){
        return FUTURES.remove(id);
    }
    public R getNeedSend() {
        return needSend;
    }

    @Override
    public S get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        S response = super.get(timeout, unit);
        if (null != response) {
            return response;
        } else {
            throw new RuntimeException("Timeout exception. Request " + needSend);
        }
    }

    @Override
    public void done(S willReceived) {
        super.done(willReceived);
        long responseTime = System.currentTimeMillis() - super.getBeginTime();
        if (responseTime > this.timeLimit) {
            logger.warn("Service response time is too slow. Response " + willReceived + ". Response Time = " + responseTime + "ms");
        }
    }

    @Override
    public void run() {
        this.channel.writeAndFlush(needSend);
    }
}
