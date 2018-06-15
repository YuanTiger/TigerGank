package com.my.gank.request;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Author：mengyuan
 * Date  : 2017/8/14下午4:54
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class Params {

    private HashMap<String, String> parmasMap;

    public Params() {
        parmasMap = new HashMap<>();
        //当有公共参数时，可在此处添加
    }

    public void put(String key, String value) {
        parmasMap.put(key, value);
    }

    public void put(String key, int value) {
        parmasMap.put(key, String.valueOf(value));
    }

    public void put(String key, float value) {
        parmasMap.put(key, String.valueOf(value));
    }

    /**
     * 将参数Add到请求头中
     *
     * @param builder 请求Builder
     */
    public void addToRequest(Request.Builder builder) {
        for (Map.Entry<String, String> entry : parmasMap.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
    }
}
