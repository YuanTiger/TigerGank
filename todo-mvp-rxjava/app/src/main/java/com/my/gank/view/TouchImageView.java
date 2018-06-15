package com.my.gank.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Author：mengyuan
 * Date  : 2017/8/17上午11:35
 * E-Mail:mengyuanzz@126.com
 * Desc  :可以返回点击坐标的ImageView
 */

public class TouchImageView extends AppCompatImageView {
    private onImageClickListener listener;

    //手势解析处理
    private GestureDetector mGestureDetector;

    public TouchImageView(Context context) {
        this(context, null);
    }

    public TouchImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        mGestureDetector = new GestureDetector(this.getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            //单击
            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (listener != null) {
                    listener.onClick(motionEvent.getRawX(), motionEvent.getRawY(), TouchImageView.this);
                }
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    public void setonImageClickListener(onImageClickListener listener) {
        this.listener = listener;

    }


    public interface onImageClickListener {
        void onClick(float x, float y, View view);
    }
}
