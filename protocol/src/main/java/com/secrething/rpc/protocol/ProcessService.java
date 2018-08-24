package com.secrething.rpc.protocol;

/**
 * Created by Idroton on 2018/8/11.
 */
public interface ProcessService<R,S> {
    /**
     * process a input return a result
     * @param inputMsg
     * @return S
     */
    S process(R inputMsg);
}
