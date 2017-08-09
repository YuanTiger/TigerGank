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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showData();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public int getPageStyle() {
        return Constant.PageStyle.LOADING_PAGE;
    }
}
