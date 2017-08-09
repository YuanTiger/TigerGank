package com.my.gank;

/**
 * Author：mengyuan
 * Date  : 2017/8/9下午3:20
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class Constant {

    //页面状态
    public interface PageState {
        //页面Loading中
        int LOADING = 1;
        //Dialog Loading中
        int LOADING_DIALOG = 2;
        //无网
        int NO_NET = 3;
        //在页面初始化成功后，网络断开
        int NET_BREAK_OFF = 4;
        //无数据
        int NO_DATA = 5;
        //正常状态
        int NORMAL = 6;
    }


    //页面初始化风格
    public interface PageStyle {
        //进入页面时使用全局Loading来等待
        int LOADING_PAGE = 1;
        //进入页面时使用Dialog来等待
        int LOADING_DIALOG = 2;
        //进入页面时无需等待，用于没有接口请求的页面
        int NO_LOADING = 3;
    }
}
