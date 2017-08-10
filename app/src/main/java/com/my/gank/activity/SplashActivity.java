package com.my.gank.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.my.gank.Constant;
import com.my.gank.R;
import com.my.gank.base.BaseActivity;

/**
 * Author：mengyuan
 * Date  : 2017/8/4下午5:51
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class SplashActivity extends BaseActivity {


    @Override
    public int getLayId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    public void sendRequest() {
        requestTest(false);
    }


    @Override
    public void reConnection() {
        requestTest(true);
    }

    @Override
    public int getPageStyle() {
        return Constant.PageStyle.LOADING_PAGE;
//        return Constant.PageStyle.LOADING_DIALOG;
//        return Constant.PageStyle.NO_LOADING;
    }

    @Override
    public boolean isNeedNet() {
        return true;
    }


    /**
     * 模拟5秒请求
     */
    private void requestTest(final boolean isSuccess) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isSuccess) {
                                showData();
                            } else {
                                showNoDataPage();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
