package com.my.gank.rxrequest;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.my.gank.utils.LogUtil;
import com.my.gank.utils.NetUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Create By MengYuan on 2018/06/25
 * <p>
 * 请求缓存封装
 * <p>
 * 使用okHttp的拦截器进行缓存策略
 * <p>
 * Cache-Control：缓存控制器，存在于请求的Header、Response中，okHttp自带的缓存策略会去解析Response中的Cache-Control，从而进行自动缓存，缓存有效期为多久
 * max-age:缓存有效时长，超过该时间，缓存失效，如果在该时间内，就算有网络，也会走缓存
 * max-stale：当缓存过期后，还可以访问的有效期限。如果没有网络时，选择使用缓存，当缓存已过期时，会判断过期时长是否超过该值，如果超过了，则缓存才真正意义的失效。
 */
public class OkHttpCache implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {


        Request request = chain.request();

        //如果有网络，强制使用网络
        if (NetUtils.isConnected()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
        }
        //如果没有网络，设置强制使用缓存
        else{
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        //获取Header中的Cache-Control
        //Cache-Control在ApiService中使用注解配置
        String cacheString = request.cacheControl().toString();
        //获取响应头
        Response response = chain.proceed(request);
        //如果有网络
        if (NetUtils.isConnected()) {
            return response.newBuilder()
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public,max-age=5")
                    .build();
        } else {
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .header("Cache-Control", cacheString)
                    .build();
        }

    }
}
