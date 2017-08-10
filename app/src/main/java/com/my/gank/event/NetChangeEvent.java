package com.my.gank.event;

/**
 * Author：mengyuan
 * Date  : 2017/8/10上午10:48
 * E-Mail:mengyuanzz@126.com
 * Desc  :
 */

public class NetChangeEvent {

    public boolean isConnection;

    public NetChangeEvent(boolean isConnection){
        this.isConnection = isConnection;
    }
}
