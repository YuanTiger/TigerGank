package com.my.gank.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtils {

    /**
     * 获取内存缓存目录
     *
     * @return 缓存目录文件夹 或 null（创建目录文件失败）
     * 注：该方法获取的目录是能供当前应用自己使用，外部应用没有读写权限，如 系统相机应用
     */
    public static File getInternalCacheDirectory(Context context) {

        return context.getCacheDir();// /data/data/app_package_name/cache
    }
}