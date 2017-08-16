package com.my.gank;

/**
 * Author：mengyuan
 * Date  : 2017/8/9下午3:20
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public interface Constant {

    //某日详情页条目TYPE
    int TYPE_HOME_DETAIL_TITLE = 31;
    int TYPE_HOME_DETAIL_ITEM = 32;

    //页面状态
    interface PageState {
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
    interface PageStyle {
        //进入页面时使用全局Loading来等待
        int LOADING_PAGE = 1;
        //进入页面时使用Dialog来等待
        int LOADING_DIALOG = 2;
        //进入页面时无需等待，用于没有接口请求的页面
        int NO_LOADING = 3;
    }

    interface URL {
        String BASE_URL = "http://gank.io/api/";
        //图片大小
        String imageSize = "?imageView2/0/w/250";


        //所有Gank: /数据类型/一页数量/页码
        String TYPE_GAK_DATA = BASE_URL + "data/";
        //获取某几日的Gank数据，10代表一页的数据量，1代表页码
        String HISTORY_LIST = BASE_URL + "history/content/10/";//+pageIndex
        //获取特定日期的网站数据，需拼接日期
        String HISTORY_DETAIL = BASE_URL + "day/";//2017/8/11
        //获取有Gank消息的具体日期
        String HISTORY_DAY = BASE_URL + "day/history";
    }
}
