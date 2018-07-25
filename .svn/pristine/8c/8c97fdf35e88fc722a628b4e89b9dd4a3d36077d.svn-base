package com.wangcai.lottery.base.net;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.fragment.LoadingDialog;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by ACE-PC on 2016/11/21.
 */

public class VolleyRequest {
    private static final String TAG = VolleyRequest.class.getSimpleName();
    private static RetryPolicy sRetryPolicy = new DefaultRetryPolicy(30 * 1000, 3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    public static StringRequest stringRequest;

    /**
     * post请求（带有map传递参数）
     *
     * @param url
     * @param tag
     * @param params
     * @param vif
     */
    public static void requestPost(String url, String tag, final Map<String, String> params, VolleyInterface vif) {
        WangCaiApp.getHttpQueue().cancelAll(tag);
        LoadingDialog.show(vif.context);
        stringRequest = new StringRequest(Request.Method.POST, url, vif.loadingListener(), vif.errorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String parsed;
                try {
                    parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                } catch (UnsupportedEncodingException e) {
                    parsed = new String(response.data);
                }
                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        stringRequest.setRetryPolicy(sRetryPolicy);
        stringRequest.setTag(tag);
        stringRequest.setShouldCache(true);
        WangCaiApp.getHttpQueue().add(stringRequest);
        WangCaiApp.getHttpQueue().start();
    }
}
