package com.my.gank.base;

import android.content.Intent;

import com.my.gank.R;

import java.lang.ref.WeakReference;

/**
 * Author：mengyuan
 * Date  : 2017/8/21下午4:25
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class Jump {

    public static final String jump = "jump";


    public enum JumpType {
        //左右平移动画
        SLIDING_SCREEN
        //弹出动画
        , EJECT
        //使用Android默认动画，也就是不需要自定义动画
        , NORMAL
    }

    //Activity对象
    private WeakReference<BaseActivity> weakActivity;
    //当前动画类型
    private JumpType currentAnimation = JumpType.SLIDING_SCREEN;

    public JumpType getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(JumpType jumpType) {
        currentAnimation = jumpType;
    }


    public Jump(BaseActivity activity) {
        weakActivity = new WeakReference<>(activity);
    }


    public void to(Intent intent) {
        weakActivity.get().startActivity(intent);
        setAnimaByType(currentAnimation);
    }

    public void to(Intent intent, JumpType jumpType) {
        intent.putExtra(jump, jumpType);
        weakActivity.get().startActivity(intent);
        setAnimaByType(jumpType);
    }

    public void to(Intent intent, int requestCode) {
        weakActivity.get().startActivityForResult(intent, requestCode);
        setAnimaByType(currentAnimation);
    }

    public void to(Intent intent, int requestCode, JumpType jumpType) {
        intent.putExtra(jump, jumpType);
        weakActivity.get().startActivityForResult(intent, requestCode);
        setAnimaByType(jumpType);
    }


    public void finish() {
        switch (currentAnimation) {
            case SLIDING_SCREEN:
                weakActivity.get().overridePendingTransition(R.anim.anim_sliding_close_next, R.anim.anim_sliding_close_last);
                break;
            case EJECT:
                weakActivity.get().overridePendingTransition( R.anim.anim_static, R.anim.anim_eject_close_last);
                break;
            case NORMAL:
                break;
        }
    }

    /**
     * 根据Activity设置的动画类型，来设置对应动画
     * overridePendingTransition(anim,anim)
     * 第一个参数为即将出现的Activity所做的动画，第二参数为即将离开的Activity所做的动画
     */
    private void setAnimaByType(JumpType type) {
        switch (type) {
            case SLIDING_SCREEN:
                weakActivity.get().overridePendingTransition(R.anim.anim_sliding_open_next, R.anim.anim_sliding_open_last);
                break;
            case EJECT:
                weakActivity.get().overridePendingTransition(R.anim.anim_eject_open_next, R.anim.anim_static);
                break;
            case NORMAL:
                break;
        }
    }


    public void onDestory() {
        if (weakActivity == null) {
            return;
        }
        weakActivity.clear();
        weakActivity = null;
    }


}
