package com.wangcai.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.FragmentLauncher;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.data.ActivityData;
import com.wangcai.lottery.material.ConstantInformation;

import butterknife.BindView;

/**
 * 活动页面
 * Created by ACE-PC on 2017/6/22.
 */

public class AdvertisementFragment extends BaseFragment{
    private static final String TAG = AdvertisementFragment.class.getSimpleName();

    private String url = "";
    private String titleText = "";

    @BindView(android.R.id.home)
    ImageButton home;
    @BindView(android.R.id.title)
    TextView title;
    @BindView(R.id.payWebView)
    WebView appView;
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;

    public static void launch(BaseFragment fragment, ActivityData activityData) {
        if(activityData==null){
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("url", activityData.getUrl());
        bundle.putString("title", activityData.getTitle());
        FragmentLauncher.launch(fragment.getActivity(), AdvertisementFragment.class, bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, "活动加奖", R.layout.fragment_activity,true,true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.titleText = getArguments().getString("title");
        this.url = getArguments().getString("url");
        if(TextUtils.isEmpty(url)){
            this.url = "file:///android_asset/web/Error.html";
        }else {
            this.url = ConstantInformation.isUrl(url + "?user_id=" + WangCaiApp.getUserCentre().getUserID() + "&token=" + WangCaiApp.getUserCentre().getUserInfo().getToken());
        }
        init();
    }

    private void init() {
        setTitle(titleText);
        WebSettings settings = appView.getSettings();
        if (WangCaiApp.getNetStateHelper().isConnected()) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }
        saveData(settings);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);          //允许缩放
        settings.setBuiltInZoomControls(true);  //原网页基础上缩放
        settings.setUseWideViewPort(true);      //任意比例缩放
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片

        appView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(loadingLayout!=null) {
                    loadingLayout.setVisibility(View.GONE);
                    appView.setVisibility(View.VISIBLE);
                }
            }
        });

        appView.loadUrl(url);
    }

    /**
     * HTML5数据存储
     */
    private void saveData(WebSettings mWebSettings) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        String appCachePath = getActivity().getCacheDir().getAbsolutePath();
        mWebSettings.setAppCachePath(appCachePath);
    }


    @Override
    public void onDestroyView() {
        if (appView != null) {
            appView.clearHistory();
            appView.destroy();
        }
        super.onDestroyView();
    }
}
