package com.my.gank.base;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * AUTHOR:       Yuan.Meng
 * E-MAIL:       mengyuanzz@126.com
 * CREATE-TIME:  16/8/31/下午5:20
 * DESC:
 */
public class BasePresenter<T extends BaseView> {

    protected WeakReference<T> viewWeakReference;

    public BasePresenter(T view) {
        viewWeakReference = new WeakReference<>(view);

    }

}
