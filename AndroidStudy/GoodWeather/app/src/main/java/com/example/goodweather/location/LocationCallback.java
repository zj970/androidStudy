package com.example.goodweather.location;

import com.baidu.location.BDLocation;

/**
 * 定位接口
 * @auther zj970
 * @create 2023-05-19 下午8:27
 */
public interface LocationCallback {
    /**
     * 接收定位
     * @param bdLocation 定位数据
     */
    void onReceiveLocation(BDLocation bdLocation);
}
