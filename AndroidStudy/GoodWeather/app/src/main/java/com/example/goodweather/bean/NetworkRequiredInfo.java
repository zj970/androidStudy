package com.example.goodweather.bean;

import android.app.Application;
import com.baidu.ar.npc.BuildConfig;
import com.example.mylibrary.network.INetworkRequiredInfo;

/**
 * @auther zj970
 * @create 2023-05-20 下午4:42
 */
public class NetworkRequiredInfo implements INetworkRequiredInfo {

    private final Application application;

    public NetworkRequiredInfo(Application application) {
        this.application = application;
    }

    /**
     * 获取App版本名
     */
    @Override
    public String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * 获取App版本号
     */
    @Override
    public String getAppVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    /**
     * 判断是否为Debug模式
     */
    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    /**
     * 获取全局上下文参数
     */
    @Override
    public Application getApplicationContext() {
        return application;
    }
}
