package com.my.gank.request;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.my.gank.base.BaseApp;
import com.my.gank.base.BaseBean;
import com.my.gank.utils.LogUtil;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author：mengyuan
 * Date  : 2017/8/14下午1:39
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class RequestManager {
    private static final RequestManager ourInstance = new RequestManager();

    private OkHttpClient okHttpClient;


    public static RequestManager getInstance() {
        return ourInstance;
    }

    private RequestManager() {

        okHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(RequestConfig.TIME_OUT, TimeUnit.SECONDS)
                .build();

    }


    /**
     * 异步Get 无参数
     *
     * @param url      请求地址
     * @param callback 请求回调
     */
    public void getAsync(String url, MyRequestCallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    /**
     * 异步Get
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求回调
     */
    public void getAsync(String url, Params params, MyRequestCallback callback) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        //添加参数
        params.addToRequest(builder);
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }


    /**
     * 同步Get 无参数
     *
     * @param url      请求地址
     * @param callback 请求回调
     */
    public void getSync(String url, MyRequestCallback callback) {
    }

    /**
     * 同步Get
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求回调
     */
    public void getSync(String url, Params params, MyRequestCallback callback) {

    }

    /**
     * 异步Post 无参数
     *
     * @param url      请求地址
     * @param callback 请求回调
     */
    public void postAsync(String url, MyRequestCallback callback) {

    }

    /**
     * 异步Post
     *
     * @param url      请求地址
     * @param callback 请求回调
     */
    public void postAsync(String url, Params params, MyRequestCallback callback) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        //添加参数
        params.addToRequest(builder);
        Request request = builder.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    /**
     * 同步Post 无参数
     *
     * @param url      请求地址
     * @param callback 请求回调
     */
    public void postSync(String url, MyRequestCallback callback) {

    }

    /**
     * 同步Post
     *
     * @param url      请求地址
     * @param callback 请求回调
     */
    public void postSync(String url, Params params, MyRequestCallback callback) {

    }

    public abstract static class MyRequestCallback<T extends BaseBean> implements Callback {

        //okHttp请求结果是在子线程，该Handler用户切换主线程进行回调
        private Handler mainHandler = new Handler(BaseApp.context.getMainLooper());

        @Override
        public void onFailure(Call call, final IOException e) {

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    failed(e.getMessage());
                }
            });
        }

        @Override
        public void onResponse(Call call, final Response response) {
            final Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        success(JSON.parseObject(response.body().string(), entityClass));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public abstract void success(T data);

        public abstract void failed(String message);
    }
}
