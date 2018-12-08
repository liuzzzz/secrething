package com.secrething.adrift.search.core;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzz on 2018-12-08 15:13.
 */
@Data
public class SearchResponse {
    private int code;
    private String msg;
    private List<Routing> routings = new ArrayList<>();
}
