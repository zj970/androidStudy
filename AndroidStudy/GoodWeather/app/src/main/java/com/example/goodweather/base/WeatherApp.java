package com.example.goodweather.base;

import com.baidu.location.LocationClient;
import com.example.goodweather.bean.NetworkRequiredInfo;
import com.example.goodweather.bean.dao.AppDatabase;
import com.example.goodweather.util.MVUtils;
import com.example.mylibrary.base.BaseApplication;
import com.example.mylibrary.network.NetworkApi;
import com.tencent.mmkv.MMKV;

/**
 * @auther zj970
 * @create 2023-05-19 下午8:25
 */
public class WeatherApp extends BaseApplication {

        //数据库
        private static AppDatabase db;

        @Override
        public void onCreate() {
            super.onCreate();
            //使用定位需要同意隐私合规政策
            LocationClient.setAgreePrivacy(true);
            //初始化网络框架
            NetworkApi.init(new NetworkRequiredInfo(this));
            //MMKV初始化
            MMKV.initialize(this);
            //工具类初始化
            MVUtils.getInstance();
            //初始化Room数据库
            db = AppDatabase.getInstance(this);
        }

        public static AppDatabase getDb() {
            return db;
        }

}
