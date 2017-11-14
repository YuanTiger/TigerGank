package com.my.gank.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.my.gank.Constant;
import com.my.gank.R;
import com.my.gank.event.NetChangeEvent;
import com.my.gank.utils.NetUtils;
import com.my.gank.utils.ToastUtil;
import com.my.gank.view.BaseButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author：mengyuan
 * Date  : 2017/8/16上午10:07
 * E-Mail:mengyuanzz@126.com
 * DESC  :Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    Toolbar toolBar;
    ImageView ivLoading;
    TextView tvNoDataDesc;
    BaseButton btReConn;
    FrameLayout viewData;
    BaseButton btBreakOffSetting;
    LinearLayout llNetBreakOff;


    //页面状态控制器
    private PageController pageController;
    //Activity切换动画控制器
    public Jump jump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //因为ButterKnife只能Bind一次，我将子Activity的Bind操作进行了封装，所以基类无法再进行Bind。
        toolBar = findViewById(R.id.tool_bar);
        ivLoading = findViewById(R.id.iv_loading);
        tvNoDataDesc = findViewById(R.id.tv_nodata_desc);
        btReConn = findViewById(R.id.bt_re_conn);
        viewData = findViewById(R.id.view_data);
        btBreakOffSetting = findViewById(R.id.bt_break_off_setting);
        llNetBreakOff = findViewById(R.id.ll_net_break_off);


        EventBus.getDefault().register(this);

        pageController = new PageController(this);
        jump = new Jump(this);
        //初始化页面
        pageController.initViewByStyle(savedInstanceState);
        //解析Activity切换动画类型
        if (getIntent() != null) {
            Jump.JumpType jumpType = (Jump.JumpType) getIntent().getSerializableExtra(Jump.jump);
            if (jumpType != null) {
                jump.setCurrentAnimation(jumpType);
            }
        }

        //顶部网络状态监听栏设置按钮点击事件
        btBreakOffSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetUtils.openSetting();
            }
        });
        //如果没有网络，不发出请求
        if (pageController.getCurrentState() == Constant.PageState.NO_NET) {
            return;
        }
        sendRequest();
    }

    //发送网络请求
    public abstract void sendRequest();

    //获取布局Id
    public abstract int getLayId();

    //初始化页面，代替onCreate(),该方法中进行页面的一些初始化操作
    public abstract void initData(Bundle savedInstanceState);

    //获取初始化页面时的风格，默认使用页面Loading，点进去可查看详情
    public int getPageStyle() {
        return Constant.PageStyle.LOADING_PAGE;
    }

    //返回该Activity是否需要网络，如果无需网络也可进入，则重写修改返回值
    public boolean isNeedNet() {
        return true;
    }

    //是否需要标题栏ToolBar，默认是需要的。
    public boolean isNeedToolbar() {
        return true;
    }

    //ToolBar设置，需要时重写即可，如果不想要统一标准，子类重写时删除super.toolBarSetting(toolbar)即可
    public void toolBarSetting(Toolbar toolbar) {
        //标题颜色
        toolbar.setTitleTextColor(getResources().getColor(R.color.c_ffffff));
        //回退按钮
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //返回ToolBar控件
    public Toolbar getToolBar() {
        return toolBar;
    }

    //无网状态下的重连按钮点击事件
    public abstract void reConnection();

    //显示 Loading Page
    public void showLoadingPage() {
        pageController.showLoadingPage();
    }

    //显示 Loading Dialog
    public void showLoadingDialog() {
        pageController.showLoadingDialog();
    }

    //显示 无数据 Page
    public void showNoDataPage() {
        pageController.showNoData();
    }

    //显示 无数据 Page
    public void showNoDataPage(String message) {
        pageController.showNoData(message);
    }

    //显示 无网 Page
    public void showNoNetPage() {
        pageController.showNoNet();
    }

    //显示 数据 Page
    public void showData() {
        pageController.showDataPage();
    }


    protected void reTry() {
        pageController.startLoading();
        if (pageController.getCurrentState() == Constant.PageState.NO_NET) {
            ToastUtil.showLongToast(R.string.net_error);
            return;
        }
        reConnection();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetChangeEvent event) {
        llNetBreakOff.setVisibility(!event.isConnection ? View.VISIBLE : View.GONE);
    }

    @Override
    public void finish() {
        super.finish();
        jump.finish();
        if (pageController != null) {
            pageController.onDestory();
        }
        if (jump != null) {
            jump.onDestory();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

}
