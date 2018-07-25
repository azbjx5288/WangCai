package com.wangcai.lottery.util;

import android.util.Log;

import com.wangcai.lottery.base.net.RequestConfig;

import java.lang.reflect.Type;

/**
 * Created by Ace.Dong on 2018/3/23.
 */

public class SignUtils {
    private static final String TAG = SignUtils.class.getSimpleName();

    public static String delSignMd5HexURLEncoder(Class tClass){
        // 此处要用反射将字段中的注解解析出来
        Class<Type> clz = tClass;
        // 判断类上是否有次注解
        boolean clzHasAnno = tClass.isAnnotationPresent(RequestConfig.class);
        if (clzHasAnno) {
            // 获取类上的注解
            RequestConfig annotation = clz.getAnnotation(RequestConfig.class);
            // 输出注解上的属性
            int method = annotation.method();
            Log.e(TAG,clz.getName() + " method = " + method );
        }
        return null;
    }
}
