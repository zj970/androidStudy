package com.example.goodweather.db.dao;

import androidx.room.*;
import com.example.goodweather.bean.MyCity;
import io.reactivex.Completable;
import io.reactivex.Flowable;

import java.util.List;

/**
 * @auther zj970
 * @create 2023-06-06 上午9:37
 */
@Dao
public interface MyCityDao {

    /**
     * 查询所有城市
     */
    @Query("SELECT * FROM MyCity")
    Flowable<List<MyCity>> getAllCity();

    /**
     * 添加城市
     *
     * @param myCity 城市
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCity(MyCity myCity);

    /**
     * 通过城市对象删除城市
     *
     * @param myCity 城市
     */
    @Delete
    Completable deleteCity(MyCity myCity);

    /**
     * 通过城市名称删除数据
     *
     * @param cityName 城市名称
     */
    @Query("DELETE  FROM MyCity where cityName=:cityName")
    Completable deleteCity(String cityName);

}
