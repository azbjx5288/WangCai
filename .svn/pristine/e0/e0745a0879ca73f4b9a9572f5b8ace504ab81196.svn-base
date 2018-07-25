package com.wangcai.lottery.fragment;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.wangcai.lottery.BuildConfig;
import com.wangcai.lottery.R;
import com.wangcai.lottery.app.BaseFragment;
import com.wangcai.lottery.app.FragmentLauncher;
import com.wangcai.lottery.app.WangCaiApp;
import com.wangcai.lottery.base.net.GsonHelper;
import com.wangcai.lottery.base.net.JsonString;
import com.wangcai.lottery.base.net.RestCallback;
import com.wangcai.lottery.base.net.RestRequest;
import com.wangcai.lottery.base.net.RestRequestManager;
import com.wangcai.lottery.base.net.RestResponse;
import com.wangcai.lottery.data.Bet;
import com.wangcai.lottery.data.BetDetailCommand;
import com.wangcai.lottery.data.CancelPackageCommand;
import com.wangcai.lottery.data.CancelTraceCommand;
import com.wangcai.lottery.data.Lottery;
import com.wangcai.lottery.data.PackageTrace;
import com.wangcai.lottery.data.Trace;
import com.wangcai.lottery.data.TraceDetailCommand;
import com.wangcai.lottery.user.UserCentre;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Alashi on 2016/1/15.
 */
public class BetOrTraceDetailFragment extends BaseFragment {
    private static final String TAG = BetOrTraceDetailFragment.class.getSimpleName();

