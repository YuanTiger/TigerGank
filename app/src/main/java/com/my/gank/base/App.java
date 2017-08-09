package com.my.gank.base;

import android.app.Application;
import android.content.Context;

/**
 * Author：mengyuan
 * Date  : 2017/8/4下午5:50
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this.getApplicationContext();
    }
}
