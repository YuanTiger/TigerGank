package com.my.gank.contract;

import com.my.gank.base.BaseView;
import com.my.gank.bean.HomeAllBean;
import com.my.gank.bean.TypeGankBean;
import com.my.gank.request.RequestManager;

/**
 * Author：mengyuan
 * Date  : 2017/8/15上午11:04
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public interface HomeContract {

    interface View extends BaseView {
        void getAllDataSuccess(HomeAllBean data);

        void getAllDataFailed(String message);


        void getTypeDataSuccess(TypeGankBean data);

        void getTypeDataFailed(String message);

    }

    interface Presenter {
        void requestHistoryList(int pageIndex);

        void requestTypeDataList(String typeName, int pageIndex);
    }

    interface Model {
        void requestHistoryList(int pageIndex, RequestManager.MyRequestCallback<HomeAllBean> callback);

        void requestTypeDataList(String typeName, int pageIndex, RequestManager.MyRequestCallback<TypeGankBean> callback);

    }
}
