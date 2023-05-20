package com.example.goodweather.base;

import com.baidu.location.LocationClient;
import com.example.mylibrary.base.BaseApplication;

/**
 * @auther zj970
 * @create 2023-05-19 下午8:25
 */
public class WeatherApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //使用定位需要同意隐私合规政策
        LocationClient.setAgreePrivacy(true);

    }
}
