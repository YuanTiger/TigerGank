package com.my.gank.presenter;

import com.my.gank.base.BasePresenter;
import com.my.gank.bean.HomeDetailItemBean;
import com.my.gank.contract.HomeDetailContract;
import com.my.gank.rxrequest.ApiException;
import com.my.gank.rxrequest.HttpMethods;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author：mengyuan
 * Date  : 2017/8/15上午11:09
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class HomeDetailPresenter extends BasePresenter<HomeDetailContract.View> implements HomeDetailContract.Presenter {


    public HomeDetailPresenter(HomeDetailContract.View view) {
        super(view);
    }


    @Override
    public void requestDetail(String date) {

        Observer<HomeDetailItemBean> observer = new Observer<HomeDetailItemBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(HomeDetailItemBean homeDetailItemBean) {
                viewWeakReference.get().getDataSuccess(homeDetailItemBean);

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ApiException) {
                    viewWeakReference.get().getDataFailed(((ApiException) e).getApiExceptionMessage());
                }

            }

            @Override
            public void onComplete() {

            }
        };

        HttpMethods.getInstance().getDetail(date, observer);

    }
}
