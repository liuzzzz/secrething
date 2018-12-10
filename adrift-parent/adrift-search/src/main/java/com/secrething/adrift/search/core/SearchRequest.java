package com.secrething.adrift.search.core;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by liuzz on 2018-12-08 15:13.
 */
@Data
public class SearchRequest {

    private String depCity;
    private String arrCity;
    private String depDate;
    private String retDate;
    private int adtNumber;
    private int chdNumber;
    private int infNumber;

    public String generatorSearchKey() {
        SearchRequest request = this;
        StringBuilder sbff = new StringBuilder();
        sbff.append(request.getDepCity())
                .append('_')
                .append(request.getArrCity())
                .append('|')
                .append(request.getDepDate());
        if (StringUtils.isNotBlank(request.getRetDate())) {
            sbff.append('_').append(request.getRetDate());
        }
        sbff.append('|')
                .append('$')
                .append(request.getAdtNumber())
                .append('-')
                .append(request.getChdNumber())
                .append('-')
                .append(request.getInfNumber());
        return sbff.toString();
    }

}
