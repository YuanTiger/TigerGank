package com.my.gank.base;

import java.lang.ref.WeakReference;

/**
 * MVP
 * P extends BasePresenter
 */
public abstract class BasePresenter<MvpView extends IBaseView> {

    protected WeakReference<MvpView> viewWeakReference;

    public BasePresenter(MvpView mvpView) {
        viewWeakReference = new WeakReference<>(mvpView);
    }

}
