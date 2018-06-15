package com.my.gank.presenter;

import com.my.gank.base.BasePresenter;
import com.my.gank.bean.HomeAllBean;
import com.my.gank.bean.TypeGankBean;
import com.my.gank.contract.HomeContract;
import com.my.gank.model.HomeModel;
import com.my.gank.request.RequestManager;

/**
 * Author：mengyuan
 * Date  : 2017/8/15上午11:09
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private HomeContract.Model model;


    public HomePresenter(HomeContract.View view) {
        super(view);

        model = new HomeModel();
    }

    @Override
    public void requestHistoryList(int pageIndex) {
        model.requestHistoryList(pageIndex, new RequestManager.MyRequestCallback<HomeAllBean>() {
            @Override
            public void success(HomeAllBean data) {

                viewWeakReference.get().getAllDataSuccess(data);
            }

            @Override
            public void failed(String message) {
                viewWeakReference.get().getAllDataFailed(message);
            }
        });
    }

    @Override
    public void requestTypeDataList(String typeName, int pageIndex) {
        model.requestTypeDataList(typeName, pageIndex, new RequestManager.MyRequestCallback<TypeGankBean>() {
            @Override
            public void success(TypeGankBean data) {
                viewWeakReference.get().getTypeDataSuccess(data);
            }

            @Override
            public void failed(String message) {
                viewWeakReference.get().getTypeDataFailed(message);
            }
        });
    }
}
