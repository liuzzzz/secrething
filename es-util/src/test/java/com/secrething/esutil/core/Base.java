package com.secrething.esutil.core;

/**
 * Created by liuzz on 2018-11-27 12:07.
 */
public class Base {
    @Key("id")
    private String uid = String.valueOf(System.currentTimeMillis());

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
