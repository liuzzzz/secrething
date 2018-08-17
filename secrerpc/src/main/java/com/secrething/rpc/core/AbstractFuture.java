package com.secrething.rpc.core;

import java.util.concurrent.Future;

/**
 * Created by liuzengzeng on 2017/12/20.
 */
public abstract class AbstractFuture<T> implements Future<T>{
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCancelled() {
        throw new UnsupportedOperationException();
    }
}
