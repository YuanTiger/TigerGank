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
        //无数据
        int NO_DATA = 4;
        //正常状态
        int NORMAL = 5;
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

    public interface URL {
        String BASE_URL = "http://gank.io/api/";
        //首页
        String HOME = BASE_URL + "data/Android/10/1";
    }
}