    private static final int BET_DETAIL_ID = 1;
    private static final int TRACE_DETAIL_ID = 2;
    private static final int CANCEL_PACKAGE_ID = 3;
    private static final int CANCEL_TRACE_ID = 4;

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.button)
    Button button;

    private boolean isBet;
    private Bet bet;
    private Trace trace;
    private Lottery lottery;
    private String[] pkids;
    private UserCentre userCentre;
    private String jsonDataForWeb;

    public static void launch(BaseFragment fragment, Bet bet) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isBet", true);
        bundle.putString("Bet", GsonHelper.toJson(bet));
        FragmentLauncher.launch(fragment.getActivity(), BetOrTraceDetailFragment.class, bundle);
    }

    public static void launch(BaseFragment fragment, Trace trace) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isBet", false);
        bundle.putString("Trace", GsonHelper.toJson(trace));
        FragmentLauncher.launch(fragment.getActivity(), BetOrTraceDetailFragment.class, bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isBet = getArguments().getBoolean("isBet");
        return inflateView(inflater, container, isBet ? "注单详情" : "追号详情", R.layout.bet_or_trace_detail, true, true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userCentre = WangCaiApp.getUserCentre();
        initWebView();
        if (isBet) {
            webView.loadUrl("file:///android_asset/web/BetDetail.html");
            loadBetData();
        } else {
            webView.loadUrl("file:///android_asset/web/TraceDetail.html");
            loadTraceData();
        }
    }

    private void loadBetData() {
        bet = GsonHelper.fromJson(getArguments().getString("Bet"), Bet.class);
        if (bet == null) {
            Toast.makeText(getActivity(), "无效注单参数", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }
        refreshBet(true,BET_DETAIL_ID);
    }

    private void loadTraceData() {
        trace = GsonHelper.fromJson(getArguments().getString("Trace"), Trace.class);
        if (trace == null) {
            Toast.makeText(getActivity(), "无效追号参数", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }
        refreshTrace(true,TRACE_DETAIL_ID);
    }

    private void refreshTrace(boolean withCache,int id) {
        TraceDetailCommand command = new TraceDetailCommand();
        command.setId(trace.getId());
        TypeToken typeToken = new TypeToken<RestResponse<PackageTrace>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, id, this);
        if (withCache) {
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null) {
                PackageTrace packageTrace=(PackageTrace)restResponse.getData();
                jsonDataForWeb = GsonHelper.toJson(packageTrace);
                update2WebView();
            }
        }
        restRequest.execute();
    }

    private void refreshBet(boolean withCache,int id) {
        BetDetailCommand command = new BetDetailCommand();
        command.setId(bet.getId());
        TypeToken typeToken = new TypeToken<RestResponse<Bet>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, id, this);
        if (withCache) {
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null) {
                jsonDataForWeb = GsonHelper.toJson(restResponse.getData());
                update2WebView();
            }
        }
        restRequest.execute();
    }

    private void update2WebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("onLoaded();", null);
        } else {
            webView.loadUrl("javascript:onLoaded();");
        }
    }

    private void initWebView() {
        webView.setOverScrollMode(WebView.OVER_SCROLL_ALWAYS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        WebSettings webSettings = webView.getSettings();
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
        webView.addJavascriptInterface(new JsInterface(), "androidJs");
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    public void onPause() {
        webView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        webView.destroy();
        super.onDestroyView();
    }

    private class JsInterface {
        @JavascriptInterface
        public String getData() {
            return jsonDataForWeb;
        }

        @JavascriptInterface
        public String getLottery() {
            if (isBet) {
                return GsonHelper.toJson(userCentre.getLottery(bet.getLotteryId()));
            } else {
                return GsonHelper.toJson(userCentre.getLottery(trace.getLotteryId()));
            }
        }

        @JavascriptInterface
        public void changeUi(final int lotteryId, final boolean supportCancel, final String[] pkids) {
            BetOrTraceDetailFragment.this.pkids = pkids;
            button.post(new Runnable() {
                @Override
                public void run() {
                    lottery = WangCaiApp.getUserCentre().getLottery(lotteryId);
                    if (lottery == null) {
                        findViewById(R.id.bottom).setVisibility(View.GONE);
                    }
                    button.setTag(supportCancel);
                    if (isBet) {
                        button.setText(supportCancel ? "撤单" : "去购彩");
                    } else {
                        if (supportCancel && BetOrTraceDetailFragment.this.pkids != null && BetOrTraceDetailFragment.this.pkids.length > 0) {
                            button.setText("撤单");
                        } else {
                            button.setText("去购彩");
                        }
                    }
                }
            });
        }

        @JavascriptInterface
        public void allowCancelTrace(final boolean allow) {
            button.post(() -> {
                if(BetOrTraceDetailFragment.this.pkids == null || BetOrTraceDetailFragment.this.pkids.length == 0){
                    return;
                }
                if (allow) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("取消追号")
                            .setMessage("确定要取消追号？")
                            .setPositiveButton("取消追号", (dialog, which) -> {
                                try {
                                    CancelTraceCommand command = new CancelTraceCommand();
                                    command.setTraceId(String.valueOf(trace.getId()));
                                    command.setIds(URLEncoder.encode(Arrays.asList(pkids).toString(), "UTF-8"));
                                    command.setToken(userCentre.getUserInfo().getToken());
                                    String requestCommand = GsonHelper.toJson(command);
                                    requestCommand = requestCommand.replace(":", "=").replace(",", "&").replace("\"", "");
                                    requestCommand = URLDecoder.decode(requestCommand.substring(1, requestCommand.length() - 1), "UTF-8");

                                    CancelTraceCommand commandCancel = new CancelTraceCommand();
                                    commandCancel.setTraceId(String.valueOf(trace.getId()));
                                    commandCancel.setIds(Arrays.asList(pkids).toString());
                                    commandCancel.setToken(userCentre.getUserInfo().getToken());
                                    commandCancel.setSign(DigestUtils.md5Hex(URLEncoder.encode(requestCommand + "&packet=Game&action=cancelTraceReserve", "UTF-8") + userCentre.getKeyApiKey()));
                                    TypeToken typeToken = new TypeToken<RestResponse<PackageTrace>>() {
                                    };
                                    RestRequest restRequest = RestRequestManager.createRequest(getActivity(), commandCancel, typeToken, restCallback, CANCEL_TRACE_ID, this);
                                    restRequest.execute();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            })
                            .setNegativeButton("保留追号", null)
                            .create().show();
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("提醒")
                            .setMessage("已经过了取消追号的时间，不能取消追号")
                            .setNegativeButton("确定", null)
                            .create().show();
                }
            });
        }
    }

    @OnClick(R.id.button)
    public void onClick(View v) {
        boolean supportCancel = (boolean) v.getTag();
        if (!supportCancel) {
            GameStickNavFragment.launch(this, lottery);
            return;
        }

        if (isBet) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("取消订单")
                    .setMessage("确定要取消订单？")
                    .setPositiveButton("取消订单", (dialog, which) -> {
                        try {
                            CancelPackageCommand command = new CancelPackageCommand();
                            command.set_Id(String.valueOf(bet.getId()));
                            command.setToken(userCentre.getUserInfo().getToken());
                            String requestCommand = GsonHelper.toJson(command);
                            requestCommand = requestCommand.replace(':', '=').replace(',', '&').replace("\"", "");
                            command.setSign(DigestUtils.md5Hex(URLEncoder.encode(requestCommand.substring(1, requestCommand.length() - 1) + "&packet=Game&action=dropProject", "UTF-8") + userCentre.getKeyApiKey()));
                            TypeToken typeToken = new TypeToken<RestResponse<Bet>>() {
                            };
                            RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, CANCEL_PACKAGE_ID, this);
                            restRequest.execute();

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    })
                    .setNegativeButton("保留订单", null)
                    .create().show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                webView.evaluateJavascript("reviewStatus();", null);
            } else {
                webView.loadUrl("javascript:reviewStatus();");
            }
        }
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case TRACE_DETAIL_ID:
                case CANCEL_TRACE_ID:
                    PackageTrace packageTrace=(PackageTrace)response.getData();
                    jsonDataForWeb = GsonHelper.toJson(packageTrace);
                    update2WebView();
                    break;
                case BET_DETAIL_ID:
                case CANCEL_PACKAGE_ID:
                    Bet packageBet=(Bet)response.getData();
                    jsonDataForWeb = GsonHelper.toJson(packageBet);
                    update2WebView();
                    break;
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 3004 || errCode == 2016) {
                signOutDialog(getActivity(), errCode);
            } else {
                showToast(errDesc);
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (state == RestRequest.RUNNING) {
                switch (request.getId()) {
                    case CANCEL_PACKAGE_ID:
                    case CANCEL_TRACE_ID:
                        dialogShow("正在处理...");
                        break;
                    case BET_DETAIL_ID:
                    case TRACE_DETAIL_ID:
                        dialogShow("正在加载...");
                        break;
                }
            } else {
                dialogHide();
            }
        }
    };
}
