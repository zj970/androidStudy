package com.coolweather.android.base;

import android.app.Application;
import android.content.Context;
import org.litepal.LitePal;

/**
 * <p>
 *  全局获取Context
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/19
 */
public class MyApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        LitePal.initialize(this);
    }
}
