package com.example.goodweather.base;

import android.app.Application;
import com.baidu.location.LocationClient;
/**
 * @auther zj970
 * @create 2023-05-19 下午8:25
 */
public class WeatherApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //使用定位需要同意隐私合规政策
        LocationClient.setAgreePrivacy(true);

    }
}
