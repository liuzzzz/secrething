package com.secrething.adrift.server.nio;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * Created by liuzz on 2019-04-30 13:22.
 */
public class Server {
    public static void main(String[] args) throws Exception {
        Selector  selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannel.bind(new InetSocketAddress(9876));
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    selector.select(10000);
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()){
                        SelectionKey key = keyIterator.next();
                        int ops = key.readyOps();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
