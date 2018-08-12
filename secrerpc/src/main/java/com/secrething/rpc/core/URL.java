package com.secrething.rpc.core;

/**
 * Created by Idroton on 2018/8/12.
 */
public class URL {
    private String host;
    private int port;

    public URL() {
    }

    public URL(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
