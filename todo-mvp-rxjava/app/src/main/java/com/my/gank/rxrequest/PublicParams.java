package com.my.gank.rxrequest;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 公共参数封装
 */
public class PublicParams implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        HttpUrl.Builder authorizedUrlBuilder = request.url()
                .newBuilder()
                //添加统一参数 如手机唯一标识符,token等
                .addQueryParameter("key1", "value1")
                .addQueryParameter("key2", "value2");

        Request newRequest = request.newBuilder()
                //对所有请求添加请求头
                .header("headerTest1", "1")
                .addHeader("headerTest2", "2")
                .method(request.method(), request.body())
                .url(authorizedUrlBuilder.build())
                .build();
        return chain.proceed(newRequest);
    }
}
