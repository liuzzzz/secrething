package com.secrething.adrift.search.core;

import lombok.Data;

/**
 * Created by liuzz on 2018-12-08 15:13.
 */
@Data
public class SearchRequest {

    private String fromCity;
    private String toCity;
    private String fromTime;
    private String retTime;
    private int adtNumber;
    private int chdNumber;
    private int infNumber;

}
