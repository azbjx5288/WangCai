package com.wangcai.lottery.base.net;


import android.content.Context;
import android.support.annotation.IntDef;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.data.LoginCommand;
import com.google.gson.JsonSyntaxException;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.data.LoginCommand;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;

public class RestRequest implements Response.ErrorListener, Response.Listener<RestResponse> {
    private static final String TAG = RestRequest.class.getSimpleName();
    public static final long ONE_SECOND = 1000L;
    @IntDef({IDLE, RUNNING, DONE, QUIT})
    public @interface RestState {
    }

    public static final int IDLE = 0;
    public static final int RUNNING = 1;
    public static final int DONE = 2;
    public static final int QUIT = 3;

    @RestState
    protected int restState = IDLE;

    /**
     * 一般用于返回后的处理代码进switch操作
     */
    private int id;

    private RequestConfig requestConfig;

    private GsonRequest request;

    /**
     * 请求返回正常时的结果
     */
    private RestResponse restResponse;

    private Type typeOfResponse;

    /**
     * 调用者处理的网络请求回调
     */
    private RestCallback restCallback;

    /**
     * 错误值
     */
    private int errCode;

    /**
     * 错误描述
     */
    private String errDesc;

    protected final Context context;
    protected Object command;

    public RestRequest(Context context) {
        this.context = context;
    }

    protected void notifyStateChanged(@RestState int state) {
        if (restState == state) {
            return;

        }
        restState = state;
        if (restCallback != null) {
            restCallback.onRestStateChanged(this, restState);
        }
    }

    public GsonRequest createGsonRequest(Type type) {
        notifyStateChanged(RUNNING);
        typeOfResponse = type;
        request = new GsonRequest(requestConfig.method(), this);
        return request;
    }

