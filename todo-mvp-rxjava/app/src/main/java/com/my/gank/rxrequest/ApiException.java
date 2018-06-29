package com.my.gank.rxrequest;

public class ApiException extends RuntimeException {

    private boolean isFailed;//true代表请求失败

    public ApiException(boolean error) {
        isFailed = error;
    }

    public String getApiExceptionMessage() {
        if (isFailed) {
            return "服务异常，请重试";
        }
        return "请求成功";
    }


}
