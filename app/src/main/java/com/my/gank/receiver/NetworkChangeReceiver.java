package com.my.gank.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.my.gank.event.NetChangeEvent;
import com.my.gank.utils.LogUtil;
import com.my.gank.utils.NetUtils;
import com.my.gank.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;


/**
 * Author：mengyuan
 * Date  : 2017/7/18下午5:50
 * E-Mail:mengyuanzz@126.com
 * Desc  :网络状态改变监听
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    //当前网络状态，防止重复发送广播，默认为良好状态
    private boolean currentNetState = true;


    @Override
    public void onReceive(Context context, Intent intent) {

        LogUtil.i("mengyuan", "接受到网络状态改变广播:" + intent.getAction());
        switch (intent.getAction()) {
            case ConnectivityManager.CONNECTIVITY_ACTION:
                boolean netState = false;
                if (NetUtils.isConnected()) {
                    netState = true;
                }
                //防止重复发送广播
                if (currentNetState != netState) {
                    EventBus.getDefault().post(new NetChangeEvent(currentNetState = netState));
                }
                break;
        }
    }
}
