package com.my.gank.bean;

import com.my.gank.base.BaseBean;

import java.util.List;

/**
 * Author：mengyuan
 * Date  : 2017/8/14下午4:14
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class HomeBean extends BaseBean {


    public List<ResultsBean> results;


    public static class ResultsBean {
        /**
         * _id : 598d1bd0421aa97de5c7ca57
         * createdAt : 2017-08-11T10:52:00.468Z
         * desc : 100篇精选干货，感谢你与码个蛋共同成长（含5重福利）
         * publishedAt : 2017-08-11T14:05:53.749Z
         * source : web
         * type : Android
         * url : https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247486370&idx=1&sn=1d922d8f375cdcec20bd31cbc2f0e418&chksm=96cdaaefa1ba23f90bc4975fa9dc22a30f7b5c343b3a0924bfcabe11df96e5955ccb08191ecf#rd
         * used : true
         * who : 陈宇明
         * images : ["http://img.gank.io/17933b35-f195-42cf-891f-0de3db555292"]
         */

        public String _id;
        //文章日期
        public String createdAt;
        //文章标题
        public String desc;
        public String publishedAt;
        //请求来源
        public String source;
        //文章类型
        public String type;
        //文章地址
        public String url;
        //
        public boolean used;
        //作者
        public String who;
        //配图
        public List<String> images;

    }
}
