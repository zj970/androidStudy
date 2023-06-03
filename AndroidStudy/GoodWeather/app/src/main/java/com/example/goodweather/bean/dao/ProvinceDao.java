package com.example.goodweather.bean.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.goodweather.bean.Province;
import io.reactivex.Completable;
import io.reactivex.Flowable;

import java.util.List;

/**
 * @auther zj970
 * @create 2023-06-03 上午12:19
 */
@Dao
public interface ProvinceDao {

    /**
     * 查询所有
     */
    @Query("SELECT * FROM Province")
    Flowable<List<Province>> getAll();

    /**
     * 插入所有
     * @param provinces 所有行政区数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(Province... provinces);
}
