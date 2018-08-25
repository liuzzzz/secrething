package com.secrething.rpc.core;

import com.secrething.common.util.MesgFormatter;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Idroton on 2018/8/12.
 * dubbo's URL 😳
 */
@Data
public class URL {
    private String protocol = "remote";
    private String host;
    private int port;
    /**
     * interface --> com.secrething.test.HelloService
     * method    --> hello
     */
    private Map<String, String> parameters;

    public URL() {
        this(null, 0);
    }

    public URL(String host, int port) {
        this(host, port, null);
    }

    public URL(String host, int port, Map<String, String> parameters) {
        this.host = host;
        this.port = port;
        if (null != parameters)
            this.parameters = new HashMap<>(parameters);
        else
            this.parameters = new HashMap<>();
    }

    public static void main(String[] args) {
        System.out.println(new URL("127.0.0.1", 9999));
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    @Override
    public String toString() {
        return MesgFormatter.format("{}://{}:{}", protocol, host, port);
    }
}
