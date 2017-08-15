package com.my.gank.activity;

import android.content.Intent;
import android.os.Bundle;

import com.my.gank.Constant;
import com.my.gank.R;
import com.my.gank.base.BaseActivity;
import com.my.gank.bean.AllGankBean;
import com.my.gank.request.RequestManager;
import com.my.gank.utils.ToastUtil;

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
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void sendRequest() {
        requestTest();
    }


    @Override
    public void reConnection() {
        requestTest();
    }

    @Override
    public int getPageStyle() {
        return Constant.PageStyle.LOADING_PAGE;
    }

    @Override
    public boolean isNeedNet() {
        return true;
    }


    private void requestTest() {

    }
}
