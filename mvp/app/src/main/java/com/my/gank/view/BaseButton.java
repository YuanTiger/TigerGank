package com.my.gank.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;

import com.my.gank.R;

/**
 * Author：mengyuan
 * Date  : 2017/8/9下午4:48
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class BaseButton extends AppCompatButton {
    public BaseButton(Context context) {
        super(context);

        initView();
    }

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();

    }

    public BaseButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {
        //背景
        setBackgroundResource(R.drawable.selector_rule_0090ff);
        //字体
        ColorStateList csl = getResources().getColorStateList(R.color.selector_ffffff_7ffffff);
        setTextColor(csl);
    }
}
