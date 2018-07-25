package com.wangcai.lottery.base.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.google.gson.reflect.TypeToken;

import java.io.File;

public class RestRequestManager {
    private static final String TAG = RestRequestManager.class.getSimpleName();
    /** 网络缓存大小 */
    private static int DISK_BASED_CACHE_SIZE = 30 * 1024 * 1024;
    private static RequestQueue sRequestQueue = newRequestQueue();

    private RestRequestManager(){
    }

    private static Cache openCache() {
        File cacheRoot = new File(com.wangcai.lottery.app.WangCaiApp.getInstance().getExternalCacheDir().getAbsolutePath()
                + File.separator + "network");
        return new DiskBasedCache(cacheRoot, DISK_BASED_CACHE_SIZE);
    }

    private static RequestQueue newRequestQueue() {
        RequestQueue requestQueue = new RequestQueue(openCache(), new BasicNetwork(new HurlStack()));
        requestQueue.start();
        return requestQueue;
    }

    public static void addRequest(Request request, Object tag) {
        if (request == null) {
            return;
        }
        if (tag != null) {
            request.setTag(tag);
        }
        sRequestQueue.add(request);
    }

    public static RestRequest createRequest(Context context, Object command,
                                                RestCallback callback, int id, Object tag) {
        RestRequest requestBase = create(context, command, callback, id);
        if (requestBase != null) {
            requestBase.createGsonRequest().setTag(tag);
        }
        return requestBase;
    }

    public static RestRequest createRequest(Context context, Object command, TypeToken typeToken, RestCallback callback, int id, Object tag) {
        RestRequest requestBase = create(context, command, callback, id);
        if (requestBase != null) {
            requestBase.createGsonRequest(typeToken.getType()).setTag(tag);
        }
        return requestBase;
    }
    /**
     *
     * @param context 上下文，一般用Activity的
     * @param command 网络请求的参数类
     * @param callback 回调处理
     * @param id 一般用于callback里面进行switch操作
     * @param tag 在需要对request进行cancel时传递的参数
     * @return
     */
    public static RestRequest executeCommand(Context context, Object command,
                                                 RestCallback callback, int id, Object tag) {
        RestRequest requestBase = createRequest(context, command, callback, id, tag);
        if (requestBase != null) {
            requestBase.execute();
        }
        return requestBase;
    }

    /**
     *
     * @param context 上下文，一般用Activity的
     * @param command 网络请求的参数类
     * @param typeToken 类似: new TypeToken&lt;RestResponse&lt;ArrayList&lt;XXX&gt;&gt;&gt;(){}
     * @param callback 回调处理
     * @param id 一般用于callback里面进行switch操作
     * @param tag 在需要对request进行cancel时传递的参数
     * @return
     */
    public static RestRequest executeCommand(Context context, Object command, TypeToken typeToken,
                                             RestCallback callback, int id, Object tag) {
        RestRequest requestBase = createRequest(context, command, typeToken, callback, id, tag);
        if (requestBase != null) {
            requestBase.execute();
        }
        return requestBase;
    }

    private static RestRequest create(Context context, Object command, RestCallback callback, int id) {
        RequestConfig requestConfig = command.getClass().getAnnotation(RequestConfig.class);
        if (requestConfig == null) {
            Log.e(TAG, "executeCommand: can't find config of " + command.getClass() , new Throwable());
            return null;
        }
        RestRequest requestBase;
        if (requestConfig.request() == RestRequest.class) {
            requestBase = new RestRequest(context);
        } else {
            try {
                requestBase = requestConfig.request().getConstructor(Context.class).newInstance(context);
            } catch (Exception e) {
                Log.e(TAG, "executeCommand: can't create RestRequest for " + command.getClass() , e);
                return null;
            }
        }

        requestBase.setCommand(command);
        requestBase.setConfig(requestConfig);
        requestBase.setRestCallback(callback);
        requestBase.setId(id);

        return requestBase;
    }

    /** 清理指定tag的请求，如界面退出时，清理掉当前界面发的请求 */
    public static void cancelAll(Object tag) {
        sRequestQueue.cancelAll(tag);
    }

    /** 清理区别请求，一般用于账号退出登录 */
    public static void cancelAll() {
        sRequestQueue.cancelAll((Request<?> request) -> {
                return true;
        });
    }

    public static Cache.Entry getCache(String key) {
        return sRequestQueue.getCache().get(key);
    }
}
