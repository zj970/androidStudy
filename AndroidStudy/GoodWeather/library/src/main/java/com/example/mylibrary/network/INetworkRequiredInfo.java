package com.example.mylibrary.network;

import android.app.Application;

/**
 * @auther zj970
 * @create 2023-05-19 下午11:18
 */
public interface INetworkRequiredInfo {

    /**
     * 获取App版本名
     */
    String getAppVersionName();

    /**
     * 获取App版本号
     */
    String getAppVersionCode();

    /**
     * 判断是否为Debug模式
     */
    boolean isDebug();

    /**
     * 获取全局上下文参数
     */
    Application getApplicationContext();
}
