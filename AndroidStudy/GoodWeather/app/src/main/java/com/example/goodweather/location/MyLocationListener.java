package com.example.goodweather.location;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.example.goodweather.util.LogUtil;

/**
 * 自定义定位监听类
 * @auther zj970
 * @create 2023-05-19 下午8:29
 */
public class MyLocationListener extends BDAbstractLocationListener {
    private static final String TAG = MyLocationListener.class.getSimpleName();

    //定位回调
    private LocationCallback callback;

    public void setCallback(LocationCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (callback == null){
            LogUtil.e(TAG,"Callback is null");
            return;
        }
        /**
         * 将百度SDK定位的定位结果返回工程抽离出来，用接口回调的方式，减少Activity使用时的成本，哪里使用就哪里实现接口
         */
        callback.onReceiveLocation(bdLocation);
    }
}
