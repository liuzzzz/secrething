package com.secrething.rpc.server;

/**
 * Created by liuzengzeng on 2017/12/22.
 * 服务提供端启动类
 */
public class Loader {
    private static volatile boolean running = true;

    public static void main(String[] args) {
        try {
            if ("provider".equals(LoadProperties.getConfig("secrerpc.rule"))) {
                ServerLoader.start();
                Runtime runtime = Runtime.getRuntime();
                runtime.addShutdownHook(new Thread() {
                    public void run() {
                        synchronized (Loader.class) {
                            ServerLoader.stop();
                            running = false;
                            Loader.class.notify();
                        }
                    }
                });
            }
        } catch (RuntimeException e) {
            System.exit(1);
        }

        synchronized (Loader.class) {
            while (running) {
                try {
                    Loader.class.wait();
                } catch (InterruptedException e) {

                }
            }

        }
    }
}
