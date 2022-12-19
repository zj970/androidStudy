package com.coolweather.android.base;

import android.app.Application;
import org.litepal.LitePal;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/19
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
