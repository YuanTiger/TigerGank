package com.my.gank.utils;

import android.widget.Toast;

import com.my.gank.base.App;

public class ToastUtil {

    public static void showShortToast(int msgId) {

        Toast.makeText(App.context, msgId, Toast.LENGTH_SHORT).show();
    }

    public static void showShortToast(String msg) {

        Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(int msgId) {

        Toast.makeText(App.context.getApplicationContext(), msgId, Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(String msg) {

        Toast.makeText(App.context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
