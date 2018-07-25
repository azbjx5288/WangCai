package com.wangcai.lottery.base.net;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wangcai.lottery.fragment.LoadingDialog;

/**
 * Created by ACE-PC on 2016/11/21.
 */

public abstract class VolleyInterface {

    public Context context;
    public static Response.Listener<String> Listener;
    public static Response.ErrorListener ErrorListener;

    public VolleyInterface(Context context, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this.context = context;
        this.Listener = listener;
        this.ErrorListener = errorListener;
    }

    public abstract void onSuccess(String result);

    public abstract void onError(VolleyError error);

    /**
     * 成功回调
     * @return Response.Listener<String>
     */
    public Response.Listener<String> loadingListener() {
        Listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onSuccess(response);
            }
        };
        return Listener;
    }

    /**
     * 失败回调
     * @return
     */
    public Response.ErrorListener errorListener() {
        ErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onError(error);
            }
        };
        return ErrorListener;
    }
}
