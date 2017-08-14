package com.my.gank.base;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * MVP
 * P extends BasePresenter
 */
public class BasePresenter<T extends BaseView> {

    protected WeakReference<T> viewWeakReference;

    public BasePresenter(T mvpView) {
        viewWeakReference = new WeakReference<>(mvpView);
    }

}
