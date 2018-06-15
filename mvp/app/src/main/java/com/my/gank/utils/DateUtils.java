package com.my.gank.utils;

import android.text.TextUtils;
import android.text.format.DateFormat;

import com.my.gank.base.BaseApp;

import java.util.Date;

/**
 * Author：mengyuan
 * Date  : 2017/8/15下午2:41
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class DateUtils {

    public static String date2Date(String date, String format) {
        Date date1 = new Date(date);
        DateFormat df = new DateFormat();
        return df.format(format, date1).toString();
    }


    public static String toGankDate(String gankDate) {
        if (TextUtils.isEmpty(gankDate) || gankDate.length() < 10) {
            return null;
        }
        return gankDate.substring(0, 10).replaceAll("-", "/");
    }

}
