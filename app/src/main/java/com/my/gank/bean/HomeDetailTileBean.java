package com.my.gank.bean;

import com.my.gank.Constant;

/**
 * Author：mengyuan
 * Date  : 2017/8/15下午6:10
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class HomeDetailTileBean implements HomeDetailView {

    public String title;


    public HomeDetailTileBean(String title){
        this.title = title;
    }

    @Override
    public int getType() {
        return Constant.TYPE_HOME_DETAIL_TITLE;
    }
}
