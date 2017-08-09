package com.my.gank.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.my.gank.utils.LogUtil;
import com.my.gank.utils.ToastUtil;


/**
 * Author：mengyuan
 * Date  : 2017/7/18下午5:50
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    //当前网络状态，防止重复发送广播，默认为良好状态
    private boolean currentNetState = true;


    @Override
    public void onReceive(Context context, Intent intent) {

        LogUtil.i("mengyuan", "接受到网络状态改变广播:" + intent.getAction());
        // 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
        // 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
        // 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适
        switch (intent.getAction()) {
            case ConnectivityManager.CONNECTIVITY_ACTION:
                ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
                boolean netState = false;
                if (activeNetwork != null) {
                    //网络是否可用
                    if (activeNetwork.isConnected()) {
                        netState = true;
                    }
                }
                //防止重复发送广播
                if (currentNetState != netState) {
                    ToastUtil.showLongToast("网络改变");
                }
                break;
        }
    }
}
