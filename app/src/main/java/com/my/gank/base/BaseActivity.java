package com.my.gank.base;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.gank.Constant;
import com.my.gank.R;
import com.my.gank.utils.ToastUtil;
import com.my.gank.view.BaseButton;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 开始时间 2017年8月9日晚
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.iv_loading)
    ImageView ivLoading;
    @Bind(R.id.tv_nodata_desc)
    TextView tvNoDataDesc;
    @Bind(R.id.bt_re_conn)
    BaseButton btReConn;
    @Bind(R.id.view_data)
    FrameLayout viewData;
    //页面状态控制器
    private static PageController pageController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);

        pageController = new PageController(this);

        pageController.initViewByStyle();

        initData(savedInstanceState);
    }

    //获取布局Id
    public abstract int getLayId();

    //初始化页面，代替onCreate()
    public abstract void initData(Bundle savedInstanceState);

    //获取初始化页面时的风格，默认使用页面Loading，点进去可查看详情
    public int getPageStyle() {
        return Constant.PageStyle.LOADING_PAGE;
    }

    //返回该Activity是否需要网络，如果无需网络也可进入，则重写修改返回值
    public boolean isNeedNet() {
        return true;
    }

    //是否需要标题栏
    public boolean isNeedToolbar() {
        return true;
    }

    //初始化ToolBar，需要时重写即可
    public void toolBarInit(Toolbar toolbar) {
    }

    //无网状态下的重连按钮点击事件，在需要的时候重写即可
    public void reTry() {
        ToastUtil.showLongToast("reTry()，重写此方法请删除 super.reTry()");
    }

    //显示LoadingPage
    public void showLoadingPage() {
        pageController.showLoadingPage();
    }

    //显示Loading Dialog
    public void showLoadingDialog() {
        pageController.showLoadingDialog();
    }

    //显示无数据Page
    public void showNoDataPage() {
        pageController.showNoData();
    }

    //显示无数据Page
    public void showNoDataPage(String message) {
        pageController.showNoData(message);
    }

    //显示无网Page
    public void showNoNetPage() {
        pageController.showNoNet();
    }

    //显示数据页面
    public void showData() {
        pageController.showDataPage();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (pageController != null) {
            pageController.onDestory();
        }
    }

}