    public GsonRequest createGsonRequest() {
        return createGsonRequest(new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{requestConfig.response()};
            }

            @Override
            public Type getOwnerType() {
                return null;
            }

            @Override
            public Type getRawType() {
                return RestResponse.class;
            }
        });
    }

    public void execute() {
        if (request == null) {
            Log.w(TAG, "execute: Request is null");
            return;
        }
        if (WangCaiApp.getNetStateHelper().isConnected()) {
            RestRequestManager.addRequest(request, request.getTag());
        } else {
            errCode = -3;
            errDesc = "网络连接错误";
            handleError();
            notifyStateChanged(QUIT);
        }
    }

    public RestResponse getCache() {
        Cache.Entry entry = RestRequestManager.getCache(request.getCacheKey());
        if (entry != null) {
            try {
                String json = new String(entry.data, HttpHeaderParser.parseCharset(entry.responseHeaders));
//                if(WangCaiApp.SERVER_TYPE){
                    json = new String(Base64.decode(json, Base64.DEFAULT), "UTF-8");
//                }
                return GsonHelper.newGson().fromJson(json, typeOfResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 实现{@link Response.Listener}，不允许子类修改，由{@link GsonRequest}回调
     */
    @Override
    public final void onResponse(RestResponse response) {
        if (null == response) {
            errCode = -2;
            errDesc = "无效网络数据";
            handleError();
            notifyStateChanged(DONE);
            return;
        }

        errCode = response.getErrno();
        errDesc = response.getError();
        if (errCode != 0) {
            handleError();
            notifyStateChanged(DONE);
            return;
        }

        if (restState != QUIT) {
            notifyStateChanged(DONE);
        }

        handleComplete(response);
    }

    /**
     * 实现{@link Response.ErrorListener}， 不允许子类修改，由{@link GsonRequest}回调
     */
    @Override
    public final void onErrorResponse(VolleyError error) {
        Throwable cause = error.getCause();
        if (error instanceof AuthFailureError || error instanceof ServerError || error instanceof NetworkError) {
            errCode = -3;
            errDesc = "网络连接错误";
        } else if (cause == null) {
            errCode = -1;
            errDesc = error.toString();
        } else if (cause instanceof ConnectException) {
            errCode = -3;
            errDesc = "网络连接错误";
        } else if (cause instanceof JsonSyntaxException) {
            errCode = -100;
            errDesc = "结果解析错误";
        } else if (cause instanceof TimeoutError) {
            errCode = -3;
            errDesc = "网络请求超时错误";
        } else {
            errCode = -1;
            errDesc = cause.getClass().getSimpleName();
        }

        Log.w(TAG, "onErrorResponse: error message: " + error.getMessage());
        handleError();
        notifyStateChanged(QUIT);
    }

    /**
     * 若还在运行就cancel掉
     */
    public void cancel() {
        if (request == null || restState == QUIT || restState == DONE) {
            return;
        }
        request.cancel();
        notifyStateChanged(QUIT);
    }

    /**
     * 运行在后台线程，子类可以进行一些结果的处理
     */
    protected void onBackgroundResult(NetworkResponse networkResponse, RestResponse response) {
    }

    private void handleComplete(RestResponse response) {
        restResponse = response;

        if (restState == QUIT) {
            return;
        }

        if (restCallback != null) {
            if (request != null && request.getTag() instanceof BaseFragment) {
                if (!((BaseFragment) request.getTag()).isAdded()) {
                    //BaseFragment已经退出，吃掉回调
                    return;
                }
            }

            if (restCallback.onRestComplete(this, restResponse)) {
                //已经处理完成，不再下发
                return;
            }
        }

        if (restState == QUIT) {
            return;
        }

        onComplete(response);
    }

    private void handleError() {
        if (restState == QUIT) {
            return;
        }

        boolean hasHandled = false;
        if (restCallback != null) {
            hasHandled = restCallback.onRestError(this, errCode, errDesc);
        }

        //错误已经处理或请求退出，不再下发
        if (hasHandled || restState == QUIT) {
            return;
        }

        hasHandled = onError();

        if (hasHandled || restState == QUIT) {
            return;
        }

//        handleErrorDefault();
    }

    /**
     * 错误的默认处理
     */
    private void handleErrorDefault() {
        if ( errCode == 3004 || errCode == 2016) { //用户没有登录，其值为服务器定义的
            /*SignOutCheck myWindow = new SignOutCheck(context);
            myWindow.showMyWindow(errCode);*/
            if (handleNotLogin()) {
                return;
            }
        }

        Toast.makeText(context, /*"出错了：[ "*/errDesc + "[" + errCode + "]", errCode < 0 ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }

    /**
     * 判断是否需要自动重新登录，并自动重新发送请求
     */
    private boolean handleNotLogin() {
        WangCaiApp.getUserCentre().saveSession(null);

        LoginCommand command = WangCaiApp.getUserCentre().createLoginCommand();
        if (command == null) {
            //不能自动登录
            return false;
        }
        Log.i(TAG, "handleNotLogin: try to auto login again");
        //尝试自动登录，并重发请求
        RestRequestManager.executeCommand(context, command, new RestCallback() {
            @Override
            public boolean onRestComplete(RestRequest request, RestResponse response) {
                Log.i(TAG, "handleNotLogin: try to auto login succeed");
                RestRequest.this.request = new GsonRequest(requestConfig.method(), RestRequest.this);
                execute();
                return false;
            }

            @Override
            public boolean onRestError(RestRequest request, int errCode, String errDesc) {
                Log.i(TAG, "handleNotLogin: try to auto login fail");
                Toast.makeText(context, /*"出错了：" + */RestRequest.this.errDesc + "[" + RestRequest.this.errCode + "]",
                        Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public void onRestStateChanged(RestRequest request, @RestState int state) {

            }
        }, 0, request.getTag());
        return true;
    }

    /**
     * 子类覆盖此方法实现错误的拦截处理
     */
    public boolean onError() {
        return false;
    }

    /**
     * 子类覆盖此方法实现默认结果处理，运行在主线程
     */
    public void onComplete(RestResponse response) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RestResponse getRestResponse() {
        return restResponse;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrDesc() {
        return errDesc;
    }

    public Context getContext() {
        return context;
    }

    public void setRestCallback(RestCallback restCallback) {
        this.restCallback = restCallback;
    }

    public Type getTypeOfResponse() {
        return typeOfResponse;
    }

    public Object getCommand() {
        return command;
    }

    public void setCommand(Object command) {
        this.command = command;
    }

    public RequestConfig getConfig() {
        return requestConfig;
    }

    public void setConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }
}
