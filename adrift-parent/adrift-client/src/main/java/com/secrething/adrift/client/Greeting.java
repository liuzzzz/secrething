package com.secrething.adrift.client;

import lombok.Data;

/**
 * Created by liuzz on 2018/11/22 下午4:35.
 */
@Data
public class Greeting {
    private String content;

    public Greeting(String content) {
        this.content = content;
    }

    public Greeting() {
    }
}
