package com.secrething.rpc.core;

/**
 * Created by liuzengzeng on 2017/12/18.
 * 通信返回封装
 */
public class RemoteResponse {
    private String requestId;
    private String error;
    private Object result;

    public boolean isError() {
        return error != null;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public static RemoteResponse defail(String requestId, String error) {
        RemoteResponse response = new RemoteResponse();
        response.requestId = requestId;
        response.error = error;
        return response;
    }
}
