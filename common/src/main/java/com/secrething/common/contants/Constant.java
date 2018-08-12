package com.secrething.common.contants;

/**
 *
 * Created by liuzengzeng on 2017/12/10.
 * 常量
 */
public interface Constant {

    int HEAD_DATA = 3411234;
    int ZK_SESSION_TIMEOUT = 50000;
    boolean IS_LINUX = System.getProperty("os.name").equals("Linux");
    String ZK_REGISTRY_PATH = "/registry";
    String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/data";

}
