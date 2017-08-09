package com.my.gank.utils;

import android.util.Log;

import com.my.gank.BuildConfig;


/**
 * Log工具类
 * Created by My on 17/3/9.
 */
public class LogUtil {

    public static void d(String tag, String msg) {
        if (BuildConfig.isDebug) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.isDebug) {
            Log.e(tag, msg);
        }
    }


}
