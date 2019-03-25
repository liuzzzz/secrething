package com.secrething.adrift.server;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;

/**
 * Created by liuzz on 2019-03-20 09:53.
 */
@Repository
public class FinishedInitializationListener {
    @EventListener(classes = {Object.class})
    public void onApplicationEvent(Object event) {
        //System.out.println(event.getApplicationContext().getBean("&testWrapper"));
        System.out.println("refreshed");
    }
}
