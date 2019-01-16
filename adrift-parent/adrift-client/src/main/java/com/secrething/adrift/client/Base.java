package com.secrething.adrift.client;

import com.secrething.esutil.core.Key;

/**
 * Created by liuzz on 2018-11-27 12:07.
 */
public class Base {
    @Key("id")
    private String uid = UUIDBuilder.genUUID();

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
