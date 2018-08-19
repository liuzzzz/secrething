package com.secrething.rpc.core;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Idroton on 2018/8/12.
 */
@Data
public class URL {
    private final Map<String, String> parameters = new HashMap<>();
    private String host;
    private int port;
    private String path;

    public URL() {
    }

    public URL(String host, int port) {
        this.host = host;
        this.port = port;
    }

}
