package com.my.gank.contract;

import com.my.gank.base.BaseView;
import com.my.gank.bean.GankItemBean;
import com.my.gank.bean.HomeAllBean;

import java.util.List;

/**
 * Author：mengyuan
 * Date  : 2017/8/15上午11:04
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public interface HomeContract {

    interface View extends BaseView {
        void getAllDataSuccess(List<HomeAllBean> data);

        void getAllDataFailed(String message);


        void getTypeDataSuccess(List<GankItemBean> data);

        void getTypeDataFailed(String message);

    }

    interface Presenter {
        void requestHistoryList(int pageIndex);

        void requestTypeDataList(String typeName, int pageIndex);
    }
}
