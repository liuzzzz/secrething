package com.secrething.rpc.core;

import com.secrething.common.util.ConcurrentHashMap;
import com.secrething.common.util.MesgFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by liuzengzeng on 2017/12/18.
 */
public class RemoteFuture<R extends TransportData, S extends TransportData> extends AbstractFuture<S> implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RemoteFuture.class);
    private final static Map<String, RemoteFuture> FUTURES = new ConcurrentHashMap<>();
    private static final AtomicLong SENDER_IDX = new AtomicLong();
    private static final String SENDER_PREFIX = "remote-future-pool-{}";
    private static final ExecutorService pool = new ThreadPoolExecutor(1, 50, 30, TimeUnit.SECONDS, new SynchronousQueue<>(), r -> {
        Thread t = new Thread(r, MesgFormatter.format(SENDER_PREFIX, SENDER_IDX.incrementAndGet()));
        return t;
    }, (r, executor) -> {
        if (r instanceof RemoteFuture) {
            RemoteFuture<RemoteRequest, RemoteResponse> rf = (RemoteFuture) r;
            RemoteRequest request = rf.getNeedSend();
            logger.error("rejected request {}", request);
        }
    });
    private final R needSend;
    private final long timeLimit = 5000;
    private final ExecutorService executor;
    private final Runnable target;

    public RemoteFuture(R request, Runnable target) {
        this(null, request, target);

    }

    public RemoteFuture(ExecutorService executor, R request, Runnable target) {
        super();
        if (null == executor) {
            this.executor = pool;
        } else {
            this.executor = executor;
        }
        this.needSend = request;
        this.target = target;
        FUTURES.put(request.transportId(), this);

    }

    public static RemoteFuture getFuture(String id) {
        return FUTURES.get(id);
    }

    public static RemoteFuture removeFuture(String id) {
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
        if (null != target) {
            target.run();
        }
    }

    public RemoteFuture<R, S> go() throws Exception {
        if (isDone()) {
            throw new RuntimeException("future has done");
        }
        this.executor.execute(this);
        return this;
    }
}
