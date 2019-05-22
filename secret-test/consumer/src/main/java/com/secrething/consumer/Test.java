package com.secrething.consumer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuzz on 2019-04-17 15:31.
 */
public class Test {
    static Jedis jedis = new Jedis("192.168.68.88", 6379);
    static Subscribe subscriber = new Subscribe();

    public static class Subscribe extends JedisPubSub {

        @Override
        public void onMessage(String channel, String message) {
            System.out.println(channel + "----" + message);
        }
    }

    public static void main(String[] args) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        Date d = dateFormat.parse("Sun May 05 17:08:51 CST 2019");
        System.out.println(d.getTime());
    }
}
