package com.my.gank.presenter;

import com.my.gank.base.BasePresenter;
import com.my.gank.bean.GankItemBean;
import com.my.gank.bean.HomeAllBean;
import com.my.gank.contract.HomeContract;
import com.my.gank.rxrequest.HttpMethods;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Author：mengyuan
 * Date  : 2017/8/15上午11:09
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private CompositeDisposable mCompositeDisposable;


    public HomePresenter(HomeContract.View view) {
        super(view);


        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void requestHistoryList(int pageIndex) {
        Observer<List<HomeAllBean>> subscriber= new Observer<List<HomeAllBean>>(){

            @Override
            public void onError(Throwable e) {
                viewWeakReference.get().getAllDataFailed("");

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onNext(List<HomeAllBean> homeAllBeans) {
                viewWeakReference.get().getAllDataSuccess(homeAllBeans);
            }
        };
        HttpMethods.getInstance().getHistory(pageIndex,subscriber);
    }

    @Override
    public void requestTypeDataList(String typeName, int pageIndex) {
        Observer<List<GankItemBean>> subscriber= new Observer<List<GankItemBean>>(){

            @Override
            public void onError(Throwable e) {
                viewWeakReference.get().getTypeDataFailed("");
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<GankItemBean> gankItemBeans) {
                viewWeakReference.get().getTypeDataSuccess(gankItemBeans);
            }


        };
        HttpMethods.getInstance().getTypeData(typeName,pageIndex,subscriber);

    }
}
