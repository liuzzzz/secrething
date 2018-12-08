package com.secrething.adrift.search.push.protocol;

/**
 * Created by liuzz on 2018-12-08 15:26.
 */
public interface Constants {

    int PING_CODE = 10015;
    int PONG_CODE = 10016;

    int SEARCH_CODE = 10000;
    int MESS_CODE = 10086;
    /**
     * 系统消息类型
     */
    int SYS_OTHER_INFO = 20003; // 系统消息

    String SEARCH_KEY = "searchKey";

    Object HOLDER = new Object();

}
