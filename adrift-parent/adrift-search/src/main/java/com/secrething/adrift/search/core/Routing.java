package com.secrething.adrift.search.core;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzz on 2018-12-08 15:16.
 */
@Data
public class Routing {

    private int adtPrice;
    private int chdPrice;
    private int infPrice;

    private List<Flight> fromSegments = new ArrayList<>();

    private List<Flight> retSegments = new ArrayList<>();
}
