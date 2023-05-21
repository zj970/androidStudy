package com.example.goodweather.base;

import com.baidu.location.LocationClient;
import com.example.goodweather.bean.NetworkRequiredInfo;
import com.example.mylibrary.base.BaseApplication;
import com.example.mylibrary.network.NetworkApi;

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

        //初始化网络框架
        NetworkApi.init(new NetworkRequiredInfo(this));
    }
}
