package com.my.gank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 开始时间 2017年8月4日晚
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
