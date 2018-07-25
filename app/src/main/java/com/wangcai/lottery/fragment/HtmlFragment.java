package com.wangcai.lottery.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
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
import com.wangcai.lottery.app.FragmentLauncher;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.material.SaveImageUtils;
import com.wangcai.lottery.util.DownPicUtil;
import com.wangcai.lottery.util.ToastUtils;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by ACE-PC on 2017/6/22.
 */

@RuntimePermissions
public class HtmlFragment extends BaseFragment  {
    private static final String TAG = HtmlFragment.class.getSimpleName();

    private String url = "";

    @BindView(R.id.payWebView)
    WebView appView;
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;

    public static void launch(BaseFragment fragment, String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        FragmentLauncher.launch(fragment.getActivity(), HtmlFragment.class, bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, getArguments().getString("title"), R.layout.fragment_html, true, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initImmersionBarEnabled();
        url = getArguments().getString("url");
        if(!TextUtils.isEmpty(url)){
            init();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (appView != null) {
            appView.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (appView != null) {
            appView.destroy();
        }
    }

    private void init() {
        loadingLayout.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        appView.setOverScrollMode(WebView.OVER_SCROLL_ALWAYS);
        WebSettings webSettings = appView.getSettings();
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
        appView.addJavascriptInterface(new JavaScriptInterface(), "android");
        appView.loadUrl(url);
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
        // 长按点击事件
        appView.setOnLongClickListener((View view) -> {
            final WebView.HitTestResult hitTestResult = appView.getHitTestResult();
            // 如果是图片类型或者是带有图片链接的类型
            if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE || hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                callCard(hitTestResult.getExtra());
            }
            return true;
        });
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void callCard(String path) {
        saveImageInto(WangCaiApp.getInstance().getBaseUrl() + path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HtmlFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showCard(final PermissionRequest request) {
        new AlertDialog.Builder(getActivity()).setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which)
            {
                request.proceed();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which)
            {
                request.cancel();
            }
        }).setCancelable(false).setMessage("使用支付扫码需要获取读写SD卡权限").show();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void onCardDenied() {
        ToastUtils.showShortToast(getActivity(), "获取读写SD卡权限才可使用支付");
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void onCardNeverAsk() {
        ToastUtils.showShortToast(getActivity(), "若想使用支付，请到“设置-应用程序-金苹果”中开启读写SD卡权限");
    }

    private class JavaScriptInterface {
        @JavascriptInterface
        public void goBack() {
            getActivity().finish();
        }

        @JavascriptInterface
        public void downloadQr(String path) {
            Log.e(TAG,"------------->"+path);
            callCard(path);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = (Bitmap) msg.obj;
            SaveImageUtils.saveImageToGallerys(getActivity(), bitmap);
        }
    };

    public void saveImageInto(String url) {
        // 弹出保存图片的对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("保存图片到本地");
        builder.setPositiveButton("确认", (DialogInterface dialogInterface, int i) -> {
            // 下载图片到本地
            DownPicUtil.downPic(url, (Bitmap bitmap) ->
            {
                Message msg = Message.obtain();
                msg.obj = bitmap;
                handler.sendMessage(msg);
            });
        });
        builder.setNegativeButton("取消", (DialogInterface dialogInterface, int i) ->
        {
            // 自动dismiss
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
