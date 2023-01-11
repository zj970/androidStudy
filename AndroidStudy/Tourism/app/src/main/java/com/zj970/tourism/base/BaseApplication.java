package com.zj970.tourism.base;

import android.app.Application;
import android.content.Context;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.services.core.ServiceSettings;

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
        //定位隐私政策同意
        AMapLocationClient.updatePrivacyShow(context,true,true);
        AMapLocationClient.updatePrivacyAgree(context,true);
        //地图隐私政策同意
        MapsInitializer.updatePrivacyShow(context,true,true);
        MapsInitializer.updatePrivacyAgree(context,true);
        //搜索隐私政策同意
        ServiceSettings.updatePrivacyShow(context,true,true);
        ServiceSettings.updatePrivacyAgree(context,true);
        //加载百度地图so文件
        //LocationClient.setAgreePrivacy(true);
        //SDKInitializer.setAgreePrivacy(this,true);
        //SDKInitializer.initialize(this);
    }
}
