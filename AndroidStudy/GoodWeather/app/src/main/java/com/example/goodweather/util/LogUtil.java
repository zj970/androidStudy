package com.example.goodweather.util;

import android.util.Log;

/**
 * 日志工具类
 * @auther zj970
 * @create 2023-05-19 下午8:34
 */
public class LogUtil {

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static final int level = DEBUG;

    public static void v(String tag, String msg) {
        if (level <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (level <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (level <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (level <= WARN) {
            Log.w(tag, msg);
        }
    }
    public static void w(String tag, String errorCleaningUpHidProxy, Throwable t) {
        if (level <=WARN){
            Log.w(tag, errorCleaningUpHidProxy,t);
        }
    }

    public static void e(String tag, String msg, NoSuchMethodError e) {
        if (level <= ERROR) {
            Log.e(tag, msg,e);
        }
    }

    public static void e(String tag, String msg, Exception e) {
        if (level <= ERROR) {
            Log.e(tag, msg, e);
        }
    }

    public static void e(String tag, String msg) {
        if (level <= ERROR) {
            Log.e(tag, msg);
        }
    }

}
