package com.secrething.rpc.core;

/**
 * Created by liuzengzeng on 2017/12/18.
 * remote call response
 */
public class RemoteResponse implements TransportData {
    private String id;
    private String error;
    private Object result;
    private Throwable throwable;

    public boolean isError() {
        return error != null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public static RemoteResponse defail(String requestId, String error) {
        RemoteResponse response = new RemoteResponse();
        response.id = requestId;
        response.error = error;
        return response;
    }

    @Override
    public String transportId() {
        return getId();
    }
}
