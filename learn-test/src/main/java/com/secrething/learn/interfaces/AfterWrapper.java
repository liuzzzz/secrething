package com.secrething.learn.interfaces;

/**
 * Created by liuzz on 2019-03-14 16:39.
 */
public class AfterWrapper<T> {
    private Object preResult;
    private T result;
    private PreWrapper preWrapper;
    private Object afterResult;
    public Object getPreResult() {
        return preResult;
    }

    public void setPreResult(Object preResult) {
        this.preResult = preResult;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public PreWrapper getPreWrapper() {
        return preWrapper;
    }

    public void setPreWrapper(PreWrapper preWrapper) {
        this.preWrapper = preWrapper;
    }

    public Object getAfterResult() {
        return afterResult;
    }

    public void setAfterResult(Object afterResult) {
        this.afterResult = afterResult;
    }
}
