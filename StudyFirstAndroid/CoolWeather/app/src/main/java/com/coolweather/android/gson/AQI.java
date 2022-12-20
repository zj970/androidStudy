package com.coolweather.android.gson;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/20
 */
public class AQI implements Serializable {
    public AQICity city;
    public class AQICity{
        public String aqi;
        public String pm25;
    }
}
