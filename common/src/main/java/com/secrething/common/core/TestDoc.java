package com.secrething.common.core;

/**
 * Created by liuzz on 2018/11/25 上午1:35.
 */
@Document(index = "Hello",type = "hello")
public class TestDoc {

    @IdField
    private long id = System.currentTimeMillis();

    private boolean h = false;

    private String name = "张三";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isH() {
        return h;
    }

    public void setH(boolean h) {
        this.h = h;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
