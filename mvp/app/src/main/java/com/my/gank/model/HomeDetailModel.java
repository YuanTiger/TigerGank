package com.my.gank.model;

import com.my.gank.Constant;
import com.my.gank.bean.HomeDetailItemBean;
import com.my.gank.contract.HomeDetailContract;
import com.my.gank.request.RequestManager;

/**
 * Author：mengyuan
 * Date  : 2017/8/15上午11:10
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class HomeDetailModel implements HomeDetailContract.Model {


    @Override
    public void requestDetail(String date,RequestManager.MyRequestCallback<HomeDetailItemBean> callback) {
        String url = Constant.URL.HISTORY_DETAIL + date;
        RequestManager.getInstance().getAsync(url, callback);
    }
}
