package com.my.gank.presenter;

import com.my.gank.base.BasePresenter;
import com.my.gank.bean.HomeDetailItemBean;
import com.my.gank.contract.HomeDetailContract;
import com.my.gank.model.HomeDetailModel;
import com.my.gank.request.RequestManager;

/**
 * Author：mengyuan
 * Date  : 2017/8/15上午11:09
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class HomeDetailPresenter extends BasePresenter<HomeDetailContract.View> implements HomeDetailContract.Presenter {

    private HomeDetailContract.Model model;


    public HomeDetailPresenter(HomeDetailContract.View view) {
        super(view);

        model = new HomeDetailModel();

    }


    @Override
    public void requestDetail(String date) {
        model.requestDetail(date, new RequestManager.MyRequestCallback<HomeDetailItemBean>() {
            @Override
            public void success(HomeDetailItemBean data) {

                viewWeakReference.get().getDataSuccess(data);
            }

            @Override
            public void failed(String message) {
                viewWeakReference.get().getDataFailed(message);
            }
        });
    }
}
