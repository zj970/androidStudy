package com.example.goodweather.bean;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther zj970
 * @create 2023-06-03 上午12:07
 */
public class CityConverter {

    @TypeConverter
    public List<Province.City> stringToObject(String value) {
        Type userListType = new TypeToken<ArrayList<Province.City>>() {}.getType();
        return new Gson().fromJson(value, userListType);
    }

    @TypeConverter
    public String objectToString(List<Province.City> list) {
        return new Gson().toJson(list);
    }
}
