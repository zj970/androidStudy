package com.zj970.tourism.base;

import android.app.Application;
import android.content.Context;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/1
 */
public class BaseApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        //加载百度地图so文件
        LocationClient.setAgreePrivacy(true);
        SDKInitializer.setAgreePrivacy(this,true);
        SDKInitializer.initialize(this);
    }
}
