package com.my.gank.bean;

import com.my.gank.Constant;

import java.io.Serializable;
import java.util.List;

/**
 * Author：mengyuan
 * Date  : 2017/8/15下午4:58
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class GankItemBean implements Serializable,HomeDetailView {

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
    //配图，可能为空
    public List<String> images;

    @Override
    public int getType() {
        return Constant.TYPE_HOME_DETAIL_ITEM;
    }
}
