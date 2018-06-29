package com.my.gank.rxrequest;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * RxJava请求封装
 * 主要包括：
 * 1、线程封装
 */
public class RxHelper {


    /**
     * 单例对象，只初始化一次，后期直接返回，节省资源
     * 初始化的对象没有指定泛型，因为不知道具体泛型类型
     * 最后在调用applySchedulers时根据指定泛型强转即可
     */
    public static final ObservableTransformer observable = new ObservableTransformer() {

        @Override
        public ObservableSource apply(Observable observable) {
            observable = observable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            return observable;
        }
    };

    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) observable;
    }
}
