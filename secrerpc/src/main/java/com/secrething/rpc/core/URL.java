package com.secrething.rpc.core;

import com.secrething.common.util.MesgFormatter;
import lombok.Data;

/**
 * Created by Idroton on 2018/8/12.
 */
@Data
public class URL {
    private String protocol;
    private String host;
    private int port;
    private String path = "";

    public URL() {
    }

    public URL(String protocol, String host, int port) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    @Override
    public String toString() {
        return MesgFormatter.format("{}://{}:{}/{}", protocol, host, port, path);
    }

    public static void main(String[] args) {
        System.out.println(new URL("scr", "127.0.0.1", 9999));
    }
}
