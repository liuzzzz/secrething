package com.secrething.adrift.client;

import com.secrething.esutil.core.Document;
import com.secrething.esutil.core.Key;
import lombok.Data;

/**
 * Created by liuzz on 2019-01-16 14:09.
 */
@Document(index = "air_quality",type = "air_quality")
@Data
public class AirQuality extends Base{
    @Key
    private String province;

    @Key
    private String city;

    @Key
    private String district;

    @Key
    private String station;
    @Key
    private String level;
    @Key
    private Number aqi;
    @Key
    private String pollutions;
    @Key
    private Number so2;
    @Key
    private Number so2_index;
    @Key
    private Number co;
    @Key
    private Number co_index;
    @Key
    private Number no2;
    @Key
    private Number no2_index;

    @Key
    private Number o3;
    @Key
    private Number o3_index;
    @Key
    private Number pm10;
    @Key
    private Number pm10_index;
    @Key
    private Number pm10_24h;
    @Key
    private Number pm10_24h_index;
    @Key
    private Number pm2_5;
    @Key
    private Number pm2_5_index;
    @Key
    private Number pm2_5_24h;
    @Key
    private Number pm2_5_24h_index;
    @Key
    private Number o3_8h;
    @Key
    private Number o3_8h_index;
    @Key
    private String pubTime;

}
