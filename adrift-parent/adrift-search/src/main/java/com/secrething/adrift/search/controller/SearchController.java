package com.secrething.adrift.search.controller;

import com.secrething.adrift.search.core.Holder;
import com.secrething.adrift.search.core.Routing;
import com.secrething.adrift.search.core.SearchRequest;
import com.secrething.adrift.search.core.SearchResponse;
import com.secrething.adrift.search.push.handler.ChannelHolder;
import com.secrething.adrift.search.push.protocol.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by liuzz on 2018-12-08 15:12.
 */
@RestController
public class SearchController {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(500, 4000, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());

    @RequestMapping(value = "hello/search", method = {RequestMethod.POST})
    public SearchResponse search(SearchRequest request) {
        SearchResponse response = new SearchResponse();
        final Holder h = new Holder();
        final String searchKey = generatorSearchKey(request);
        CopyOnWriteArrayList<Routing> routingList = new CopyOnWriteArrayList<>();
        CountDownLatch latch = new CountDownLatch(20);
        for (int i = 0; i < 20; i++) {
            final int n = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(n + 1);
                        if (!h.compareAndSet(null, Constants.HOLDER)) {
                            // TODO search
                            List<Routing> routings = new ArrayList<>();
                            ChannelHolder.broadcastMess(searchKey, routings);
                        }
                    } catch (InterruptedException e) {

                    }
                }
            });
        }
        try {
            latch.await(5, TimeUnit.SECONDS);
            h.compareAndSet(null, Constants.HOLDER);

            //TODO merge
        } catch (InterruptedException e) {

        }
        return response;
    }


    String generatorSearchKey(SearchRequest request) {

        StringBuilder sbff = new StringBuilder();
        sbff.append(request.getFromCity())
                .append('_')
                .append(request.getToCity())
                .append('|')
                .append(request.getFromTime());
        if (StringUtils.isNotBlank(request.getRetTime())) {
            sbff.append('_').append(request.getRetTime());
        }
        sbff.append('|')
                .append('$')
                .append(request.getAdtNumber())
                .append('-')
                .append(request.getChdNumber())
                .append('-')
                .append(request.getInfNumber());
        return sbff.toString();
    }
}
