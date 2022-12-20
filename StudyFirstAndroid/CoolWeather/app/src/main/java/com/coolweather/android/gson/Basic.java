package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/20
 */
public class Basic implements Serializable {
    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update implements Serializable {
        @SerializedName("loc")
        public String updateTime;
    }

}
