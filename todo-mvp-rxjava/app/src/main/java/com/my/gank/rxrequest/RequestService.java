package com.my.gank.rxrequest;

import com.my.gank.base.BaseRxBean;
import com.my.gank.bean.GankItemBean;
import com.my.gank.bean.HomeAllBean;
import com.my.gank.bean.HomeDetailItemBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface RequestService {


    @Headers("Cache-Control: max-stale=" + Integer.MAX_VALUE)//如果该接口需要缓存，设置缓存时间，不需要则不设置
    @GET("data/{type}/10/{pageNum}")
    Observable<BaseRxBean<List<GankItemBean>>> getTypeData(@Path("type") String type, @Path("pageNum") int pageNum);//获取制定类型的数据列表

    @Headers("Cache-Control: max-stale=" + Integer.MAX_VALUE)
    @GET("history/content/10/{pageNum}")
    Observable<BaseRxBean<List<HomeAllBean>>> getHistory(@Path("pageNum") int pageNum);//获取之前天数数据

    @GET("day/{date}")
    Observable<BaseRxBean<HomeDetailItemBean>> getDetail(@Path("date") String date);//获取某一天的详情数据
}