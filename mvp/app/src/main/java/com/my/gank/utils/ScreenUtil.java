package com.my.gank.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.my.gank.base.BaseApp;

/**
 * 屏幕分辨率转换工具
 *
 * @author tzali
 */
public class ScreenUtil {

    /**
     * 获取标题栏的高度
     *
     * @param activity
     * @return > 0 success; <= 0 fail
     */
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        int resourceId = activity.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusHeight = activity.getResources().getDimensionPixelSize(
                    resourceId);
        }
        if (statusHeight != 0)
            return statusHeight;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass
                        .getField("status_bar_height").get(localObject)
                        .toString());
                statusHeight = activity.getResources()
                        .getDimensionPixelSize(i5);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * 获取标题栏的高度
     *
     * @param context
     * @return
     */
    public static float getActionbarSize(Context context) {
        TypedValue tv = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
        int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        return actionBarHeight;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(float pxValue) {
        final float scale = getDisplayMetrics(BaseApp.context).density;
        return (int) (pxValue / scale + 0.5f);
    }


    private static DisplayMetrics mDm;

    private static DisplayMetrics getDisplayMetrics(Context context) {
        if (mDm == null) {
            if (context != null) {
                mDm = context.getResources().getDisplayMetrics();
            }
            return mDm;
        }
        return mDm;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px
     */
    public static int dp2Px( int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getDisplayMetrics(BaseApp.context)) + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp(字体) 的单位 转成为 px
     */
    public static int sp2Px( int spValue) {
        final float fontScale = getDisplayMetrics(BaseApp.context).scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 当前屏幕高度
     */
    public static int getCurrentScreenHeight() {
        return getDisplayMetrics(BaseApp.context).heightPixels;
    }

    /**
     * 当前屏幕宽度
     */
    public static int getCurrentScreenWidth() {
        return getDisplayMetrics(BaseApp.context).widthPixels;
    }

    /**
     * 获取当前屏幕亮度
     *
     * @param activity
     * @return
     */
    public static int getScreenBrightness(Activity activity) {
        int value = 0;
        if (activity == null) {
            value = -1;
            return value;
        }
        ContentResolver cr = activity.getContentResolver();
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            value = -1;
        }
        return value;
    }

    /**
     * 设置当前屏幕亮度
     *
     * @param activity
     * @param value
     */
    public static void setScreenBrightness(Activity activity, int value) {
        if (activity == null || value < 0) {
            return;
        }

        if (value > 255) {
            value = 255;
        }

        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.screenBrightness = value / 255f;
        activity.getWindow().setAttributes(params);
    }

    /**
     * 设置二维码界面的亮度
     *
     * @param activity
     * @param rate     增加多少倍原有亮度
     */
    public static void adjustQRCodeBrightness(Activity activity, float rate) {
        int brightness = getScreenBrightness(activity);
        if (brightness <= 0) {
            return;
        }
        brightness += brightness * rate;
        setScreenBrightness(activity, brightness);
    }
}
