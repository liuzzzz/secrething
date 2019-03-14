package com.secrething.adrift.client;

import com.secrething.esutil.core.Document;
import com.secrething.esutil.core.Key;
import lombok.Data;

import java.util.Date;

/**
 * Created by liuzz on 2019-01-16 14:09.
 */
@Document(index = "air_quality",type = "air_quality")
@Data
public class AirQuality extends Base{
    @Key
    private String station;
    @Key
    private Number aqi;
    @Key
    private Number so2;
    @Key
    private Number co;
    @Key
    private Number no2;
    @Key
    private Number o3;
    @Key
    private Number pm10;
    @Key
    private Number pm2_5;
    @Key
    private String pubTime;
    @Key
    private String pubDate;
    @Key
    private long pubTimeLong;

}
