package com.my.gank.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.my.gank.R;
import com.my.gank.utils.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * AUTHOR:       Yuan.Meng
 * E-MAIL:       mengyuanzz@126.com
 * CREATE-TIME:  17/10/8/上午11:47
 * DESC:
 */

public class BrowseImagePopupWindow extends PopupWindow {


    private View view;


    private Animation dismissAnimation;
    private Animation showAnimation;


    public BrowseImagePopupWindow(Context context, String url, float X, float Y) {
        initView(context, url);

        initAnimotion(X, Y);

    }


    private void initView(final Context context, String url) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_browse_img, null, false);

        PinchImageView ivSrc = view.findViewById(R.id.iv_src);
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
        //获取焦点、等于优先响应物理按键
        //在PopupWindow打开的时候，点击回退，会响应PopupWindow的回退事件，而非Activity的
        //如果设置为false，则会直接响应Activity的
        this.setFocusable(true);

        this.setBackgroundDrawable(new BitmapDrawable());
        //设置大小
        //设置大小
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        this.setContentView(view);
    }

    /**
     * 初始化显示、隐藏的动画，会从用户点击的点开始做动画
     *
     * @param x 用户点击的x坐标
     * @param y 用户点击的y坐标
     */
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

    @Override
    public void dismiss() {
        this.getContentView().startAnimation(dismissAnimation);

    }

    public void startAnimotion() {
        this.getContentView().startAnimation(showAnimation);
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

