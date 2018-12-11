package com.secrething.adrift.search.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.secrething.adrift.search.core.Holder;
import com.secrething.adrift.search.core.Routing;
import com.secrething.adrift.search.core.SearchRequest;
import com.secrething.adrift.search.core.SearchResponse;
import com.secrething.adrift.search.push.Bootstrap;
import com.secrething.adrift.search.push.handler.AuthHandler;
import com.secrething.adrift.search.push.handler.ChannelHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by liuzz on 2018-12-08 15:12.
 */
@RestController
public class SearchController {
    private static final ScheduledExecutorService s = Executors.newScheduledThreadPool(1);
    private final ConcurrentMap<String,Object> concurrentMap = new ConcurrentHashMap<>();
    static {
        s.schedule(Bootstrap::start, 5, TimeUnit.SECONDS);
    }

    ThreadPoolExecutor executor = new ThreadPoolExecutor(500, 4000, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());

    @RequestMapping(value = "hello/search", method = {RequestMethod.POST,RequestMethod.GET})
    public Object search(@RequestBody String text) throws InterruptedException {
        AuthHandler.getLatch().await(10,TimeUnit.SECONDS);
        final SearchRequest request = JSONObject.parseObject(text, SearchRequest.class);
        SearchResponse response = new SearchResponse();
        final Holder h = new Holder();
        final String searchKey = request.generatorSearchKey();
        CopyOnWriteArrayList<Routing> routingList = new CopyOnWriteArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);
        JSONObject json = JSONObject.parseObject(getJSON());
        JSONArray routings = json.getJSONArray("routings");
        JSONArray ja = new JSONArray();
        ja.addAll(routings.subList(0, 30));
        json.put("routings", ja);
        List<Object> caches = routings.subList(30, routings.size());
        executor.execute(() -> {
            int size = caches.size();
            List<Object> rs = new ArrayList<>();
            for (int i = size - 1; i >= 0; i--) {
                rs.add(caches.get(i));
                if (i % 100 == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ChannelHolder.broadcastMess(searchKey, rs);
                    rs = new ArrayList<>();
                }
            }
        });

        try {
            latch.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {

        }
        response.setRoutings(routingList);
        return json;
    }


    public static String getJSON() {
        try {
            File file = new File("/usr/local/Shared/response.json");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String json = reader.readLine();
            reader.close();
            return json;
        } catch (Exception e) {
            return "";
        }

    }
}
