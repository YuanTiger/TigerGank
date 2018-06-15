package com.my.gank.base;

import java.io.Serializable;

/**
 * Author：mengyuan
 * Date  : 2017/8/11下午6:15
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class BaseBean implements Serializable {

    //接口请求结果，false为成功，true为出错
    private boolean error;


    public boolean isSuccess() {
        return !error;
    }
}
