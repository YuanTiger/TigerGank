package com.my.gank.rxrequest;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.my.gank.BuildConfig;
import com.my.gank.Constant;
import com.my.gank.base.BaseApp;
import com.my.gank.base.BaseRxBean;
import com.my.gank.bean.GankItemBean;
import com.my.gank.bean.HomeAllBean;
import com.my.gank.bean.HomeDetailItemBean;


import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.functions.Function;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpMethods {


    private static final int DEFAULT_TIMEOUT = 5;//超时时间，默认5秒


    private RequestService requestService;//接口地址

    private HttpMethods() {
        //设置缓存路径
        File cacheFile = BaseApp.context.getCacheDir();
        //设置缓存文件大小
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 5);
        //okHttp初始化
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)//超时时长
                .cache(cache)//缓存路径&大小
                .addNetworkInterceptor(new OkHttpCache())//缓存策略
                .addInterceptor(new OkHttpCache())//缓存策略
                .addInterceptor(new PublicParams());//公共参数

        //如果是Debug，显示接口日志
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        //Retrofit初始化
        requestService = new Retrofit.Builder()
                .client(builder.build())//网络请求库的设置，okHttp
                .addConverterFactory(GsonConverterFactory.create())//json解析库的设置，Gson
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//回调库的设置，RxJava
                .baseUrl(Constant.URL.BASE_URL)//基础请求地址
                .build()
                .create(RequestService.class);

    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }


    private class HttpRequestFunc<T> implements Function<BaseRxBean<T>, T> {

        @Override
        public T apply(BaseRxBean<T> tBaseRxBean) {
            if (tBaseRxBean.error) {
                throw new ApiException(true);
            }
            return tBaseRxBean.results;
        }
    }


    public void getTypeData(String type, int pageNum, Observer<List<GankItemBean>> subscriber) {

        requestService.getTypeData(type, pageNum)
                .map(new HttpRequestFunc<List<GankItemBean>>())
                .compose(RxHelper.<List<GankItemBean>>applySchedulers())
                .subscribe(subscriber);
    }

    public void getHistory(int pageNum, Observer<List<HomeAllBean>> subscriber) {

        requestService.getHistory(pageNum)
                .map(new HttpRequestFunc<List<HomeAllBean>>())
                .compose(RxHelper.<List<HomeAllBean>>applySchedulers())
                .subscribe(subscriber);
    }

   public void getDetail(String date, Observer<HomeDetailItemBean> subscriber) {

        requestService.getDetail(date)
                .map(new HttpRequestFunc<HomeDetailItemBean>())
                .compose(RxHelper.<HomeDetailItemBean>applySchedulers())
                .subscribe(subscriber);
    }


}
