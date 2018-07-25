package com.wangcai.lottery.base.net;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.wangcai.lottery.BuildConfig;
import com.google.gson.JsonSyntaxException;
import com.wangcai.lottery.app.WangCaiApp;


import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * JSON请求的Volley适配器，返回结果使用Gson格式化为指定对象。
 * <p/>
 * Notice: 大文件上传下载不适用该请求
 */
public final class GsonRequest extends Request<RestResponse> {
    private static final String TAG = GsonRequest.class.getSimpleName();

    private final static boolean DEBUG = BuildConfig.DEBUG;

    private static RetryPolicy sRetryPolicy = new DefaultRetryPolicy(30 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT );

    private final RestRequest request;
    private long startAtTime;
    private String cacheKey;

    /**
     * 发起请求
     * @param method        参考{@link Method#POST}、{@link Method#GET}等
     * @param request       请求参数封装对象
     */
    public GsonRequest(int method, RestRequest request) {
        super(method, convertUrl(method, request), request);
        this.request = request;

        setRetryPolicy(sRetryPolicy);
        cacheKey = BuildConfig.VERSION_CODE
                + WangCaiApp.getUserCentre().getUserID()
                + ":" + request.getConfig().version()
                + ":" + super.getCacheKey();
        if (DEBUG) {
            startAtTime = System.currentTimeMillis();
            String requestCommand= GsonHelper.toJson(request.getCommand());
            String urlTag;
            if (method == Method.GET) {
                urlTag = request.getConfig().api();
            } else {
                urlTag = getUrl();
            }
            Formater2.outJsonObject("GsonRequest_REQUEST", "", urlTag, requestCommand);
        }
    }

    /**
     * 由api拼上服务器host，若是GET模式，将参数拼到url
     */
    private static String convertUrl(int method, RestRequest request) {
        if (method != Method.GET) {
            return WangCaiApp.getUserCentre().getUrl(request.getConfig().api());
        }

        String baseUrl = WangCaiApp.getUserCentre().getUrl(request.getConfig().api());
        StringBuilder encodedParams = new StringBuilder(baseUrl);
        if (!baseUrl.contains("?")) {
            encodedParams.append("?");
        }
        if (encodedParams.charAt(encodedParams.length() - 1) != '?') {
            encodedParams.append('&');
        }

        Map<String, String> params = GsonHelper.convert2Map(GsonHelper.convert2gson(request.getCommand()));
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (null == entry.getValue()) {
                    continue;
                }
                encodedParams.append(entry.getKey());
                encodedParams.append('=');
                encodedParams.append(entry.getValue());
                encodedParams.append('&');
            }

            String encoded=encodedParams.toString();
            if(encoded!=null&&encoded.length()>0){
                String[] sp = encoded.split("\\?");
                if(sp.length>=1){
                    String sign="";
                    if(sp[1].substring(sp[1].length()-1,sp[1].length()).equals("&")){
                        sign=sp[1].substring(0,sp[1].length()-1);
                    }else{
                        sign=sp[1];
                    }
                    encodedParams.append("sign="+ DigestUtils.md5Hex(URLEncoder.encode(sign, "UTF-8")+ WangCaiApp.getUserCentre().getKeyApiKey()));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i(TAG,encodedParams.toString());
        return encodedParams.toString();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return GsonHelper.toJson(request.getCommand()).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return super.getBody();
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=UTF-8";
    }

    @Override
    public String getCacheKey() {
        return cacheKey;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> map = new HashMap<>();
        String cookie = WangCaiApp.getUserCentre().getSession();
        if (cookie != null) {
            map.put("Cookie", cookie);
        }
        map.put("User-Agent", "Android App");
        return map;
    }

    @Override
    protected void deliverResponse(RestResponse response) {
        request.onResponse(response);
    }

    /**
     * 网络请求结果解析
     * @param response Response from the network
     * @return
     */
    @Override
    protected Response<RestResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//            if(WangCaiApp.SERVER_TYPE) {
                json = new String(Base64.decode(json, Base64.DEFAULT), "UTF-8");//测试服需要注释这一行
//            }
            if (DEBUG) {
                String urlTag;
                if (getMethod() == Method.GET) {
                    urlTag = request.getConfig().api();
                } else {
                    urlTag = getUrl();
                }
                String name = String.format("call use %dms for %s", System.currentTimeMillis() - startAtTime, urlTag);
                Formater2.outJsonObject("GsonRequest_RESPONSE", "", name, json);
            }

            RestResponse restResponse = GsonHelper.fromJson(json, request.getTypeOfResponse());
            request.onBackgroundResult(response, restResponse);
            return Response.success(restResponse, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            Log.d("GsonRequest", e.toString());
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            Log.d("GsonRequest", e.toString());
            return Response.error(new ParseError(e));
        }
    }

    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    static class MapKeyComparator implements Comparator<String> {
        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }
}