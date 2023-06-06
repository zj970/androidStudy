package com.example.goodweather.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @auther zj970
 * @create 2023-06-06 上午9:18
 */
@Entity
public class MyCity {

    @NonNull
    @PrimaryKey
    private String cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Ignore
    public MyCity(String cityName) {
        this.cityName = cityName;
    }

    public MyCity() {}
}
