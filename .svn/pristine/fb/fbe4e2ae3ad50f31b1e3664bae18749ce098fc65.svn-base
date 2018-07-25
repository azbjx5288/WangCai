package com.wangcai.lottery.fragment;

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
import com.gyf.barlibrary.BarHide;

import butterknife.BindView;

/**
 * WebView页面
 */

public class WebViewFragment extends BaseFragment {
    private static final String TAG = WebViewFragment.class.getSimpleName();

    @BindView(R.id.gagame_WebView)
    WebView appView;
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "", R.layout.fragment_game_ga, false, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = getArguments().getString("url");
        if(!TextUtils.isEmpty(url))
            init(url);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionBar.hideBar(BarHide.FLAG_HIDE_BAR).init();
        if (appView != null) {
            appView.onResume();
        }
    }

    @Override
    public void onDestroy() {
        if (appView != null) {
            appView.destroy();
        }
        super.onDestroy();
    }
}
