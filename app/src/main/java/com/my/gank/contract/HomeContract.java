package com.my.gank.contract;

import com.my.gank.base.BaseView;
import com.my.gank.bean.HomeAllBean;
import com.my.gank.request.RequestManager;

/**
 * Author：mengyuan
 * Date  : 2017/8/15上午11:04
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public interface HomeContract {

    interface View extends BaseView {
        void getDataSuccess(HomeAllBean data);

        void getDataFailed(String message);


    }

    interface Presenter {
        void requestHistoryList(int pageIndex);

    }

    interface Model {
        void requestHistoryList(int pageIndex, RequestManager.MyRequestCallback<HomeAllBean> callback);

    }
}
