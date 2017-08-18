package com.my.gank.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.my.gank.Constant;
import com.my.gank.R;
import com.my.gank.activity.HomeActivity;
import com.my.gank.utils.ScreenUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * AUTHOR:       Yuan.Meng
 * E-MAIL:       mengyuanzz@126.com
 * CREATE-TIME:  16/9/8/上午11:47
 * DESC:
 */

public class BrowseImagePopupWindow extends PopupWindow {

    @Bind(R.id.iv_src)
    PinchImageView ivSrc;


    private View view;


    private Animation dismissAnimation;
    private Animation showAnimation;


    public BrowseImagePopupWindow(Context context, String url, float X, float Y) {
        initView(context, url, X, Y);
    }


    private void initView(final Context context, String url, float x, float y) {
        view = ((Activity) context).getLayoutInflater().inflate(R.layout.pop_browse_img, null);
        ButterKnife.bind(this, view);
        //设置图片
        Glide.with(context)
                .load(url)
                .fitCenter()
                .into(ivSrc);
        ivSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
//        this.setFocusable(true);
        //点击外部关闭
//        this.setOutsideTouchable(true);
        //无需动画
//        this.setAnimationStyle(0);
        this.setContentView(view);
        //设置大小
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        initAnimotion(x, y);
    }


    private void initAnimotion(float x, float y) {
        int screenWidth = ScreenUtil.getCurrentScreenWidth();
        int screenHeight = ScreenUtil.getCurrentScreenHeight();

        float floatX = x / screenWidth;
        float floatY = y / screenHeight;
        showAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, floatX,
                Animation.RELATIVE_TO_SELF, floatY);
        dismissAnimation = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, floatX,
                Animation.RELATIVE_TO_SELF, floatY);
        showAnimation.setDuration(300);
        showAnimation.setFillEnabled(true);
        showAnimation.setFillAfter(true);
        showAnimation.setInterpolator(new AccelerateInterpolator());
        dismissAnimation.setDuration(300);
        dismissAnimation.setFillEnabled(true);
        dismissAnimation.setFillAfter(true);
        dismissAnimation.setInterpolator(new AccelerateInterpolator());

    }

    @Override
    public void dismiss() {
        view.startAnimation(dismissAnimation);
        dismissAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                BrowseImagePopupWindow.super.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void startAnimotion() {
        view.startAnimation(showAnimation);
    }


    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom - yoff;
            setHeight(height);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom - yoff;
            setHeight(height);
        }
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }
}

