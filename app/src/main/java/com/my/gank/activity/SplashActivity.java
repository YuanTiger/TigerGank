package com.my.gank.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.my.gank.Constant;
import com.my.gank.R;
import com.my.gank.base.BaseActivity;
import com.my.gank.bean.HomeBean;
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
        RequestManager.getInstance().getAsync(Constant.URL.HOME, new RequestManager.MyRequestCallback<HomeBean>() {

            @Override
            public void success(HomeBean data) {
                showData();
                ToastUtil.showLongToast(data.results.get(0).desc);
            }

            @Override
            public void failed(String message) {
                ToastUtil.showLongToast(message);
                showNoDataPage();
            }
        });
    }
}
