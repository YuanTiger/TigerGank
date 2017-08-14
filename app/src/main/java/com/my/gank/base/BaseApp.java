package com.my.gank.base;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.my.gank.receiver.NetworkChangeReceiver;
import com.squareup.leakcanary.LeakCanary;

/**
 * Author：mengyuan
 * Date  : 2017/8/4下午5:50
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class BaseApp extends MultiDexApplication {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //内存泄漏捕捉工具
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        context = this.getApplicationContext();
        //突破65535
        MultiDex.install(this);
        //网络状态广播注册
        NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);
    }


}
