package com.wangcai.lottery.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.wangcai.lottery.BuildConfig;
import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.data.GetThirdGabbyCommand;
import com.wangcai.lottery.data.GetThirdGabbyUrl;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.BarHide;

import butterknife.BindView;

/**
 * Created by ACE-PC on 2017/7/12.
 * GA游戏页面
 */

public class GameGa2Fragment extends BaseFragment {
    private static final String TAG = GameGa2Fragment.class.getSimpleName();

    @BindView(R.id.gagame_WebView)
    WebView appView;
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "GA游戏", R.layout.fragment_game_ga, false, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void init(String url) {
        loadingLayout.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        WebSettings settings = appView.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportMultipleWindows(true);
        settings.setAppCacheEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);
        appView.addJavascriptInterface(new JsInterface(), "androidJs");
        appView.setOverScrollMode(WebView.OVER_SCROLL_ALWAYS);
        appView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (loadingLayout != null)
                    loadingLayout.setVisibility(View.GONE);
                appView.setVisibility(View.VISIBLE);
            }
        });
        appView.loadUrl(url);
        appView.setVisibility(View.VISIBLE);
    }

    private class JsInterface {
        @JavascriptInterface
        public void finishGame() {
            getActivity().finish();
        }

        @JavascriptInterface
        public void goToWebView(String url) {
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            launchFragment(WebViewFragment.class, bundle);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionBar.hideBar(BarHide.FLAG_HIDE_BAR).init();
        if (appView != null) {
            appView.onResume();
        }
        GetThirdGabbyLoad();
    }

    @Override
    public void onPause() {
        super.onPause();
        Intent i = new Intent("com.android.music.musicservicecommand");
        i.putExtra("command", "pause");
        getActivity().sendBroadcast(i);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            appView.loadUrl("about:blank");
            appView.onPause(); // 暂停网页中正在播放的视频
        }
    }

    @Override
    public void onDestroy() {
        if (appView != null) {
            appView.destroy();
        }
        super.onDestroy();
    }

    private void GetThirdGabbyLoad() {
        GetThirdGabbyCommand command = new GetThirdGabbyCommand();
        command.setIdentify("GA");
        command.setToken(WangCaiApp.getUserCentre().getUserInfo().getToken());
        TypeToken typeToken = new TypeToken<RestResponse<GetThirdGabbyUrl>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, 0, this);
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            GetThirdGabbyUrl url = (GetThirdGabbyUrl) response.getData();
            if (!TextUtils.isEmpty(url.getRequestUrl())) {
                init(url.getRequestUrl());
            } else {
                init("file:///android_asset/web/Error.html");
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 3004 || errCode == 2016) {
                signOutDialog(getActivity(), errCode);
                return true;
            } else {
                init("file:///android_asset/web/Error.html");
                showToast(errDesc);
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (state == RestRequest.RUNNING) {
                dialogShow("加载中…");
            } else {
                dialogHide();
            }
        }
    };
}
