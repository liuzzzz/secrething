package com.secrething.adrift.search.push;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.secrething.adrift.search.controller.SearchController;
import com.secrething.adrift.search.core.SearchRequest;
import com.secrething.adrift.search.push.core.Constants;
import com.secrething.adrift.search.push.handler.ChannelHolder;

import java.util.List;

/**
 * Created by liuzz on 2018-12-08 15:22.
 */
public class Bootstrap {
    private static volatile boolean started = false;

    public static PushServer start() {
        final PushServer server = new PushServer(Constants.DEFAULT_PORT);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    server.shutdown();
                }));
                server.init();
                server.start();
                started = true;
            }
        }).start();

        return server;
    }

    public static void main(String[] args) throws InterruptedException {
        PushServer server = start();
        server.await();
        JSONObject req = JSONObject.parseObject("{\"code\":10000,\"extension\":\"{\\\"depCity\\\":\\\"1\\\",\\\"tripType\\\":\\\"1\\\",\\\"arrCity\\\":\\\"1\\\",\\\"depDate\\\":\\\"1\\\",\\\"retDate\\\":\\\"1\\\",\\\"adtNumber\\\":1,\\\"chdNumber\\\":0}\"}");
        SearchRequest request = JSONObject.parseObject(req.getString("extension"),SearchRequest.class);
        JSONObject jsonObject = JSONObject.parseObject(SearchController.getJSON());
        JSONArray jsonArray = jsonObject.getJSONArray("routings");
        List<Object> list = jsonArray.toJavaList(Object.class);
        while (true){
            ChannelHolder.broadcastMess(request.generatorSearchKey(),list.subList(0,50));
            Thread.sleep(1000);
        }
    }
    public static boolean isStarted(){
        return started;
    }

}
