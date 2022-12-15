package com.zj970.advanced;

import android.app.Application;
import android.content.Context;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2022/12/15
 */
public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        context = getApplicationContext();
    }
    public static Context getContext() {
        return context;
    }

}
