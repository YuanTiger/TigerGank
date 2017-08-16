package com.my.gank.base;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.my.gank.Constant;
import com.my.gank.R;
import com.my.gank.utils.NetUtils;
import com.my.gank.view.LoadingDialog;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Author：mengyuan
 * Date  : 2017/8/9下午3:47
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 * 页面状态控制器，每个页面持有一个，使用弱引用来持有Activity
 * 存在于BaseActivity中，无需手动控制，在页面销毁时自动释放
 */

public class PageController {

    //Activity对象
    private WeakReference<BaseActivity> weakActivity;

    //当前页面状态
    private int currentState;
    //Loading Dialog
    private LoadingDialog loadingDialog;


    public PageController(BaseActivity activity) {

        weakActivity = new WeakReference<>(activity);
    }


    /**
     * 显示数据View
     */
    public void showDataPage() {
        if (weakActivity == null || weakActivity.get() == null) {
            return;
        }
        hideLastPage();
        currentState = Constant.PageState.NORMAL;

        weakActivity.get().viewData.setVisibility(View.VISIBLE);
    }

    /**
     * 显示无数据View
     */
    public void showNoData() {
        showNoData(BaseApp.context.getString(R.string.no_data));
    }

    /**
     * 显示无数据View
     *
     * @param message 提示语
     */
    public void showNoData(String message) {
        if (weakActivity == null || weakActivity.get() == null) {
            return;
        }
        hideLastPage();
        currentState = Constant.PageState.NO_DATA;


        weakActivity.get().findViewById(R.id.view_no_data).setVisibility(View.VISIBLE);
        weakActivity.get().tvNoDataDesc.setText(message);
    }

    /**
     * 显示LoadingView
     */
    public void showLoadingPage() {
        if (weakActivity == null || weakActivity.get() == null) {
            return;
        }
        hideLastPage();
        currentState = Constant.PageState.LOADING;


        View loadingLayout = weakActivity.get().findViewById(R.id.view_loading);
        try {
            ImageView imageView = loadingLayout.findViewById(R.id.iv_loading);
            if (imageView != null) {
                AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
                animationDrawable.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadingLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 显示Loading Dialog
     */
    public void showLoadingDialog() {
        if (weakActivity == null || weakActivity.get() == null) {
            return;
        }
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(weakActivity.get());
        }
        loadingDialog.setMessage("请稍候...");
        loadingDialog.show();
    }

    /**
     * 隐藏Loading Dialog
     */
    public void hideLoadingDialog() {
        if (loadingDialog == null) {
            return;
        }
        loadingDialog.cancel();
    }

    /**
     * 显示无网View
     */
    public void showNoNet() {
        if (weakActivity == null || weakActivity.get() == null) {
            return;
        }
        hideLastPage();
        currentState = Constant.PageState.NO_NET;


        weakActivity.get().findViewById(R.id.view_no_net).setVisibility(View.VISIBLE);
        weakActivity.get().btReConn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weakActivity.get().reTry();
            }
        });
    }

    /**
     * 根据页面状态隐藏对应页面
     */
    private void hideLastPage() {

        switch (currentState) {
            case Constant.PageState.LOADING:
                weakActivity.get().findViewById(R.id.view_loading).setVisibility(View.GONE);
                break;
            case Constant.PageState.LOADING_DIALOG:
                hideLoadingDialog();
                break;
            case Constant.PageState.NO_NET:
                weakActivity.get().findViewById(R.id.view_no_net).setVisibility(View.GONE);
                break;
            case Constant.PageState.NO_DATA:
                weakActivity.get().findViewById(R.id.view_no_data).setVisibility(View.GONE);
                break;
            case Constant.PageState.NORMAL:
                weakActivity.get().viewData.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 根据页面状态显示对应页面
     */
    private void showPageByState() {
        switch (currentState) {
            case Constant.PageState.LOADING:
                showLoadingPage();
                break;
            case Constant.PageState.LOADING_DIALOG:
                showLoadingDialog();
                break;
            case Constant.PageState.NO_NET:
                showNoNet();
                break;
            case Constant.PageState.NO_DATA:
                showNoData();
                break;
            case Constant.PageState.NORMAL:
                showDataPage();
                break;
        }
    }


    /**
     * 初始化页面
     *
     * @param savedInstanceState
     */
    public void initViewByStyle(Bundle savedInstanceState) {
        //填充数据Layout
        int layoutId = weakActivity.get().getLayId();
        if (layoutId < 0) {
            throw new RuntimeException("请通过getLayId()设置布局");
        }
        View inflate = weakActivity.get().getLayoutInflater().inflate(layoutId, null);

        weakActivity.get().viewData.addView(inflate);

        ButterKnife.bind(weakActivity.get());

        //初始化页面
        weakActivity.get().initData(savedInstanceState);
        //是否需要ToolBar
        weakActivity.get().toolBar.setVisibility(weakActivity.get().isNeedToolbar() ? View.VISIBLE : View.GONE);
        //ToolBar初始化，如果需要的话
        if (weakActivity.get().isNeedToolbar()) {
            weakActivity.get().toolBarSetting(weakActivity.get().toolBar);
        }
        //启动页面Loading
        startLoading();


    }

    public void startLoading() {
        //页面是否需要网络，需要的话首先进行网络状态判断
        if (weakActivity.get().isNeedNet()) {
            if (!NetUtils.isConnected()) {
                showNoNet();
                return;
            }
        }
        hideLastPage();
        //到这里说明有网络 或者 不需要网络，即isNeedNet()返回false
        //根据页面加载方式，选择具体的页面样式
        //需要提一下的是，isNeedNet()返回false代表页面无需网络，那么getPageStyle()理应返回 Constant.PageStyle.NO_LOADING
        //因为没有网络请求的页面基本无需Loading
        switch (weakActivity.get().getPageStyle()) {
            case Constant.PageStyle.LOADING_PAGE:
                currentState = Constant.PageState.LOADING;
                break;
            case Constant.PageStyle.LOADING_DIALOG:
                currentState = Constant.PageState.LOADING_DIALOG;
                break;
            case Constant.PageStyle.NO_LOADING:
                currentState = Constant.PageState.NORMAL;
                break;
        }
        showPageByState();
    }

    public int getCurrentState() {
        return currentState;
    }

    public void onDestory() {
        if (weakActivity == null) {
            return;
        }
        weakActivity.clear();
        weakActivity = null;
        loadingDialog = null;
    }
}
