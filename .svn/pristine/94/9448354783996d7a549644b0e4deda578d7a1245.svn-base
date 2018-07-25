package com.wangcai.lottery.base.net;

import com.android.volley.Request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Alashi on 2015/12/23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RequestConfig {
    /** 网络请求类型，{@link Request.Method} */
    int method() default Request.Method.GET;
    /** 服务器API，不包含basePath */
    String api();
    /** 正常访问返回数据的bean类，参考{@link RestResponse#data} */
    Class response() default JsonString.class;
    Class<? extends RestRequest> request() default RestRequest.class;
    /** 返回结果版本，防止API变更时，读取cache解析出错 */
    int version() default 0;
}
