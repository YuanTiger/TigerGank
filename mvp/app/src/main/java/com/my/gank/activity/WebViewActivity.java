package com.my.gank.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.my.gank.Constant;
import com.my.gank.R;
import com.my.gank.base.BaseActivity;
import com.my.gank.base.Jump;
import com.my.gank.utils.ToastUtil;


import butterknife.BindView;

/**
 * Author：mengyuan
 * Date  : 2017/8/16上午10:07
 * E-Mail:mengyuanzz@126.com
 */
public class WebViewActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private String url;

    @Override
    public void sendRequest() {
        webView.loadUrl(url);
    }

    @Override
    public int getLayId() {
        return R.layout.activity_webview;
    }



    @Override
    public void initData(Bundle savedInstanceState) {
        //网页中的视频，上屏幕的时候，可能出现闪烁的情况，需要如下设置
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        url = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url)) {
            ToastUtil.showShortToast(getString(R.string.data_error));
            return;
        }
        webViewInit();

    }

    /**
     * 初始化WebView
     */
    private void webViewInit() {
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 支持通过JS打开新窗口
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        //进度监听
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (progressBar != null)
                    progressBar.setProgress(newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (progressBar != null)
                    progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                    getToolBar().setTitle(TextUtils.isEmpty(webView.getTitle()) ? "WebView" : webView.getTitle());
                }
            }

            //请求失败时显示失败的界面
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                showNoNetPage();
            }
        });
    }


    @Override
    public int getPageStyle() {
        return Constant.PageStyle.NO_LOADING;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            //返回上个页面
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void reConnection() {
        showData();
        sendRequest();
    }


}
