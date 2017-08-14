package com.my.gank.utils;

import android.widget.Toast;

import com.my.gank.base.BaseApp;

public class ToastUtil {

    public static void showShortToast(int msgId) {

        Toast.makeText(BaseApp.context, msgId, Toast.LENGTH_SHORT).show();
    }

    public static void showShortToast(String msg) {

        Toast.makeText(BaseApp.context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(int msgId) {

        Toast.makeText(BaseApp.context.getApplicationContext(), msgId, Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(String msg) {

        Toast.makeText(BaseApp.context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
